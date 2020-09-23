package com.one.exercise.pojo.questioncomment;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@ToString
public class QuestionCommentVO {

    private Long commentId;

    private Long questionId;

    private String message;

    /**
     * 发起人
     */
    private Long initiator;

    @JsonFormat(pattern = "MM月dd日 HH:mm:ss",timezone="GMT+8")
    private Date thisTime;

    private String face;

    private String nickName;
}