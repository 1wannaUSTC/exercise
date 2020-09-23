package com.one.exercise.service;

import com.one.exercise.ExerciseApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class StudentSubjectServiceTest extends ExerciseApplicationTests {

    @Autowired
    StudentSubjectService studentSubjectService;

    @Test
    public void querySubject(){
        System.out.println(studentSubjectService.querySubject((long) 1));
    }

}
