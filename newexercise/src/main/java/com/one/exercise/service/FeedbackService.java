package com.one.exercise.service;

import com.one.exercise.pojo.Feedback;

public interface FeedbackService {

    /** 通过id获取反馈信息 */
    Feedback selectFeedbackById(Long feedbackId);

    /** 添加反馈信息 */
    int insertFeedback(Feedback feedback);
}
