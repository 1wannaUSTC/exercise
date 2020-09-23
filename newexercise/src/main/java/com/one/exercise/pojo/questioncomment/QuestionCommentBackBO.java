package com.one.exercise.pojo.questioncomment;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Setter
@Getter
@ToString
public class QuestionCommentBackBO {

    /**
     * 收到回复的评论id
     */
    private Long commentId;

    // private Long backSend;

    private Long backReceiver; // 接收者id

    private String message;

    // private String sendFace;

    // private String sendNickName;

    // private String receiverFace;

    // private String receiverNickName;

}