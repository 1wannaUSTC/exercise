package com.one.exercise.mapper;

import com.one.exercise.pojo.StudentCollectQuestion;
import org.apache.ibatis.annotations.Delete;
import tk.mybatis.mapper.common.Mapper;

public interface StudentCollectQuestionMapper extends Mapper<StudentCollectQuestion> {

    @Delete("DELETE FROM `exercise`.`student_collect_question` WHERE `question_id`=#{workId} AND `student_id` =#{studentId}")
    int cancelCollectQuestion(Long workId, Long studentId);

    int countCollectQuestion(StudentCollectQuestion studentCollectQuestion);
}