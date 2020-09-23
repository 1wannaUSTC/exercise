package com.one.exercise.mapper;

import com.one.exercise.pojo.Student;
import com.one.exercise.pojo.StudentRelationTeacher;
import com.one.exercise.pojo.Teacher;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface StudentRelationTeacherMapper extends Mapper<StudentRelationTeacher> {

    // 通过教师id获取，关注教师的所有学生列表
    List<Student> selectStudentByTeacherId(Long teacherId, Integer startIndex, Integer pageSize);
    // 获取id列表：学生id
    List<Long> selectStudentIdByTeacherId(long teacherId);
    // 获取所有关联：获取老师id
    @Select("SELECT teacher_id FROM student_relation_teacher WHERE student_id=#{studentId}")
    List<Long> selectTeacherIdByStudentId(long studentId);

    // 通过教师id获取，关注的学生数量
    int countStudentByTeacherId(Long teacherId);

    // 创建一组关联，学生关注老师
    int insertRelation(StudentRelationTeacher srt);
    // 取消一组关联
    int cancelRelationTeacher(Long teacherId, Long studentId);

    @Select("SELECT * FROM `exercise`.`teacher`  " +
            "WHERE teacher_id IN (SELECT teacher_id FROM student_relation_teacher WHERE student_id = #{studentId}) " +
            "LIMIT #{startIndex},#{pageSize}")
    List<Teacher> selectTeacherOfStudentRelation(long studentId, int startIndex, Integer pageSize);

    @Select("SELECT COUNT(*) FROM `exercise`.`student_relation_teacher` WHERE student_id = #{studentId}")
    int countTeacherByStudentId(long studentId);
}