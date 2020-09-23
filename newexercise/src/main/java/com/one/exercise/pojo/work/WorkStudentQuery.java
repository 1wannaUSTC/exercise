package com.one.exercise.pojo.work;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
public class WorkStudentQuery {

    private Integer workId;

    private String workName;

    // 学科
    private Integer subjectId;

    // 分数
    private Integer result;

    // 老师
    private Integer teacherId;

    // 难度
    private String difficulty;

}
