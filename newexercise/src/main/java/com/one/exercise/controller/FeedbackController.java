package com.one.exercise.controller;

import com.one.exercise.exception.NotCorrectImageFormatException;
import com.one.exercise.pojo.Feedback;
import com.one.exercise.pojo.FeedbackPicture;
import com.one.exercise.pojo.Identity;
import com.one.exercise.pojo.ResultResponse;
import com.one.exercise.service.FeedbackPictureService;
import com.one.exercise.service.FeedbackService;
import com.one.exercise.utils.FileUpDownLoad;
import com.one.exercise.utils.IdentityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("feedback")
@Slf4j
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private FeedbackPictureService feedbackPictureService;

    @Value("${file.img.imagePathLocal}")
    private String savePath;

    @Value("${file.img.imagePathWeb}")
    private String webPrefix;

    @PostMapping("send")
    @Transactional
    public ResultResponse getFeedback(@RequestParam(required = false) MultipartFile[] files, Feedback feedback, HttpServletRequest request){


        Identity identity = (Identity)request.getAttribute("identity");
        long id = identity.getId();
        System.err.println(id);
        boolean green = IdentityUtils.greenAdminAndTeacher(identity);
        if (!green){
            return ResultResponse.RETURN(2, "fail: 权限不足", false);
        }

        List<String> imageWebPath = null;
        if (files != null && files.length != 0){
            // 获取内容
            try {
                imageWebPath = FileUpDownLoad.upImages(files, savePath, webPrefix);
            } catch (Exception e){
                e.printStackTrace();
                return ResultResponse.RETURN(3, "fail"+e.getMessage(), false);
            }
        }

        feedback.setTeacherId(id);
        feedback.setValidity(1);
        // 插入反馈信息
        int i1 = feedbackService.insertFeedback(feedback);
        log.info("feedbackService.insertFeedback(feedback) : " + i1);
        if (i1 == 0){
            return ResultResponse.RETURN(4, "fail:插入数据失败4.1", false);
        }

        if (imageWebPath != null && imageWebPath.size() != 0){
            List<FeedbackPicture> list = new ArrayList<>();
            imageWebPath.forEach(item->{
                list.add(new FeedbackPicture(Long.valueOf(id), item));
            });
            int i2 = feedbackPictureService.insertPictureList(list);
            log.info("feedbackPictureService.insertPictureList(list) : " + i2);
            if (i2 == 0){
                return ResultResponse.RETURN(4, "fail:插入数据失败4.2", false);
            }
        }

        return ResultResponse.RETURN(1, "success", true);
    }

}
