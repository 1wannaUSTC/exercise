package com.one.exercise.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import javax.persistence.*;

@Getter
@Setter
@ToString
@Table(name = "question_comment_back")
public class QuestionCommentBack {
    /**
     * 问题评论回复id
     */
    @Id
    @Column(name = "back_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long backId;

    /**
     * 收到回复的评论id
     */
    @Column(name = "comment_id")
    private Long commentId;

    @Column(name = "back_send")
    private Long backSend;

    @Column(name = "back_receiver")
    private Long backReceiver;

    private String message;

    @Column(name = "this_time")
    private Date thisTime;

    @Column(name = "is_read")
    private Integer isRead;

    @Column(name = "send_face")
    private String sendFace;

    @Column(name = "send_nick_name")
    private String sendNickName;

    @Column(name = "receiver_face")
    private String receiverFace;

    @Column(name = "receiver_nick_name")
    private String receiverNickName;
}