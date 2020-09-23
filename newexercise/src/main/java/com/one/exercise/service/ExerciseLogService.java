package com.one.exercise.service;

import com.one.exercise.pojo.CountWeek;
import com.one.exercise.pojo.ExerciseLog;

import java.util.List;

public interface ExerciseLogService {

    /** 追加一条新的习题日志 */
    int insertLog(ExerciseLog exerciseLog);

    /** 追加多条新的习题日志 */
    int insertLogList(List<ExerciseLog> exerciseLogList);

    /** 学生端：统计七日做题信息 */
    CountWeek countWeekByStudent(long studentId, List<String> dateWeek);

}
