package com.one.exercise.mapper;

import com.one.exercise.ExerciseApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TeacherMapperTest extends ExerciseApplicationTests {

    @Autowired
    TeacherMapper teacherMapper;

    @Test
    public void selectCountEx(){
        int i = teacherMapper.selectCountTeacher();
        System.out.println(i);

    }

}
