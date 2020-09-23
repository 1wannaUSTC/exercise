package com.one.exercise.pojo;

import lombok.Data;

@Data
public class WorkBO {

    /**
     * 插入时，非必须
     */
    private Integer workId;

    /**
     * insert must
     */
    private String workName;

    private String major;

    private Integer subjectId;

    private String subjectName;

    private Integer result;

    private Integer teacherId;

    /**
     * 0未发布，1发布
     */
    private Integer state;
}