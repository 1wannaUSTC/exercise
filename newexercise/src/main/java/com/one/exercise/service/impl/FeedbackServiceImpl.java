package com.one.exercise.service.impl;

import com.one.exercise.mapper.FeedbackMapper;
import com.one.exercise.pojo.Feedback;
import com.one.exercise.service.FeedbackService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Resource
    private FeedbackMapper feedbackMapper;

    @Override
    public Feedback selectFeedbackById(Long feedbackId) {
        Feedback feedback = feedbackMapper.selectFeedbackById(feedbackId);
        return feedback;
    }

    @Override
    public int insertFeedback(Feedback feedback) {
        int i = feedbackMapper.insertSelective(feedback);
        return i;
    }
}
