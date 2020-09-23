package com.one.exercise.controller;

import com.one.exercise.enums.ImageFormat;
import com.one.exercise.pojo.Identity;
import com.one.exercise.pojo.ResultResponse;
import com.one.exercise.utils.IdentityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("img_server")
public class UploadImageController {

    /**
     * 教师头像上传
     * @param
     * @return
     */
    @PostMapping("/upload")
    @ResponseBody
    public String uploadTeacher(@RequestParam(required = false) MultipartFile file, HttpServletRequest request)  {

        String imagePathLocal = "/usr/my_file/pro_static/images/";
        String imagePathWeb= "http://148.70.115.4/exercise/static/images/";
        String returnImagePath = null;

        System.out.println(file.isEmpty());

        // 如果上传了头像将更新头像信息
        if (!file.isEmpty()) {
            try {
                // 拼接成本地路径，并保存到本地
                String fileName = file.getOriginalFilename();

                // 判断图片格式
                if (!ImageFormat.isFormat(fileName)){
                    return "修改信息失败：不是支持的图片格式";
                }

                fileName = "img-server" + UUID.randomUUID() + "0" + fileName.substring(fileName.lastIndexOf("."));
                File dest = new File(imagePathLocal + fileName);


                // 拼接成网络地址
                returnImagePath = imagePathWeb + fileName;

                file.transferTo(dest);
                BufferedImage bi = ImageIO.read(dest);
                if(bi == null){
                    dest.delete();
                    return "图片内容不正确";
                }

            }catch (IOException e){
                return "上传失败";
            }
        }

        return returnImagePath;

    }
}