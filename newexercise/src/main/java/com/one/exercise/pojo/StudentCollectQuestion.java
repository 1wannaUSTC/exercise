package com.one.exercise.pojo;

import javax.persistence.*;

@Table(name = "student_collect_question")
public class StudentCollectQuestion {
    /**
     * 收藏id
     */
    @Id
    @Column(name = "collect_id")
    private Long collectId;

    @Column(name = "question_id")
    private Long questionId;

    @Column(name = "student_id")
    private Long studentId;

    /**
     * 获取收藏id
     *
     * @return collect_id - 收藏id
     */
    public Long getCollectId() {
        return collectId;
    }

    /**
     * 设置收藏id
     *
     * @param collectId 收藏id
     */
    public void setCollectId(Long collectId) {
        this.collectId = collectId;
    }

    /**
     * @return question_id
     */
    public Long getQuestionId() {
        return questionId;
    }

    /**
     * @param questionId
     */
    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    /**
     * @return student_id
     */
    public Long getStudentId() {
        return studentId;
    }

    /**
     * @param studentId
     */
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
}