package com.one.exercise.pojo;

import lombok.Data;

import java.util.List;

@Data
public class WorkQ {

    private Integer workId;

    private String workName;

    private String major;

    private Integer subjectId;

    private String subjectName;

    private Integer result;

    private Integer teacherId;

    private String difficulty;

    /** 0未发布，1发布 */
    private Integer state;

    private List<QuestionBank> questionList;

}
