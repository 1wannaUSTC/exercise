package com.one.exercise.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 * 直接当作CommentVO使用
 */
@Setter
@Getter
@ToString
@Table(name = "comment")
public class Comment {
    /**
     * 评论id
     */
    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    /**
     * 评论消息
     */
    private String message;

    /**
     * 评论者
     */
    private Long initiator;

    /**
     * 时间
     */
    private Date time;

    /**
     * 动态id
     */
    @Column(name = "dy_id")
    private Long dyId;

    /**
     * 是否已读
     */
    @Column(name = "is_read")
    private Integer isRead;

}