package com.one.exercise.mapper.custom;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public class TeacherCustom {

    public String selectTeacherByIdList(@Param("teacherIdList") List<Integer> teacherIdList){
        StringBuilder sb = new StringBuilder("SELECT * FROM `exercise`.`teacher` ");
        sb.append(" WHERE teacher_id IN " +
                teacherIdList.toString().replace("[", "(").replace("]",") ")
        );
        return sb.toString();
    }

}
