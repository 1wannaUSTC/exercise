package com.one.exercise.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import javax.persistence.*;

@Setter
@Getter
@ToString
@Table(name = "write_back")
public class WriteBack {

    /**
     * 当前动态id
     */
    @Transient
    private Long dyId;

    /**
     * 回复id
     */
    @Id
    @Column(name = "back_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long backId;

    /**
     * 评论id
     */
    @Column(name = "comment_id")
    private Long commentId;

    /**
     * 发起回复人
     */
    @Column(name = "back_sender")
    private Long backSender;

    /**
     * 收到回复
     */
    @Column(name = "back_receiver")
    private Long backReceiver;

    /**
     * 回复内容
     */
    private String message;

    /**
     * 时间
     */
    private Date time;

    /**
     * 是否已读
     */
    @Column(name = "is_read")
    private Integer isRead;
}