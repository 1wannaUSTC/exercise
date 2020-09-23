package com.one.exercise.pojo.dynamicmessage;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.one.exercise.pojo.DynamicPicture;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * 一对多DTO
 * 发布动态、
 */
@Setter
@Getter
@ToString
public class DynamicMessageDTO {

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
     * 发起人-studentId
     */
    private Long initiator;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String face;

    /**
     * 喜欢数
     */
    private Long like;

    /**
     * 是否被点赞
     */
    private Boolean isLike;

    /**
     * 评论数
     */
    private Long comment;

    /**
     * 时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date time;

    /**
     * 有效性 0无效， 1有效
     */
    private Integer validity;

    /**
     * 动态图片
     */
    private List<DynamicPicture> pictures;

}
