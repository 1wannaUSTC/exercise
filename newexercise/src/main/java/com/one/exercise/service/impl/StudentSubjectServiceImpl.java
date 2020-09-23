package com.one.exercise.service.impl;

import com.one.exercise.exception.InsertException;
import com.one.exercise.mapper.StudentSubjectMapper;
import com.one.exercise.mapper.SubjectMapper;
import com.one.exercise.pojo.StudentSubject;
import com.one.exercise.pojo.Subject;
import com.one.exercise.service.StudentSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentSubjectServiceImpl implements StudentSubjectService {

    @Autowired
    private StudentSubjectMapper studentSubjectMapper;

    @Autowired
    private SubjectMapper subjectMapper;

    @Override
    @Transactional
    public List<Subject> saveSubjects(List<StudentSubject> subjectList, Long studentId) throws InsertException {

        /**
         * 1、保存科目信息
         * 2、查询科目信息
         */
        try {
            studentSubjectMapper.insertStudentSubjectList(subjectList);
        }catch (Exception e){
            throw new InsertException("插入异常");
        }

        // 根据studentId 查找学生对应科目
        List<Subject> subjectListResult = subjectMapper.getSubjectById(studentId);

        return subjectListResult;
    }

    @Override
    public List<Subject> querySubject(Long studentId) {
        List<Subject> subjectList = subjectMapper.selectStudentSubject(studentId);
        return subjectList;
    }
}
