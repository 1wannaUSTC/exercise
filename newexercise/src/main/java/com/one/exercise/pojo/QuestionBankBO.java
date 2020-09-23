package com.one.exercise.pojo;

import lombok.Data;

/**
 * 返回参数
 */
@Data
public class QuestionBankBO {

    private Integer teacherId;

    /**
     * 是否为公共试题：1是，0不是
     */
    private Integer isPublic;

    /**
     * 专业
     */
    private String major;

    /**
     * 学科
     */
    private String subject;

    /**
     * 习题类型：选择(单选、多选)、填空、判断、简答
     */
    private String type;

    /**
     * 问题
     */
    private String question;

    /**
     * A、xxx&uuid&B、xxx&uuid&C、xxx&uuid&D、xxx
     */
    private String option;

    private String[] options;

    /**
     * 用于存放答案：多选(a,b,c)
     */
    private String answer;

    /**
     * 用于分隔
     */
    private String uuidShort;

    /**
     * 分值
     */
    private Integer score;
}