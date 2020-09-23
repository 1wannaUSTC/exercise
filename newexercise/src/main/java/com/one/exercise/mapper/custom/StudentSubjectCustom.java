package com.one.exercise.mapper.custom;

import com.one.exercise.pojo.StudentSubject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public class StudentSubjectCustom {

    public String insertStudentSubjectList(@Param("subjectList") List<StudentSubject> subjectList){
        StringBuilder sb = new StringBuilder("INSERT ignore INTO `exercise`.`student_subject`(`subject_id`, `student_id`) VALUES ");
        subjectList.forEach(item->{
            sb.append("(" + item.getSubjectId() + "," + item.getStudentId() + "),");
        });
        return sb.toString().substring(0, sb.toString().length()-1);
    }

}
