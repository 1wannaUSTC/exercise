package com.one.exercise.mapper;

import com.one.exercise.pojo.Feedback;
import tk.mybatis.mapper.common.Mapper;


public interface FeedbackMapper extends Mapper<Feedback> {

    Feedback selectFeedbackById(Long feedbackId);

}