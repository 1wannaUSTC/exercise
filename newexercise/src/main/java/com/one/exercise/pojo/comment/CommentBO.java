package com.one.exercise.pojo.comment;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CommentBO {

    /**
     * 动态id
     */
    private Long dyId;

    /**
     * 评论内容
     */
    private String message;

}
