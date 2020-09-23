package com.one.exercise.service;

import com.one.exercise.pojo.QuestionComment;
import com.one.exercise.pojo.questioncomment.QuestionCommentQuery;

import java.util.List;

public interface QuestionCommentService {

    QuestionComment saveComment(QuestionComment questionComment);

    List<QuestionCommentQuery> queryCommentList(Long questionId);

    int countComment(long questionId);
}
