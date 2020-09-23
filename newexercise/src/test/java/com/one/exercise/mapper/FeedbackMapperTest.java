package com.one.exercise.mapper;

import com.one.exercise.ExerciseApplicationTests;
import com.one.exercise.pojo.Feedback;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class FeedbackMapperTest extends ExerciseApplicationTests {

    @Autowired
    private FeedbackMapper feedbackMapper;

    @Test
    public void selectFeedbackById(){
        Feedback feedback = feedbackMapper.selectFeedbackById((long) 1);
        System.out.println(feedback);
    }

}
