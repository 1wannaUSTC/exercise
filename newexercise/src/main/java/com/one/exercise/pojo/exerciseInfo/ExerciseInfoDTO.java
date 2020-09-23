package com.one.exercise.pojo.exerciseInfo;

import lombok.*;


/**
 * 用于INFO信息的更新
 */
@Setter
@Getter
@ToString
public class ExerciseInfoDTO {

    /**
     * 练习的综合信息id
     */
    private Long infoId;

    /**
     * 学生id
     */
    private Long studentId;

    /**
     * 困难题目数量
     */
    private Integer difficulty;

    private Integer difficultyCorrect;

    /**
     * 一般题目数量
     */
    private Integer general;

    private Integer generalCorrect;

    /**
     * 简单题目数量
     */
    private Integer easy;

    private Integer easyCorrect;

    /**
     * 正确数量
     */
    private Integer correct;

    /**
     * 错误
     */
    private Integer error;

    public ExerciseInfoDTO(Long studentId) {
        this.studentId = studentId;
        this.difficulty = 0;
        this.difficultyCorrect = 0;
        this.general = 0;
        this.generalCorrect = 0;
        this.easy = 0;
        this.easyCorrect = 0;
        this.correct = 0;
        this.error = 0;
    }
}