package com.one.exercise.controller;

import com.alibaba.fastjson.JSONObject;
import com.one.exercise.pojo.*;
import com.one.exercise.service.StudentRelationTeacherService;
import com.one.exercise.service.TeacherService;
import com.one.exercise.utils.IdentityUtils;
import com.one.exercise.utils.PageUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("student-relation-teacher")
public class StudentRelationTeacherController {

    @Autowired
    private StudentRelationTeacherService studentRelationTeacherService;

    @Resource
    private TeacherService teacherService;

    /**
     * 获取关注自己的学生
     * 权限级别：教师专属
     */
    @PostMapping("query-relation-student")
    public ResultResponse queryRelationStudent(Integer startPage, Integer pageSize, HttpServletRequest request){
        Identity identity = (Identity)request.getAttribute("identity");
        long teacherId = identity.getId();
        boolean green = IdentityUtils.greenTeacher(identity);
        if (!green){
            return ResultResponse.RETURN(2, "fail: 权限不足", false);
        }

        // 参数校验
        if (startPage == null){
            startPage = 1;
        }
        if (pageSize == null){
            pageSize = 10;
        }

        /**
         * 1. 获取总数
         * 2. 生成分页信息
         * 3. 获取分页结果集
         */
        int total = studentRelationTeacherService.countStudentByTeacherId(teacherId);
        if (total == 0){
            return ResultResponse.RETURN(1, "success", null);
        }

        PageUtil pageUtil = new PageUtil(total, pageSize);

        List<Student> students = studentRelationTeacherService.selectStudentByTeacherId(teacherId, pageUtil.getStartIndex(startPage), pageSize);

        List<StudentVO> studentVOList = new ArrayList<>();

        students.forEach(item->{
            StudentVO studentVO = new StudentVO();
            BeanUtils.copyProperties(item, studentVO);
            studentVOList.add(studentVO);
        });

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pageInfo", pageUtil);
        jsonObject.put("studentList", studentVOList);
        return ResultResponse.RETURN(1, "success", jsonObject);

    }

    /**
     * 学生关注老师
     * 权限级别：学生专属
     */
    @Transactional
    @PostMapping("relation")
    public ResultResponse relationTeacher(Long teacherId, HttpServletRequest request){
        Identity identity = (Identity)request.getAttribute("identity");
        long id = identity.getId();
        boolean green = IdentityUtils.greenStudent(identity);
        if (!green){
            return ResultResponse.RETURN(2, "fail: 权限不足", false);
        }

        /**
         * 1. 获取学生id， 获取teacherId
         * 2. 添加到关联表
         * 3. 老师的关注数加1
         */

        int i = studentRelationTeacherService.studentRelationTeacher(teacherId, id);

        boolean result = false;
        if (i > 0){
            int i1 = teacherService.updateTeacherFollow(Math.toIntExact(teacherId), 1);
            result = i1 > 0;
        }

        return ResultResponse.RETURN(1, "success", result);
    }

    /**
     * 学生取消关注
     * 权限级别：学生专属
     */
    @Transactional
    @PostMapping("cancelRelation")
    public ResultResponse cancelRelationTeacher(Long teacherId, HttpServletRequest request){
        Identity identity = (Identity)request.getAttribute("identity");
        long id = identity.getId();
        boolean green = IdentityUtils.greenStudent(identity);
        if (!green){
            return ResultResponse.RETURN(2, "fail: 权限不足", false);
        }
        /**
         * 1. 获取学生id， 获取teacherId
         * 2. 删除关联关系
         * 3. 老师的关注数减去1
         */
        int i = studentRelationTeacherService.cancelRelationTeacher(teacherId, id);
        boolean result = false;
        if (i > 0){
            int i1 = teacherService.updateTeacherFollow(Math.toIntExact(teacherId), -1);
            result = i1 > 0;
        }
        return ResultResponse.RETURN(1, "success", result);
    }

    /**
     * 学生获取自己关注的老师
     * 权限级别：学生专属
     */
    @PostMapping("relationTeacherList")
    public ResultResponse relationTeacherList(@RequestParam(required = false, defaultValue = "1") Integer startPage,
                                              @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                              HttpServletRequest request){
        Identity identity = (Identity)request.getAttribute("identity");
        long studentId = identity.getId();
        boolean green = IdentityUtils.greenStudent(identity);
        if (!green){
            return ResultResponse.RETURN(2, "fail: 权限不足", false);
        }

        /**
         * 获取关注教师总数
         * 生成分页信息
         * 进行分页查询
         */
        int total = studentRelationTeacherService.countTeacherByStudentId(studentId);
        PageUtil pageInfo = new PageUtil(total, pageSize);

        List<Teacher> teacherList = studentRelationTeacherService.selectTeacherOfStudentRelation(studentId, pageInfo.getStartIndex(startPage), pageSize);

        return ResultResponse.RETURN(1, "success", teacherList);
    }

}
