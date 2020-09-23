package com.one.exercise.mapper.custom;

import com.one.exercise.ExerciseApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class SubjectCustomTest extends ExerciseApplicationTests {

    SubjectCustom subjectCustom = new SubjectCustom();

    @Test
    public void fun(){
        ArrayList l = new ArrayList();
        l.add(1);
        l.add(2);
        String s = subjectCustom.insertSubjectListIgnore((long) 1, l);
        System.out.println(s);
    }

}
