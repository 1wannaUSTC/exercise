package com.one.exercise.service.impl;

import com.one.exercise.mapper.QuestionCommentBackMapper;
import com.one.exercise.pojo.QuestionCommentBack;
import com.one.exercise.service.QuestionCommentBackService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class QuestionCommentBackServiceImpl implements QuestionCommentBackService {

    @Resource
    private QuestionCommentBackMapper questionCommentBackMapper;

    @Override
    @Transactional
    public QuestionCommentBack saveBack(QuestionCommentBack questionCommentBack) {

        int i = questionCommentBackMapper.insertSelective(questionCommentBack);
        if (i > 0){
            QuestionCommentBack questionCommentBack1 = questionCommentBackMapper.selectByPrimaryKey(questionCommentBack);
            return questionCommentBack1;
        }else {
            return null;
        }

    }
}
