package com.one.exercise.service;

import com.one.exercise.ExerciseApplicationTests;
import org.junit.Test;

import javax.annotation.Resource;

public class TeacherServiceTest extends ExerciseApplicationTests {

    @Resource
    TeacherService teacherService;

    @Test
    public void updateTeacherFollow(){
        int i1 = teacherService.updateTeacherFollow(20, -10);
        System.out.println(i1);
    }

}
