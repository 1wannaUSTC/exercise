package com.one.exercise.pojo.notification;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import javax.persistence.*;

@Getter
@Setter
@ToString
@Table(name = "notification")
public class Notification {
    /**
     * 通知id
     */
    @Id
    @Column(name = "message_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    /**
     * 发起人
     */
    private Integer sender;

    /**
     * 标题
     */
    private String title;

    /**
     * 发起时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date date;

    /**
     * 已读数量
     */
    @Column(name = "`reads`")
    private Integer reads;

    /**
     * 有效数据: 0无效、1有效、2灰色数据
     */
    private Integer validity;

    /**
     * 内容
     */
    private String content;
}