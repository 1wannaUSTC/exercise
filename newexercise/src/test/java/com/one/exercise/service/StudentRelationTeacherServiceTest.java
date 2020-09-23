package com.one.exercise.service;

import com.one.exercise.ExerciseApplicationTests;
import org.junit.Test;

import javax.annotation.Resource;

public class StudentRelationTeacherServiceTest extends ExerciseApplicationTests {

    @Resource
    StudentRelationTeacherService studentRelationTeacherService;

    @Test
    public void studentRelationTeacher(){
        long teacherId = 20;
        long studentId = 2;
        int i = studentRelationTeacherService.studentRelationTeacher(teacherId, studentId);
        System.out.println(i);
    }

}
