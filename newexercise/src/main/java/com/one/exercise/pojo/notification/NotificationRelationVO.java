package com.one.exercise.pojo.notification;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@ToString
public class NotificationRelationVO {

    private Long relationId;

    /**
     * 对应消息id
     */
    private Long messageId;

    /**
     * 0未读、1已读
     */
    private Integer isRead;

    /**
     * 阅读时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date readTime;


}