package com.one.exercise.pojo;

import javax.persistence.*;

@Table(name = "student_collect_work")
public class StudentCollectWork {
    @Id
    @Column(name = "collect_id")
    private Long collectId;

    @Column(name = "work_id")
    private Long workId;

    @Column(name = "student_id")
    private Long studentId;

    /**
     * @return collect_id
     */
    public Long getCollectId() {
        return collectId;
    }

    /**
     * @param collectId
     */
    public void setCollectId(Long collectId) {
        this.collectId = collectId;
    }

    /**
     * @return work_id
     */
    public Long getWorkId() {
        return workId;
    }

    /**
     * @param workId
     */
    public void setWorkId(Long workId) {
        this.workId = workId;
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