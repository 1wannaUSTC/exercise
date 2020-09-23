package com.one.exercise.mapper;

import com.one.exercise.mapper.custom.ExerciseLogCustom;
import com.one.exercise.pojo.CountWeek;
import com.one.exercise.pojo.ExerciseLog;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface ExerciseLogMapper extends Mapper<ExerciseLog>, MySqlMapper<ExerciseLog> {

    @SelectProvider(type = ExerciseLogCustom.class, method = "countWeekByStudent")
    CountWeek countWeekByStudent(
            @Param("studentId") long studentId,
            @Param("dateWeek") List<String> dateWeek);
}