package com.one.exercise.service.impl;

import com.one.exercise.mapper.FeedbackPictureMapper;
import com.one.exercise.pojo.FeedbackPicture;
import com.one.exercise.service.FeedbackPictureService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FeedbackPictureServiceImpl implements FeedbackPictureService {

    @Resource
    private FeedbackPictureMapper feedbackPictureMapper;

    @Override
    public int insertPictureList(List<FeedbackPicture> feedbackPictureList) {
        int i = feedbackPictureMapper.insertList(feedbackPictureList);
        return i;
    }
}
