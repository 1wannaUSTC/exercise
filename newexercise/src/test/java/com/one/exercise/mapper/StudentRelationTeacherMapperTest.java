package com.one.exercise.mapper;

import com.one.exercise.ExerciseApplicationTests;
import com.one.exercise.pojo.Student;
import com.one.exercise.pojo.StudentVO;
import com.one.exercise.service.StudentService;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

public class StudentRelationTeacherMapperTest extends ExerciseApplicationTests {

    @Resource
    StudentRelationTeacherMapper studentRelationTeacherMapper;

    @Resource
    StudentService studentService;

    @Test
    public void selectStudentByTeacherId(){
        List<Student> students = studentRelationTeacherMapper.selectStudentByTeacherId((long) 20, 1, 2);
        students.forEach(item->{
            System.out.println(item);
        });
    }

    @Test
    public void getStudentById(){
        StudentVO s = studentService.getStudentById("oVAi55U8nKKtnJaKW8ApLI5hf1Pg");
        System.out.println(s);
    }

    @Test
    public void selectStudentIdByTeacherId(){
        List<Long> list = studentRelationTeacherMapper.selectStudentIdByTeacherId(20);

        System.out.println(list.toString().replace("[", "(").replace("]", ")"));
    }

}
