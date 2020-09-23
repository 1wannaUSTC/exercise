package com.one.exercise.controller;

import com.one.exercise.exception.InsertException;
import com.one.exercise.pojo.Identity;
import com.one.exercise.pojo.ResultResponse;
import com.one.exercise.pojo.Student;
import com.one.exercise.service.StudentService;
import com.one.exercise.utils.IdentityUtils;
import com.one.exercise.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("manager")
public class ManagerStudentController {

    @Resource
    private StudentService studentService;

    /**
     * 添加学生
     * @param student
     * @param request
     * @return
     */
    @PostMapping("student-save")
    public ResultResponse saveStudent(Student student, HttpServletRequest request){
        Identity identity = (Identity)request.getAttribute("identity");
        boolean green = IdentityUtils.greenAdmin(identity);
        if (!green){
            return ResultResponse.RETURN(2, "fail: 权限不足,非管理员不能访问", null);
        }

        if(Tools.isNullAllStr(student.getEmail(), student.getPassword())) {
            return ResultResponse.RETURN(3, "fail: email、password为必要参数", null);
        }

        try {
            student.setNickName(student.getEmail());
            Student resultStudent = studentService.insertStudent(student);
            return ResultResponse.RETURN(1, "success", resultStudent);
        } catch (InsertException e) {
            return ResultResponse.RETURN(4, "fail: " + e.getMessage(), null);
        }
    }

    /**
     * 逻辑删除学生
     * @param studentId
     * @param request
     * @return
     */
    @PostMapping("student-remove")
    public ResultResponse removeStudent(Long studentId, HttpServletRequest request){

        Identity identity = (Identity)request.getAttribute("identity");
        boolean green = IdentityUtils.greenAdmin(identity);
        if (!green){
            return ResultResponse.RETURN(2, "fail: 权限不足,非管理员不能访问", null);
        }

        Student resultStudent = studentService.logicDelete(studentId);
        return ResultResponse.RETURN(1, "success", resultStudent);

    }

    /**
     * 修改学生信息
     * @param student
     * @param request
     * @return
     */
    @PostMapping("student-modify")
    public ResultResponse modifyStudent(Student student, HttpServletRequest request){

        Identity identity = (Identity)request.getAttribute("identity");
        boolean green = IdentityUtils.greenAdmin(identity);
        if (!green){
            return ResultResponse.RETURN(2, "fail: 权限不足,非管理员不能访问", null);
        }

        if (student.getStudentId() == null){
            return ResultResponse.RETURN(3, "fail: studentId为必要参数", null);
        }

        Student resultStudent = studentService.updateStudent(student);
        return ResultResponse.RETURN(1, "success", resultStudent);

    }

    /**
     * 查询学生信息
     * @param pageStart
     * @param pageSize
     * @param request
     * @return
     */
    @PostMapping("student-queryList")
    public ResultResponse queryListStudent(int pageStart, int pageSize, HttpServletRequest request){

        Identity identity = (Identity)request.getAttribute("identity");
        boolean green = IdentityUtils.greenAdmin(identity);
        if (!green){
            return ResultResponse.RETURN(2, "fail: 权限不足,非管理员不能访问", null);
        }


        Map<String, Object> map = studentService.selectStudentList(pageStart, pageSize);
        return ResultResponse.RETURN(1, "success", map);
    }


}
