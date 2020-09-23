package com.one.exercise.pojo.work;

import com.one.exercise.pojo.QuestionBank;
import com.one.exercise.pojo.question.QuestionDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class WorkQDTO {

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

    private Boolean teacherIsFollow;

    private Boolean workIsCollect;

    private List<QuestionDTO> questions;

}
