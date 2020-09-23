package com.one.exercise.service;

import com.one.exercise.ExerciseApplicationTests;
import com.one.exercise.mapper.ExerciseInfoMapper;
import com.one.exercise.pojo.ExerciseInfo;
import com.one.exercise.pojo.exerciseInfo.ExerciseInfoDTO;
import org.junit.Test;

import javax.annotation.Resource;

public class ExerciseInfoServiceTest extends ExerciseApplicationTests {

    @Resource
    ExerciseInfoService exerciseInfoService;

    @Resource
    private ExerciseInfoMapper exerciseInfoMapper;

    @Test
    public void selectExerciseInfoByStudentId(){
        ExerciseInfo exerciseInfo = exerciseInfoService.selectExerciseInfoByStudentId((long) 18);
        System.out.println(exerciseInfo);
    }

    @Test
    public void selectOne(){
        long studentId = 18;
        ExerciseInfo exerciseInfo = new ExerciseInfo(studentId);
        exerciseInfo.setStudentId(studentId);
        ExerciseInfo resultInfo = exerciseInfoMapper.selectOne(exerciseInfo);
        System.out.println(resultInfo);
    }

    @Test
    public void updateInfo(){
        ExerciseInfoDTO exerciseInfoDTO = new ExerciseInfoDTO((long) 18);
        exerciseInfoDTO.setError(100);
        int i = exerciseInfoMapper.updateInfo(exerciseInfoDTO);
        System.out.println(i);
    }


}
