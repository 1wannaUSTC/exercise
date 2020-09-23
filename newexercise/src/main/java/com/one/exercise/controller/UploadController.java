package com.one.exercise.controller;

import com.one.exercise.enums.Gender;
import com.one.exercise.enums.ImageFormat;
import com.one.exercise.pojo.ResultResponse;
import com.one.exercise.pojo.Teacher;
import com.one.exercise.pojo.TeacherVO;
import com.one.exercise.service.TeacherService;
import com.one.exercise.utils.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 用于图片上传的controller
 */
@RestController
public class UploadController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);

    @Value("${file.img.imagePathLocal}")
    private String imagePathLocal;

    @Value("${file.img.imagePathWeb}")
    private String imagePathWeb;

    @Autowired
    private TeacherService teacherService;

    /** 图片上传接口 */
    @PostMapping("/put-image")
    public ResultResponse upload(MultipartFile file) {
        // 如果上传了头像将更新头像信息
        if (file!=null && !file.isEmpty()) {
            try {
                // 拼接成本地路径，并保存到本地
                String fileName = file.getOriginalFilename();

                // 判断图片格式
                if (!ImageFormat.isFormat(fileName)){
                    return ResultResponse.RETURN(5, "修改信息失败：不是支持的图片格式", null);
                }

                fileName = UUID.randomUUID() + "0" + fileName.substring(fileName.lastIndexOf("."));
                File dest = new File(imagePathLocal + fileName);

                System.out.println("fileName: "+fileName);

                // 拼接成网络地址
                String webPath = imagePathWeb + fileName;

                file.transferTo(dest);
                BufferedImage bi = ImageIO.read(dest);

                if(bi == null){
                    LOGGER.info("此文件不为图片文件");
                    dest.delete();
                    return ResultResponse.RETURN(6, "修改信息失败：图片内容不正确", null);
                }else {
                    System.out.println("上传成功: 内容为图片内容");
                    // 返回图片内容
                    return ResultResponse.RETURN(1, "success", webPath);
                }

            }catch (IOException e){
                return ResultResponse.RETURN(2,"图片上传异常", null);
            }
        }else {
            return ResultResponse.RETURN(3,"file参数缺失", null);
        }
    }

    /** 教师头像上传,及信息修改 */
    @PostMapping("/uploadTeacher")
    @ResponseBody
    public ResultResponse uploadTeacher(@RequestParam(required = false) MultipartFile file, HttpServletRequest request)  {

        String teacherId = request.getParameter("teacherId");
        String phone = request.getParameter("phone");
        String name = request.getParameter("name");
        String gender = request.getParameter("gender");
        String course = request.getParameter("course");

        LOGGER.info("teacherId:" + teacherId );

        Teacher teacher = new Teacher();
        try{
            teacher.setTeacherId(Integer.valueOf(teacherId));
        }catch (Exception e){
            return ResultResponse.RETURN(4, "修改信息失败：参数有误", null);
        }
        teacher.setPhone(phone);
        teacher.setName(name);
        teacher.setGender(Gender.getIndex(gender));
        teacher.setCourse(course);

        // 如果上传了头像将更新头像信息
        if (file!=null && !file.isEmpty()) {
            try {
                // 拼接成本地路径，并保存到本地
                String fileName = file.getOriginalFilename();

                // 判断图片格式
                if (!ImageFormat.isFormat(fileName)){
                    return ResultResponse.RETURN(5, "修改信息失败：不是支持的图片格式", null);
                }

                fileName = UUID.randomUUID() + "0" + fileName.substring(fileName.lastIndexOf("."));
                File dest = new File(imagePathLocal + fileName);

                LOGGER.info(fileName);

                // 拼接成网络地址
                String save2dbPath = imagePathWeb + fileName;

                file.transferTo(dest);
                BufferedImage bi = ImageIO.read(dest);
                if(bi == null){
                    LOGGER.info("此文件不为图片文件");
                    dest.delete();
                    return ResultResponse.RETURN(6, "修改信息失败：图片内容不正确", null);
                }else {
                    LOGGER.info("内容为图片内容");
                    LOGGER.info("上传成功");
                }

                // 存入teacher对象中
                teacher.setIFace(save2dbPath);

            }catch (IOException e){
                LOGGER.info("更新头像失败");
                return ResultResponse.RETURN(2,"修改头像失败", null);
            }
        }

        TeacherVO teacherVO;
        // 对teacher中的非空数据进行更新
        if (!Tools.isNullStr(teacher.getTeacherId().toString())){
            // 如果为null 更新失败
            Teacher teacher1 = null;
            try {
                teacher1 = teacherService.updateTeacherById(teacher);
            }catch (Exception e){
                e.printStackTrace();
                return ResultResponse.RETURN(4, "修改信息失败：参数异常", null);
            }
            LOGGER.info(teacher1.toString());
            teacherVO = new TeacherVO();
            BeanUtils.copyProperties(teacher1,teacherVO);
            return ResultResponse.RETURN(1, "修改信息成功", teacherVO);
        }else {
            return ResultResponse.RETURN(3, "修改信息失败：参数有误", null);
        }

    }

}


