package com.one.exercise.service.impl;

import com.one.exercise.mapper.SubjectMapper;
import com.one.exercise.pojo.Subject;
import com.one.exercise.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectMapper subjectMapper;

    @Override
    public List<Subject> selectSubjectList() {
        List<Subject> subjects = subjectMapper.selectAll();
        return subjects;
    }

    @Override
    public Subject selectSubjectById(Integer subjectId) {
        Subject subject = subjectMapper.selectByPrimaryKey(subjectId);
        return subject;
    }

    @Override
    public List<Subject> selectSubjectListByTeacherId(Integer teacherId) {
        return subjectMapper.selectSubjectListByTeacherId(teacherId);
    }

    @Override
    public List<Subject> insertSubjectListIgnore(Long teacherId, List<Integer> list) {
        return subjectMapper.insertSubjectListIgnore(teacherId, list);
    }
}
