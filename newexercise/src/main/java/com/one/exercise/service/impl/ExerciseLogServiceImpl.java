package com.one.exercise.service.impl;

import com.one.exercise.mapper.ExerciseLogMapper;
import com.one.exercise.pojo.CountWeek;
import com.one.exercise.pojo.ExerciseLog;
import com.one.exercise.service.ExerciseLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ExerciseLogServiceImpl implements ExerciseLogService {

    @Resource
    private ExerciseLogMapper exerciseLogMapper;

    @Override
    public int insertLog(ExerciseLog exerciseLog) {
        try {
            int i = exerciseLogMapper.insertSelective(exerciseLog);
            return i;
        }catch (Exception e){
            log.error(e.getMessage());
            return 0;
        }
    }

    @Override
    public int insertLogList(List<ExerciseLog> exerciseLogList) {
        try {
            int i = exerciseLogMapper.insertList(exerciseLogList);
            return i;
        }catch (Exception e){
            log.error(e.getMessage());
            return 0;
        }
    }

    @Override
    public CountWeek countWeekByStudent(long studentId, List<String> dateWeek) {

        return exerciseLogMapper.countWeekByStudent(studentId, dateWeek);
    }
}
