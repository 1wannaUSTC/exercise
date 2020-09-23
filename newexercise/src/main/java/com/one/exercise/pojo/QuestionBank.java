package com.one.exercise.pojo;

import lombok.Data;

import javax.persistence.*;

/**
 * 这是题库pojo
 */
@Data
@Table(name = "question_bank")
public class QuestionBank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Integer questionId;

    @Column(name = "teacher_id")
    private Integer teacherId;

    /** 是否为公共试题：1是，0不是 */
    @Column(name = "is_public")
    private Integer isPublic;

    /** 专业 */
    private String major;

    /** 学科ID */
    @Column(name = "subject_id")
    private Integer subjectId;

    /** 学科 */
    private String subject;

    /** 习题类型：选择(单选、多选)、填空、判断、简答 */
    private String type;

    /** 问题 */
    private String question;

    /** A、xxx&uuid&B、xxx&uuid&C、xxx&uuid&D、xxx */
    private String option;

    /** 用于存放答案：多选(a,b,c)/填空(xx&uuid&xxx&uuid&xxx) */
    private String answer;

    /** 用于分隔 */
    @Column(name = "uuid_short")
    private String uuidShort;

    /** 分值 */
    private Integer score;

    /** 难度 */
    private String difficulty;

    /** 数据有效性 */
    private Integer validity;

    /** 正确 */
    private Integer correct;

    /** 错误 */
    private Integer error;

}