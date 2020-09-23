package com.one.exercise.service;

import com.one.exercise.pojo.Comment;
import com.one.exercise.pojo.comment.CommentDTO;

import java.util.List;

public interface CommentService {

    // 添加一条评论
    Comment comment(Comment comment);

    // 获取评论列表
    List<CommentDTO> commentList(Long dyId);

    // 删除评论
    boolean deleteComment(Comment comment);
}
