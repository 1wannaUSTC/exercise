package com.one.exercise.pojo.notification;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 * 接受前端数据
 */
@Getter
@Setter
@ToString
public class NotificationBO {

    /**
     * 标题
     */
    private String title;


    /**
     * 内容
     */
    private String content;
}