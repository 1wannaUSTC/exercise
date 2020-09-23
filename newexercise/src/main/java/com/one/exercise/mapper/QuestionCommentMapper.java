package com.one.exercise.mapper;

import com.one.exercise.pojo.QuestionComment;
import com.one.exercise.pojo.questioncomment.QuestionCommentQuery;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface QuestionCommentMapper extends Mapper<QuestionComment> {

    List<QuestionCommentQuery> queryCommentList(long questionId);

}