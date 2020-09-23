package com.one.exercise.controller;

import com.one.exercise.pojo.Identity;
import com.one.exercise.pojo.ResultResponse;
import com.one.exercise.pojo.Teacher;
import com.one.exercise.service.TeacherService;
import com.one.exercise.utils.IdentityUtils;
import com.one.exercise.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("manager")
public class ManagerTeacherController {

    @Autowired
    private TeacherService teacherService;

    /**
     * 添加教师
     */
    @PostMapping("teacher-save")
    public ResultResponse saveTeacher(Teacher teacher, HttpServletRequest request) {

        Identity identity = (Identity)request.getAttribute("identity");
        boolean green = IdentityUtils.greenAdmin(identity);
        if (!green){
            return ResultResponse.RETURN(2, "fail: 权限不足,非管理员不能访问", null);
        }

        try {
            if (teacher == null || Tools.isNullAllStr(teacher.getEmail(), teacher.getPassword())) {
                return ResultResponse.RETURN(3, "fail: email、password为必要参数", null);
            }
            Teacher resultTeacher = teacherService.saveOneTeacher(teacher);
            return ResultResponse.RETURN(1, "success", resultTeacher);
        } catch (Exception e) {
            return ResultResponse.RETURN(6, "fail:该用户已经存在", null);
        }
    }

    /**
     * 删除教师-逻辑删除
     */
    @PostMapping("teacher-remove")
    public ResultResponse logicDeleteTeacher(Integer teacherId, HttpServletRequest request) {
        Identity identity = (Identity)request.getAttribute("identity");
        boolean green = IdentityUtils.greenAdmin(identity);
        if (!green){
            return ResultResponse.RETURN(2, "fail: 权限不足,非管理员不能访问", null);
        }

        int i = teacherService.logicDelete(teacherId);
        return ResultResponse.RETURN(1, "success", i);
    }

    /**
     * 修改教师信息
     */
    @PostMapping("teacher-modify")
    public ResultResponse modifyTeacher(Teacher teacher, HttpServletRequest request) {
        Identity identity = (Identity)request.getAttribute("identity");
        boolean green = IdentityUtils.greenAdmin(identity);
        if (!green){
            return ResultResponse.RETURN(2, "fail: 权限不足,非管理员不能访问", null);
        }

        Teacher resultTeacher = teacherService.updateTeacherById(teacher);
        return ResultResponse.RETURN(1, "success", resultTeacher);
    }

    /**
     * 查询-获取教师列表
     */
    @PostMapping("teacher-queryList")
    public ResultResponse queryListTeacher(int startPage, int pageSize, HttpServletRequest request){
        Identity identity = (Identity)request.getAttribute("identity");
        boolean green = IdentityUtils.greenAdmin(identity);
        if (!green){
            return ResultResponse.RETURN(2, "fail: 权限不足,非管理员不能访问", null);
        }

        Map<String, Object> map = teacherService.selectTeacherList(startPage, pageSize);
        return ResultResponse.RETURN(1, "success", map);
    }

}
