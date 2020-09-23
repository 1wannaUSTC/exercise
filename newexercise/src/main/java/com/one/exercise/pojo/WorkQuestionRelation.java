package com.one.exercise.pojo;

import lombok.Data;

import javax.persistence.*;

/**
 * 作业与问题的映射表
 */
@Data
@Table(name = "work_question_relation")
public class WorkQuestionRelation {

    @Id
    @Column(name = "mapper_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer mapperId;

    @Column(name = "question_id")
    private Integer questionId;

    @Column(name = "work_id")
    private Integer workId;



}
