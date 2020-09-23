package com.one.exercise.service.impl;

import com.one.exercise.exception.InsertException;
import com.one.exercise.mapper.StudentMapper;
import com.one.exercise.pojo.Student;
import com.one.exercise.pojo.StudentVO;
import com.one.exercise.service.StudentService;
import com.one.exercise.utils.PageUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {

    @Resource
    private StudentMapper studentMapper;

    @Override
    public StudentVO wxLogin() {
        return null;
    }

    @Override
    public StudentVO getStudentById(Long studentId) {

        Student student = new Student();
        student.setStudentId(studentId);
        Student result = studentMapper.selectByPrimaryKey(student);

        StudentVO studentVO = new StudentVO();
        BeanUtils.copyProperties(result, studentVO);

        return studentVO;
    }

    @Override
    public StudentVO getStudentById(String openId) {
        Student student = new Student();
        student.setOpenId(openId);
        student = studentMapper.selectOne(student);

        StudentVO studentVO = null;
        if (student != null){
            studentVO = new StudentVO();
            BeanUtils.copyProperties(student, studentVO);
        }

        return studentVO;
    }

    @Override
    public Map<String,Object> selectStudentList(int pageStart, int pageSize) {
        Student student = new Student();
        student.setValidity(1);
        int total = studentMapper.selectCount(student);

        PageUtil pageUtil = new PageUtil(total, pageSize);
        List<Student> studentList = studentMapper.selectStudentList(pageUtil.getStartIndex(pageStart), pageSize);
        Map<String,Object> map = new HashMap<>();
        map.put("pageInfo", pageUtil);
        map.put("studentList", studentList);
        return map;
    }

    @Override
    public Long wxJoin(Student student) {
        long i = studentMapper.insertSelective(student);
        return i;
    }

    @Override
    @Transactional
    public Student insertStudent(Student student) throws InsertException {

        student.setStudentId(null);
        student.setOpenId(null);
        student.setValidity(null);

        Student resultStudent = null;

        try {
            int i = studentMapper.insertSelective(student);
            resultStudent = studentMapper.selectByPrimaryKey(student.getStudentId());
        }catch (Exception e){
            e.printStackTrace();
            throw new InsertException("创建的学生可能已经存在");
        }

        return resultStudent;

    }

    @Override
    public Student logicDelete(Long studentId) {
        Student student = new Student();
        student.setStudentId(studentId);
        student.setValidity(0);
        Student resultStudent = null;
        int i = studentMapper.updateByPrimaryKeySelective(student);
        resultStudent = studentMapper.selectByPrimaryKey(student.getStudentId());
        return resultStudent;
    }

    @Override
    public Student updateStudent(Student student) {
        student.setOpenId(null);
        student.setValidity(null);
        studentMapper.updateByPrimaryKeySelective(student);
        System.out.println(student);
        Student student1 = studentMapper.selectByPrimaryKey(student.getStudentId());
        return student1;
    }
}
