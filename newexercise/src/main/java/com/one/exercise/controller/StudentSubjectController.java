package com.one.exercise.controller;

import com.one.exercise.exception.InsertException;
import com.one.exercise.pojo.ResultResponse;
import com.one.exercise.pojo.StudentSubject;
import com.one.exercise.pojo.Subject;
import com.one.exercise.service.StudentSubjectService;
import com.one.exercise.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("student-subject")
public class StudentSubjectController {

    @Autowired
    private StudentSubjectService studentSubjectService;

    /** 学生选课 */
    @GetMapping("choose/{studentId}")
    public ResultResponse chooseSubject(String subjectsStr, @PathVariable Long studentId){

        String[] split = subjectsStr.trim().split("");
        List<Long> subjects = new ArrayList<>();

        if (split == null && split.length == 0){
            return ResultResponse.RETURN(2, "fail:subjectsStr为空", null);
        }
        try {
            for (String s : split) {
                if (!Tools.isNullStr(s.trim())){
                    subjects.add(Long.valueOf(s.trim()));
                }
            }
        }catch (Exception e){
            return ResultResponse.RETURN(2, "fail:subjectsStr参数有误：" + e.getMessage(), null);
        }

        List<StudentSubject> subjectList = new ArrayList<>();
        subjects.forEach(item->{
            StudentSubject studentSubject = new StudentSubject();
            studentSubject.setSubjectId(item);
            studentSubject.setStudentId(studentId);
            subjectList.add(studentSubject);
            System.out.println(item);
        });

        // 保存、并返回对应科目信息
        try {
            List<Subject> resultSubjects = studentSubjectService.saveSubjects(subjectList, studentId);
            return ResultResponse.RETURN(1, "success", resultSubjects);
        } catch (InsertException e) {
            e.printStackTrace();
            return ResultResponse.RETURN(2, "fail:"+e.getMessage(), null);
        }

    }

}
