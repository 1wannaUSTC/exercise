package com.one.exercise.pojo.notification;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@ToString
@Table(name = "notification_relation")
public class NotificationRelation {
    @Id
    @Column(name = "relation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long relationId;

    /**
     * 对应消息id
     */
    @Column(name = "message_id")
    private Long messageId;

    /**
     * 发起人
     */
    private Long sender;

    /**
     * 接收者
     */
    private Long receiver;

    /**
     * 0未读、1已读
     */
    @Column(name = "is_read")
    private Integer isRead;

    /**
     * 阅读时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @Column(name = "read_time")
    private Date readTime;


}