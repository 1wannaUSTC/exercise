package com.one.exercise.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import javax.persistence.*;

@Getter
@Setter
@ToString
@Table(name = "exercise_log")
public class ExerciseLog {
    /**
     * 练习日志id
     */
    @Id
    @Column(name = "log_id")
    private Long logId;

    /**
     * 学生id
     */
    @Column(name = "student_id")
    private Long studentId;

    /**
     * 问题id
     */
    @Column(name = "question_id")
    private Long questionId;

    /**
     * 是否正确0错误，1正确
     */
    @Column(name = "is_correct")
    private Integer isCorrect;

    /**
     * 答题时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @Column(name = "finish_time")
    private Date finishTime;
}