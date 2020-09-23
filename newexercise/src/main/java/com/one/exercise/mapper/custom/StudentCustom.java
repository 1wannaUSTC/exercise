package com.one.exercise.mapper.custom;

import org.apache.ibatis.annotations.Param;

import java.util.Set;

public class StudentCustom {
    public String selectStudentByIds(@Param("idSet") Set<Long> idSet){
        // SELECT * FROM `exercise`.`student` WHERE student_id in (1, 2, 3, 4)
        String idString = idSet.toString().replace("[", "(").replace("]", ")");
        return "SELECT * FROM `exercise`.`student` WHERE student_id in " + idString;
    }
}
