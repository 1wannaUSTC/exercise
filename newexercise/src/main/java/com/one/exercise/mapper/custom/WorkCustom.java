package com.one.exercise.mapper.custom;

import com.one.exercise.pojo.Work;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class WorkCustom {

    /** SELECT * FROM `work` WHERE major="软件工程" AND subject_name="Java语言基础" AND state=0 AND difficulty="简单" */
    public String getWorkList(
            @Param("work") Work work,
            @Param("startIndex") Integer startIndex,
            @Param("pageSize") Integer pageSize
    ){

        return new SQL(){
            {
                SELECT("*");
                FROM("work");

                StringBuilder sb = new StringBuilder("");


                if(work.getMajor()!=null && work.getMajor().length()>0){
                    sb.append(" major=#{work.major} AND ");
                }

                if(work.getSubjectName()!=null && work.getSubjectName().length()>0){
                    sb.append(" subject_name=#{work.subjectName} AND ");
                }

                if(work.getState()==1 || work.getState()== 0){
                    sb.append(" state = #{work.state} AND ");
                }

                if(work.getDifficulty() != null && work.getDifficulty().length()>0){
                    sb.append("difficulty=#{work.difficulty}");
                }

                sb.append(" teacher_id = #{work.teacherId} ");

                WHERE(sb.toString());

                LIMIT("#{startIndex},#{pageSize}");
            }
        }.toString();

    }

}
