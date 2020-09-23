package com.one.exercise.controller;

import com.alibaba.fastjson.JSONObject;
import com.one.exercise.enums.AdminType;
import com.one.exercise.enums.VerifyType;
import com.one.exercise.pojo.Identity;
import com.one.exercise.pojo.ResultResponse;
import com.one.exercise.pojo.Teacher;
import com.one.exercise.pojo.TeacherVO;
import com.one.exercise.service.CodeMapService;
import com.one.exercise.service.TeacherService;
import com.one.exercise.service.impl.MailService;
import com.one.exercise.utils.IdentityUtils;
import com.one.exercise.utils.PageUtil;
import com.one.exercise.utils.Tools;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("teacher")
public class TeacherController {

    @Resource
    private TeacherService teacherService;

    @Resource
    private MailService mailService;

    @Resource
    private CodeMapService codeMapService;

    @Value("${file.img.imagePathWeb}")
    private String imagePathWeb;

    /**
     * 教师注册
     */
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public ResultResponse teacherRegister(String email,String password,String checkCode){

        try{
            String okCode = codeMapService.selectCodeRedis(email);
            if (!checkCode.equals(okCode)){
                return ResultResponse.RETURN(2,"注册失败：验证码不正确或验证码超时。",false);
            }
        }catch (Exception e){
            System.out.println("此处的异常很可能为空，原因是未找到该邮箱；也有肯是找到了多个");
            return ResultResponse.RETURN(3,"注册失败：系统异常",false);
        }

        if (teacherService.hasTeacherByEmail(email)){
            return ResultResponse.RETURN(4,"注册失败：邮箱已经被注册",false);
        }

        Teacher teacher = new Teacher();
        teacher.setEmail(email);
        teacher.setPassword(password);
        teacher.setIFace(imagePathWeb + "default_face.png");
        int i = teacherService.saveTeacher(teacher);

        if (i<=0){
            return ResultResponse.RETURN(4,"注册失败：邮箱已经注册。",false);
        }

        return ResultResponse.RETURN(1,"注册成功",i>0);
    }

    /**
     * 教师登录
     * @return
     */
    @PostMapping("login")
    public ResultResponse login(String email,String password){

        if (Tools.isNullAllStr(email, password)){
            return ResultResponse.RETURN(3,"登录失败：账号或密码为空", null);
        }

        Teacher returnTeacher = teacherService.selectTeacherByEmailAndPw(email,password);
        if (returnTeacher == null){
            return ResultResponse.RETURN(2,"登录失败：账号或密码错误", null);
        }

        TeacherVO teacherVO = new TeacherVO();
        BeanUtils.copyProperties(returnTeacher,teacherVO);

        // 判断登录者的身份类型
        int identityCode = ((int)(returnTeacher.getIsAdmin())) == AdminType.ADMIN.index ? VerifyType.ADMIN.index : VerifyType.TEACHER.index;

        try {
            // 生成身份码
            String build = IdentityUtils.build(new Identity((long) returnTeacher.getTeacherId(), identityCode));
            teacherVO.setVerifyCode(build);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultResponse.RETURN(500,e.getMessage(), null);
        }

        return ResultResponse.RETURN(1,"登录成功", teacherVO);
    }

    /**
     * 根据number删除一个teacher
     * @param number
     * @return
     */
    @RequestMapping("delete")
    public boolean delete(String number){
        int i = teacherService.delete(number);
        return i>0;
    }

    /**
     * 发送验证码
     * @param email
     * @return
     */
    @GetMapping("getCheckCode")
    public ResultResponse getCheckCode(String email){
        String checkCode = String.valueOf(new Random().nextInt(899999) + 100000);
        String message = "您的注册验证码为(验证码5分钟内有效)："+checkCode;

        // i 为1表示code失效，添加成功
        // i 为0表示code有效，不添加
        int i = codeMapService.saveCodeRedis(email,checkCode);
        System.out.println("redis获取code状态：" + i);

        if (i>0){
            try {
                new Thread(()->{
                    mailService.sendSimpleMail(email, "注册验证码", message);
                }).start();
            }catch (Exception e){
                return ResultResponse.RETURN(3,"邮件发送过程出现异常", false);
            }
            return ResultResponse.RETURN(1,"邮件发送成功", true);
        }else
            return ResultResponse.RETURN(2,"验证码处于有效期(有效时间5分钟)", false);
    }

    /**
     * 根据教师id获取教师对象
     * @return
     */
    @GetMapping("get")
    public ResultResponse getTeacherById(int teacherId, HttpServletRequest request){
        Identity identity = (Identity)request.getAttribute("identity");
        long id = identity.getId();
        boolean green = IdentityUtils.greenAdminAndTeacher(identity);
        if (!green || id != teacherId){
            return ResultResponse.RETURN(2, "fail: 权限不足", null);
        }

        try {
            Teacher teacher = teacherService.selectTeacherById(teacherId);
            if (teacher == null){
                return ResultResponse.RETURN(2,"没有该用户",null);
            }else {
                return ResultResponse.RETURN(1,"ok",teacher);
            }
        }catch (Exception e){
            return ResultResponse.RETURN(500,e.getMessage(),null);
        }
    }

    /**
     * 学生端：根据教师id获取教师对象
     */
    @GetMapping("info")
    public ResultResponse getTeacherInfo(int teacherId, HttpServletRequest request){
        Identity identity = (Identity)request.getAttribute("identity");
        boolean green = IdentityUtils.greenAll(identity);
        if (!green){
            return ResultResponse.RETURN(2, "fail: 权限不足", null);
        }
        Teacher teacher = teacherService.selectTeacherById(teacherId);
        if (teacher == null){
            return ResultResponse.RETURN(2,"没有该用户",null);
        }else {
            TeacherVO teacherVO = new TeacherVO();
            BeanUtils.copyProperties(teacher, teacherVO);
            return ResultResponse.RETURN(1,"success",teacherVO);
        }
    }

    /**
     * 获取老师信息，根据学科获取，关注度越高的老师排名越靠前
     * 权限级别：All
     */
    @PostMapping("queryTeacherList")
    public ResultResponse queryTeacherList(Integer subjectId,
                                           @RequestParam(required = false, defaultValue = "1") Integer startPage,
                                           @RequestParam(required = false, defaultValue = "10") Integer pageSize, HttpServletRequest request){
        // 参数校验
        if (subjectId == null || subjectId <= 0){
            return ResultResponse.RETURN(3, "fail: 错误的subjectId参数", null);
        }

        /**
         * 1. 获取总数、生成page对象
         * 2. 获取分开开始索引
         * 3. 获取teacher集合
         */

        // 分页信息
        int total = teacherService.countSubjectTeacherList(subjectId);
        if (total == 0){
            return ResultResponse.RETURN(1, "success", null);
        }
        PageUtil pageInfo = new PageUtil(total, pageSize);
        int startIndex = pageInfo.getStartIndex(startPage);

        // 返回的教师信息
        List<Teacher> teachers = teacherService.selectTeacherList(subjectId, startIndex, pageSize);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pageInfo", pageInfo);
        jsonObject.put("teacherList", teachers);
        return ResultResponse.RETURN(1, "success", jsonObject);
    }

}
