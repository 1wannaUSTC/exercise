package com.one.exercise.pojo;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Null;

@Data
@Table(name = "work")
public class Work {

    /**
     * 插入时，非必须
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
    @Column(name = "work_id")
    private Integer workId;

    /**
     * insert must
     */
    @Null(message = "workName不能为空")
    @Column(name = "work_name")
    private String workName;

    private String major;

    @Column(name = "subject_id")
    private Integer subjectId;

    @Column(name = "subject_name")
    private String subjectName;

    private Integer result;

    @Column(name = "teacher_id")
    private Integer teacherId;

    @Column(name = "difficulty")
    private String difficulty;

    /**
     * 0未发布，1发布
     */
    private Integer state;
}