package com.one.exercise.mapper;

import com.one.exercise.mapper.custom.SubjectCustom;
import com.one.exercise.pojo.Subject;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SubjectMapper extends Mapper<Subject> {

    /**
     * 根据studentId获取课程
     * 1. 根据studentId获取课程列表
     * 2. 根据subjectId列表获取对应课程名称
     * @param studentId
     * @return
     */
    @Select(
            "SELECT * FROM `exercise`.`subject` WHERE subject_id in " +
                    "(" +
                        "SELECT subject_id FROM `exercise`.`student_subject` WHERE student_id=#{studentId}" +
                    ")"
    )
    List<Subject> getSubjectById(Long studentId);

    @Select("SELECT s.* FROM `subject` s, teacher_subject ts WHERE s.subject_id = ts.subject_id AND teacher_id = #{teacherId}")
    List<Subject> selectSubjectListByTeacherId(Integer teacherId);

    @SelectProvider(type = SubjectCustom.class, method = "insertSubjectListIgnore")
    List<Subject> insertSubjectListIgnore(
            @Param("teacherId") Long teacherId,
            @Param("list") List<Integer> list
    );

    @Select("SELECT * FROM `subject` WHERE subject_id in (SELECT `subject_id` FROM student_subject WHERE student_id = #{studentId})")
    List<Subject> selectStudentSubject(Long studentId);
}
