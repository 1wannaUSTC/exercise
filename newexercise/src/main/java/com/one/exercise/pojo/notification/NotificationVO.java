package com.one.exercise.pojo.notification;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 * 返回到前端的数据
 */
@Getter
@Setter
@ToString
public class NotificationVO {

    /**
     * 通知id
     */
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
     * 内容
     */
    private String content;

    /**
     * 发起时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date date;

    /**
     * 已读数量
     */
    private Integer reads;
}