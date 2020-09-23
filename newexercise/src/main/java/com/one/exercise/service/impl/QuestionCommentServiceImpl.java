package com.one.exercise.service.impl;

import com.one.exercise.mapper.QuestionCommentMapper;
import com.one.exercise.pojo.QuestionComment;
import com.one.exercise.pojo.questioncomment.QuestionCommentQuery;
import com.one.exercise.service.QuestionCommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class QuestionCommentServiceImpl implements QuestionCommentService {

    @Resource
    private QuestionCommentMapper questionCommentMapper;

    @Override
    public QuestionComment saveComment(QuestionComment questionComment) {
        int i = questionCommentMapper.insertSelective(questionComment);
        if (i > 0){
            QuestionComment questionComment1 = questionCommentMapper.selectByPrimaryKey(questionComment);
            return questionComment1;
        }else {
            return null;
        }
    }

    @Override
    public List<QuestionCommentQuery> queryCommentList(Long questionId) {
        List<QuestionCommentQuery> questionCommentQueryList = questionCommentMapper.queryCommentList(questionId);
        return questionCommentQueryList;
    }

    @Override
    public int countComment(long questionId) {
        QuestionComment questionComment = new QuestionComment();
        questionComment.setQuestionId(questionId);
        int i = questionCommentMapper.selectCount(questionComment);
        return i;
    }
}
