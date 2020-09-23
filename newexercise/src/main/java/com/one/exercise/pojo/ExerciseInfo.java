package com.one.exercise.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "exercise_info")
public class ExerciseInfo {
    /**
     * 练习的综合信息id
     */
    @Id
    @Column(name = "info_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long infoId;

    /**
     * 学生id
     */
    @Column(name = "student_id")
    private Long studentId;

    /**
     * 困难题目数量
     */
    private Integer difficulty;

    @Column(name = "difficulty_correct")
    private Integer difficultyCorrect;

    /**
     * 一般题目数量
     */
    private Integer general;

    @Column(name = "general_correct")
    private Integer generalCorrect;

    /**
     * 简单题目数量
     */
    private Integer easy;

    @Column(name = "easy_correct")
    private Integer easyCorrect;

    /**
     * 正确数量
     */
    private Integer correct;

    /**
     * 错误
     */
    private Integer error;

    public ExerciseInfo(Long studentId) {
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