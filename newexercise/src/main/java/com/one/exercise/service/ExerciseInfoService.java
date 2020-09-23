package com.one.exercise.service;

import com.one.exercise.pojo.ExerciseInfo;
import com.one.exercise.pojo.exerciseInfo.ExerciseInfoDTO;

public interface ExerciseInfoService {

    ExerciseInfo selectExerciseInfoByStudentId(long studentId);

    /**
     * 更新INFO信息
     */
    int updateInfo(ExerciseInfoDTO exerciseInfoDTO);


}
