package com.one.exercise.controller;

import com.one.exercise.pojo.*;
import com.one.exercise.pojo.questioncomment.*;
import com.one.exercise.service.QuestionCommentBackService;
import com.one.exercise.service.QuestionCommentService;
import com.one.exercise.service.StudentService;
import com.one.exercise.utils.IdentityUtils;
import com.one.exercise.utils.Tools;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("QuestionComment")
public class QuestionCommentController {

    @Resource
    private StudentService studentService;

    @Resource
    private QuestionCommentService questionCommentService;

    @Resource
    private QuestionCommentBackService questionCommentBackService;

    @PostMapping("comment")
    public ResultResponse comment(QuestionCommentBo questionCommentBo, HttpServletRequest request){

        Identity identity = (Identity)request.getAttribute("identity");
        long studentId = identity.getId();
        boolean green = IdentityUtils.greenStudent(identity);
        if (!green){
            return ResultResponse.RETURN(2, "fail: 权限不足", null);
        }

        if (questionCommentBo.getQuestionId() != null && questionCommentBo.getQuestionId() <=0 && Tools.isNullStr(questionCommentBo.getMessage())){
            return ResultResponse.RETURN(3, "参数出错或参数不全", null);
        }

        QuestionComment questionComment = new QuestionComment();
        BeanUtils.copyProperties(questionCommentBo, questionComment);

        questionComment.setInitiator(studentId);

        System.out.println(studentId);
        // 获取当前学生信息
        StudentVO student = studentService.getStudentById(studentId);

        questionComment.setFace(student.getAvatar());
        questionComment.setNickName(student.getNickName());

        // 存储评论信息
        QuestionComment resultQuestionComment = questionCommentService.saveComment(questionComment);
        QuestionCommentVO questionCommentVO = new QuestionCommentVO();
        BeanUtils.copyProperties(resultQuestionComment, questionCommentVO);

        return ResultResponse.RETURN(1, "success", questionCommentVO);
    }

    @PostMapping("writeBack")
    public ResultResponse writeBack(QuestionCommentBackBO questionCommentBackBO, HttpServletRequest request){

        Identity identity = (Identity)request.getAttribute("identity");
        long studentId = identity.getId();
        boolean green = IdentityUtils.greenStudent(identity);
        if (!green){
            return ResultResponse.RETURN(2, "fail: 权限不足", null);
        }

        QuestionCommentBack questionCommentBack = new QuestionCommentBack();
        BeanUtils.copyProperties(questionCommentBackBO, questionCommentBack);

        // 获取发送人的基本信息
        StudentVO sender = studentService.getStudentById(studentId);
        // 接收人的基本信息
        StudentVO receiver = studentService.getStudentById(questionCommentBackBO.getBackReceiver());

        // 设置发送人基本信息
        questionCommentBack.setBackSend(studentId);
        questionCommentBack.setSendFace(sender.getAvatar());
        questionCommentBack.setSendNickName(sender.getNickName());

        // 设置接收人基本信息
        questionCommentBack.setBackReceiver(receiver.getStudentId());
        questionCommentBack.setReceiverFace(receiver.getAvatar());
        questionCommentBack.setReceiverNickName(receiver.getNickName());

        QuestionCommentBack resultQuestionCommentBack = questionCommentBackService.saveBack(questionCommentBack);

        QuestionCommentBackVO questionCommentBackVO = new QuestionCommentBackVO();
        BeanUtils.copyProperties(resultQuestionCommentBack, questionCommentBackVO);

        return ResultResponse.RETURN(1, "success", questionCommentBackVO);
    }

    @PostMapping("queryCommentList")
    public ResultResponse queryComment(HttpServletRequest request, long questionId){

        Identity identity = (Identity)request.getAttribute("identity");
        long studentId = identity.getId();
        boolean green = IdentityUtils.greenStudent(identity);
        if (!green){
            return ResultResponse.RETURN(2, "fail: 权限不足", null);
        }

        List<QuestionCommentQuery> questionCommentQueryList = questionCommentService.queryCommentList(questionId);

        return ResultResponse.RETURN(1, "success", questionCommentQueryList);

    }

    @PostMapping("countComment")
    public ResultResponse countComment(HttpServletRequest request, long questionId){

        Identity identity = (Identity)request.getAttribute("identity");
        long studentId = identity.getId();
        boolean green = IdentityUtils.greenStudent(identity);
        if (!green){
            return ResultResponse.RETURN(2, "fail: 权限不足", null);
        }

        int i = questionCommentService.countComment(questionId);

        return ResultResponse.RETURN(1, "success", i);
    }

}
