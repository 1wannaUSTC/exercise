package com.one.exercise.pojo.questioncomment;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
public class QuestionCommentBackVO {
    /**
     * 问题评论回复id
     */
    private Long backId;

    /**
     * 收到回复的评论id
     */
    private Long commentId;

    private Long backSend;

    private Long backReceiver;

    private String message;

    @JsonFormat(pattern = "MM月dd日 HH:mm:ss",timezone="GMT+8")
    private Date thisTime;

    private Integer isRead;

    private String sendFace;

    private String sendNickName;

    private String receiverFace;

    private String receiverNickName;
}