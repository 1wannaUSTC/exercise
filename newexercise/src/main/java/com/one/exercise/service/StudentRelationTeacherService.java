package com.one.exercise.service;

import com.one.exercise.pojo.Student;
import com.one.exercise.pojo.Teacher;

import java.util.List;

public interface StudentRelationTeacherService {

    /**
     * 分页返回关注老师的学生
     * @param teacherId
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<Student> selectStudentByTeacherId(Long teacherId, Integer startIndex, Integer pageSize);

    /**
     * 学生关注老师：获取学生id列表
     */
    List<Long> selectStudentIdByTeacherId(long teacherId);

    /**
     * 学生关注老师：获取老师id列表
     */
    List<Long> selectTeacherIdByStudentId(long studentId);

    /**
     * 统计关注老师的学生数量
     * @param teacherId
     * @return
     */
    int countStudentByTeacherId(Long teacherId);

    /**
     * 学生关注教师
     * @return
     */
    int studentRelationTeacher(Long teacherId, Long studentId);

    /** 学生取消关注 */
    int cancelRelationTeacher(Long teacherId, Long studentId);

    /** 获取学生关注的老师列表 */
    List<Teacher> selectTeacherOfStudentRelation(long studentId, int startIndex, Integer pageSize);
    /** 获取学生关注的老师数量 */
    int countTeacherByStudentId(long studentId);
}
