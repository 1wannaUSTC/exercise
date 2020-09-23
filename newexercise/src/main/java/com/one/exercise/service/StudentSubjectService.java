package com.one.exercise.service;

import com.one.exercise.exception.InsertException;
import com.one.exercise.pojo.StudentSubject;
import com.one.exercise.pojo.Subject;

import java.util.List;

public interface StudentSubjectService {

    // 保存学生选择的科目，并返回具体的科目信息
    List<Subject> saveSubjects(List<StudentSubject> subjectList, Long studentId) throws InsertException;

    // 返回所选的所有课程
    List<Subject> querySubject(Long studentId);
}
