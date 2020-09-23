package com.one.exercise.pojo.admire;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class AdmireVO {

    private Long admireId;

    private Long dyId;

    /**
     * 点赞人
     */
    private Long admireLiker;
    /**
     * 呢称
     */
    private String nickname;
    /**
     * 头像
     */
    private String face;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;

    /**
     * 时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date time;

    /**
     * 是否已读
     */
    private Integer isRead;

}
