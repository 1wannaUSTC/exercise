package com.one.exercise.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Setter
@Getter
@ToString
@Table(name = "dynamic_message")
public class DynamicMessage {
    /**
     * 动态id
     */
    @Id
    @Column(name = "dy_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dyId;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 发起人
     */
    private Long initiator;

    /**
     * 喜欢数
     */
    private Long like;

    /**
     * 评论数
     */
    private Long comment;

    /**
     * 时间
     */
    private Date time;

    /**
     * 有效性 0无效， 1有效
     */
    private Integer validity;

    @Transient
    private List<DynamicPicture> pictures;
}