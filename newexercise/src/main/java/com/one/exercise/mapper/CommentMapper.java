package com.one.exercise.mapper;

import com.one.exercise.pojo.Comment;
import com.one.exercise.pojo.comment.CommentDTO;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface CommentMapper extends Mapper<Comment>, MySqlMapper<Comment> {

    List<CommentDTO> getListByDyId(Long dyId);
}