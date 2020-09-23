package com.one.exercise.pojo;

import lombok.Data;

/**
 * 接收参数
 */
@Data
public class QuestionBankVO {

    private Integer teacherId;

    /** 是否为公共试题：1是，0不是 */
    private Integer isPublic = 0;

    /** 专业 */
    private String major;

    /** 学科ID */
    private Integer subjectId;

    /** 学科 */
    private String subject;

    /** 习题类型：选择(单选、多选)、填空、判断、简答 */
    private String type;

    /** 问题 */
    private String question;

    /** A、xxx &uuid& B、xxx &uuid& C、xxx &uuid &D、xxx */
    private String option;

    /** 用于存放答案：多选(a,b,c)/填空(xx &uuid& xxx &uuid& xxx) */
    private String answer;

    /** 用于分隔 */
    private String uuidShort;

    /** 分值 */
    private Integer score = 5;

    /** 难度 */
    private String difficulty = "一般";
}