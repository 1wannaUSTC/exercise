package com.one.exercise.mapper;

import com.one.exercise.ExerciseApplicationTests;
import com.one.exercise.pojo.questioncomment.QuestionCommentQuery;
import com.one.exercise.service.impl.QuestionCommentServiceImpl;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

public class QuestionCommentMapperTest extends ExerciseApplicationTests {

    @Resource
    private QuestionCommentMapper questionCommentMapper;

    @Resource
    private QuestionCommentServiceImpl questionCommentService;

    @Test
    public void queryCommentList(){
        List<QuestionCommentQuery> questionCommentQueries =
                questionCommentMapper.queryCommentList(1);
        System.out.println(questionCommentQueries);
    }

    @Test
    public void countComment(){
        int i = questionCommentService.countComment(1);
        System.out.println(i);
    }

}
