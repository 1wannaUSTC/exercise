package com.one.exercise.service.impl;

import com.one.exercise.mapper.ExerciseInfoMapper;
import com.one.exercise.pojo.ExerciseInfo;
import com.one.exercise.pojo.exerciseInfo.ExerciseInfoDTO;
import com.one.exercise.service.ExerciseInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ExerciseInfoServiceImpl implements ExerciseInfoService {

    @Resource
    private ExerciseInfoMapper exerciseInfoMapper;

    @Override
    public ExerciseInfo selectExerciseInfoByStudentId(long studentId){
        ExerciseInfo exerciseInfo = new ExerciseInfo();
        exerciseInfo.setStudentId(studentId);
        ExerciseInfo resultInfo = exerciseInfoMapper.selectOne(exerciseInfo);

        if (resultInfo == null){
            ExerciseInfo exerciseInfo1 = new ExerciseInfo(studentId);
            exerciseInfoMapper.insertSelective(exerciseInfo1);
            return exerciseInfo1;
        }
        return resultInfo;
    }

    @Override
    public int updateInfo(ExerciseInfoDTO exerciseInfoDTO) {
        int i = exerciseInfoMapper.updateInfo(exerciseInfoDTO);
        return i;
    }
}
