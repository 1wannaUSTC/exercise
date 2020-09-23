package com.one.exercise.pojo.questioncomment;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@ToString
public class QuestionCommentQuery {

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

    private List<QuestionCommentBackQuery> backs;
}