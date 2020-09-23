package com.one.exercise.mapper.custom;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public class ExerciseLogCustom {

    public String countWeekByStudent(@Param("studentId") long studentId,
                                     @Param("dateWeek") List<String> dateWeek){

        StringBuilder sb = new StringBuilder("SELECT * FROM  ");

        int i = 0;
        for (String s : dateWeek) {
            sb.append("(SELECT COUNT(*) one"+ i + " FROM `exercise`.`exercise_log` WHERE finish_time LIKE '%"+ s +"%' AND student_id = "+ studentId +") as c"+ i +",");
            i++;
        }

        String s = sb.toString();
        return s.substring(0, s.length()-1);

    }

}
