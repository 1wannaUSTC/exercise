package com.one.exercise.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import javax.persistence.*;

@Getter
@Setter
@ToString
@Table(name = "student_relation_teacher")
public class StudentRelationTeacher {
    /**
     * 学生老师关联id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 老师id
     */
    @Column(name = "teacher_id")
    private Long teacherId;

    /**
     * 学生id
     */
    @Column(name = "student_id")
    private Long studentId;

    /**
     * 关联时间
     */
    @Column(name = "relation_time")
    private Date relationTime;
}