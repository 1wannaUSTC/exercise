package com.one.exercise.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@Table(name = "admire")
public class Admire {
    /**
     * 点赞id
     */
    @Id
    @Column(name = "admire_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long admireId;

    @Column(name = "dy_id")
    private Long dyId;

    /**
     * 点赞人
     */
    @Column(name = "admire_liker")
    private Long admireLiker;

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