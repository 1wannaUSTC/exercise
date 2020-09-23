package com.one.exercise;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExerciseApplicationTests {

    @Test
    public void contextLoads() {

        Integer i1 = new Integer(1);
        Integer i2 = new Integer(1);
        System.out.println(Objects.equals(i1, i2));

    }

    public void test(){
        InputStreamReader isr=new InputStreamReader(System.in);
        BufferedReader br=new BufferedReader(isr);
        System.out.println("请输入字符串：");
        String str="";
        try {
            str = br.readLine();
            br.close();
        } catch (IOException e) {

            e.printStackTrace();
        }
        Pattern p = Pattern.compile("[a-z]*");//小写字母
        Matcher m = p.matcher(str);
        StringBuffer lowercase = new StringBuffer();
        while(m.find()){
            lowercase.append(m.group(0));
        }

        p = Pattern.compile("[A-Z]*");//大写字母
        m = p.matcher(str);
        StringBuffer uppercase = new StringBuffer();
        while(m.find()){
            uppercase.append(m.group(0));
        }

        p = Pattern.compile("[^a-zA-Z]*");//非英文字母
        m = p.matcher(str);
        StringBuffer others = new StringBuffer();
        while(m.find()){
            others.append(m.group(0));
        }
        System.out.println("小写字母："+lowercase.length()+"个");
        System.out.println("大写字母："+uppercase.length()+"个");
        System.out.println("非英文字母："+others.length()+"个");



    }

}
