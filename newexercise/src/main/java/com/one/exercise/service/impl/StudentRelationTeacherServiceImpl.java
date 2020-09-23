package com.one.exercise.service.impl;

import com.one.exercise.mapper.StudentRelationTeacherMapper;
import com.one.exercise.pojo.Student;
import com.one.exercise.pojo.StudentRelationTeacher;
import com.one.exercise.pojo.Teacher;
import com.one.exercise.service.StudentRelationTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StudentRelationTeacherServiceImpl implements StudentRelationTeacherService {

    @Resource
    private StudentRelationTeacherMapper studentRelationTeacherMapper;

    @Override
    public List<Student> selectStudentByTeacherId(Long teacherId, Integer startIndex, Integer pageSize) {
        List<Student> students = studentRelationTeacherMapper.selectStudentByTeacherId(teacherId, startIndex, pageSize);
        return students;
    }

    @Override
    public List<Long> selectStudentIdByTeacherId(long teacherId) {
        List<Long> idList = studentRelationTeacherMapper.selectStudentIdByTeacherId(teacherId);
        return idList;
    }

    @Override
    public List<Long> selectTeacherIdByStudentId(long studentId) {
        List<Long> idList = studentRelationTeacherMapper.selectTeacherIdByStudentId(studentId);
        return idList;
    }

    @Override
    public int countStudentByTeacherId(Long teacherId) {
        int i = studentRelationTeacherMapper.countStudentByTeacherId(teacherId);
        return i;
    }

    @Override
    public int studentRelationTeacher(Long teacherId, Long studentId) {
        StudentRelationTeacher srt = new StudentRelationTeacher();
        srt.setStudentId(studentId);
        srt.setTeacherId(teacherId);
        int i = studentRelationTeacherMapper.insertRelation(srt);
        return i;
    }

    @Override
    public int cancelRelationTeacher(Long teacherId, Long studentId) {
        return studentRelationTeacherMapper.cancelRelationTeacher(teacherId, studentId);
    }

    @Override
    public List<Teacher> selectTeacherOfStudentRelation(long studentId, int startIndex, Integer pageSize) {
        return studentRelationTeacherMapper.selectTeacherOfStudentRelation(studentId, startIndex, pageSize);
    }
    @Override
    public int countTeacherByStudentId(long studentId) {
        return studentRelationTeacherMapper.countTeacherByStudentId(studentId);
    }
}
