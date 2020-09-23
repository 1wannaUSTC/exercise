package com.one.exercise.mapper;

import com.one.exercise.pojo.ExerciseInfo;
import com.one.exercise.pojo.exerciseInfo.ExerciseInfoDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

public interface ExerciseInfoMapper extends Mapper<ExerciseInfo> {

    @Update("UPDATE `exercise`.`exercise_info`\n" +
            "SET `difficulty` = `difficulty` + #{exerciseInfoDTO.difficulty}, \n" +
            "`difficulty_correct` = `difficulty_correct` + #{exerciseInfoDTO.difficultyCorrect}, \n" +
            "`general` = `general` + #{exerciseInfoDTO.general}, \n" +
            "`general_correct` = #{exerciseInfoDTO.generalCorrect}, \n" +
            "`easy` = `easy` + #{exerciseInfoDTO.easy}, \n" +
            "`easy_correct` = `easy_correct` + #{exerciseInfoDTO.easyCorrect}, \n" +
            "`correct` = `correct` + #{exerciseInfoDTO.correct}, \n" +
            "`error` = `error` + #{exerciseInfoDTO.error}\n" +
            "WHERE `student_id` = #{exerciseInfoDTO.studentId}")
    int updateInfo(@Param("exerciseInfoDTO") ExerciseInfoDTO exerciseInfoDTO);
}