package com.one.exercise.controller;

import com.alibaba.fastjson.JSONObject;
import com.one.exercise.enums.VerifyType;
import com.one.exercise.pojo.Identity;
import com.one.exercise.pojo.ResultResponse;
import com.one.exercise.pojo.Student;
import com.one.exercise.pojo.StudentVO;
import com.one.exercise.service.StudentService;
import com.one.exercise.utils.HttpUtils;
import com.one.exercise.utils.IdentityUtils;
import com.one.exercise.utils.Tools;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("student")
@Slf4j
public class StudentLoginController {

    @Resource
    private StudentService studentService;

    @GetMapping(value = "login")
    public ResultResponse login(String code, String loginType, HttpServletRequest request) {

        String avatar = request.getParameter("avatar").trim();
        String nickName = request.getParameter("nickName").trim();
        String gender = request.getParameter("gender").trim();

        System.err.println(code);
        System.err.println(loginType);
        System.err.println(avatar);
        System.err.println(nickName);
        System.err.println(gender);

        /**
         * 微信登录
         * 1. 获取openid
         * 2. 查找数据库是否存在该openid
         *      存在：则进行登录，并返回studentVO
         *      不存在：将student信息添加到数据库(注册)，并返回主键，通过主键查找，并返回studentVO
         */
        if (Tools.isNullAllStr(code, loginType, avatar, nickName, gender)){
            return ResultResponse.RETURN(3, "fail:参数缺失", null);
        }

        if (loginType.trim().equalsIgnoreCase("wx")){
            log.info("微信登陆...");
            JSONObject jsonObject = null;
            try {
                jsonObject = wxLogin(code);
                if(jsonObject == null || ((String)jsonObject.get("openid")).length()==0){
                    return ResultResponse.RETURN(2, "fail:登录失败", null);
                }
            }catch (Exception e){
                return ResultResponse.RETURN(3, "fail:code参数无效", null);
            }

            String openid = (String)jsonObject.get("openid");
            // wxLogin
            StudentVO studentVO = studentService.getStudentById(openid);

            JSONObject jsonObject1 = new JSONObject();
            if (studentVO==null){// 注册
                Student student = new Student();
                student.setNickName(nickName);
                student.setGender(Integer.parseInt(gender));
                student.setAvatar(avatar);
                student.setOpenId(openid);
                Long studentId = studentService.wxJoin(student);
                studentVO  = studentService.getStudentById(studentId);
            }
            jsonObject1.put("studentInfo", studentVO);
            try {
                String build = IdentityUtils.build(new Identity(studentVO.getStudentId(), VerifyType.STUDENT.index));
                jsonObject1.put("verifyCode", build);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ResultResponse.RETURN(1, "success", jsonObject1);
        }else {
            return ResultResponse.RETURN(2, "fail:loginType参数错误", null);
        }


    }

    @Value("${wx.appid}")
    private String appid;
    @Value("${wx.secret}")
    private String secret;
    public JSONObject wxLogin(String code){

//        String appid = "wx110db91794f68b40";
//        String secret = "dc49f7c935b1f1653e4f9c58825a23a1";
//        dev
//        String appid = "wx579832410b69b5a0";
//        String secret = "cd4e33da68d80d47ddcda9a995a9c8e0";
        String js_code = code;
        String host = "https://api.weixin.qq.com";
        String path = "/sns/jscode2session?appid=" + appid + "&secret=" + secret + "&js_code=" + js_code + "&grant_type=authorization_code";
        // String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+ appid +"&secret="+ secret +"&js_code="+ js_code +"&grant_type=authorization_code";
        Map<String, String> headers = new HashMap<String, String>();
        Map<String, String> querys = new HashMap<String, String>();

        try {
            HttpResponse result = HttpUtils.doGet(host, path, "GET", headers, querys);
            String responseBody = EntityUtils.toString(result.getEntity());
            JSONObject jsonObject = JSONObject.parseObject(responseBody);

            System.out.println(jsonObject.toString());

            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
