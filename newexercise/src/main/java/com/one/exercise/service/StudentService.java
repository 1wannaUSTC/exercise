package com.one.exercise.service;

import com.one.exercise.exception.InsertException;
import com.one.exercise.exception.SelectException;
import com.one.exercise.pojo.Student;
import com.one.exercise.pojo.StudentVO;
import org.springframework.stereotype.Service;

import java.util.Map;


public interface StudentService {

    /** 查 */
    // 微信登录----------------暂时废弃
    StudentVO wxLogin();
    // 通过studentId获取student信息
    StudentVO getStudentById(Long studentId);
    // 通过OpenId获取student信息
    StudentVO getStudentById(String openId);

    Map<String,Object> selectStudentList(int pageStart, int pageSize);

    /** 增 */
    // 第一次微信登录
    Long wxJoin(Student student);
    // 添加学生
    Student insertStudent(Student student) throws InsertException;

    /** 删 */
    Student logicDelete(Long studentId);

    /** 改 */
    Student updateStudent(Student student);

}
