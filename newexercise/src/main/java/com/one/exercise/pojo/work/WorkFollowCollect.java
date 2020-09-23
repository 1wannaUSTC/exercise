package com.one.exercise.pojo.work;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class WorkFollowCollect {

    private Integer workId;

    private String workName;

    private String major;


    private Integer subjectId;


    private String subjectName;

    private Integer result;

    private Integer teacherId;

    private String difficulty;

    /**
     * 0未发布，1发布
     */
    private Integer state;

}
