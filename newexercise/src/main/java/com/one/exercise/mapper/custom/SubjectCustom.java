package com.one.exercise.mapper.custom;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

import static jdk.nashorn.internal.objects.NativeString.substring;

public class SubjectCustom {

    // INSERT ignore INTO teacher_subject (teacher_id, subject_id) VALUES(15,1),(15,2)
    public String insertSubjectListIgnore(
            @Param("teacherId") Long teacherId,
            @Param("list") List<Integer> list
    ){
        StringBuffer sb = new StringBuffer("INSERT ignore INTO teacher_subject (teacher_id, subject_id) VALUES ");
        list.forEach(item->{
            sb.append("(#{teacherId}," + item + "),");
        });
        String s = sb.toString();
        return s.substring(0, s.length()-1);
    }

}
