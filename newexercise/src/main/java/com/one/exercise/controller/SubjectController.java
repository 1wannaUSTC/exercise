package com.one.exercise.controller;

import com.one.exercise.enums.VerifyType;
import com.one.exercise.exception.InexistenceException;
import com.one.exercise.pojo.Identity;
import com.one.exercise.pojo.ResultResponse;
import com.one.exercise.pojo.Subject;
import com.one.exercise.service.SubjectService;
import com.one.exercise.utils.IdentityUtils;
import com.one.exercise.utils.SpringContextUtils;
import com.one.exercise.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @GetMapping("getAll")
    public List<Subject> getAll(){
        List<Subject> subjects = subjectService.selectSubjectList();
        return subjects;
    }

    @PostMapping("teacher-list")
    public ResultResponse getSubjectListByTeacherId(HttpServletRequest request){
        Identity identity = (Identity)request.getAttribute("identity");
        boolean green = IdentityUtils.greenAdminAndTeacher(identity);
        if (!green){
            return ResultResponse.RETURN(2, "fail: 权限不足", null);
        }

        long tId = identity.getId();

        // 根据teacherID 获取科目列表
        List<Subject> subjects = subjectService.selectSubjectListByTeacherId((int) tId);
        return ResultResponse.RETURN(1, "success", subjects);
    }

    @PostMapping("teacher-choice-subject")
    public ResultResponse choiceSubject(@RequestBody List<Integer> list, HttpServletRequest request){

        Identity identity = (Identity)request.getAttribute("identity");
        boolean green = IdentityUtils.greenAdminAndTeacher(identity);
        if (!green){
            return ResultResponse.RETURN(2, "fail: 权限不足", null);
        }

        long id = identity.getId();


        /**
         * 1. 参数校验
         * 2. 获取id
         * 3. 添加课程id
         */
        if (list == null){
            return ResultResponse.RETURN(3, "fail: 课程列表数组不能为空", null);
        }

        // 添加课程
        List<Subject> subjectList =  subjectService.insertSubjectListIgnore(id, list);

        // 根据teacherID 获取科目列表
        List<Subject> subjects = subjectService.selectSubjectListByTeacherId((int) id);
        return ResultResponse.RETURN(1, "success", subjects);
    }
}