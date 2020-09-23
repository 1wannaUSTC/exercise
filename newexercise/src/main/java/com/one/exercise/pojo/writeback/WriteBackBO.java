package com.one.exercise.pojo.writeback;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Transient;

@Setter
@Getter
@ToString
public class WriteBackBO {

    /**
     * 评论id
     */
    private Long commentId;

    /**
     * 接收回复人（当前评论的student - id）
     */
    private Long backReceiver;

    /**
     * 回复内容
     */
    private String message;

    /**
     * 当前动态id
     */
    private Long dyId;

}
