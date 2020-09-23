package com.one.exercise.pojo.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.one.exercise.pojo.writeback.WriteBackDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@ToString
public class CommentDTO {

    /**
     * 评论id
     */
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
     * 评论者呢称
     */
    private String nickname;

    /**
     * 评论者头像
     */
    private String avatar;

    /**
     * 时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date time;

    /**
     * 动态id
     */
    private Long dyId;

    /**
     * 当前评论的回复列表
     */
    private List<WriteBackDTO> writeBackList;

}
