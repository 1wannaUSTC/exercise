package com.one.exercise.util;

import com.one.exercise.ExerciseApplicationTests;
import com.one.exercise.pojo.Identity;
import com.one.exercise.utils.IdentityUtils;
import org.junit.Test;

public class IdentityUtilsTest extends ExerciseApplicationTests {

    @Test
    public void build(){
        try {
            String build = IdentityUtils.build(new Identity((long) 15, 1));
            System.out.println(build);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void check(){
        String code = "fe4df7c5152d4acc8d20c25edccfe709bc166ce9594b45aabd5686353066e351a87f9d91c5f84b11aac51a1e6c94394c";
        try {
            Identity check = IdentityUtils.check(code);
            System.out.println(check);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
