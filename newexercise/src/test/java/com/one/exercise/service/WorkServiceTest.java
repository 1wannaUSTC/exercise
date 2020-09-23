package com.one.exercise.service;

import com.one.exercise.ExerciseApplicationTests;
import com.one.exercise.pojo.Work;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

public class WorkServiceTest extends ExerciseApplicationTests {

    @Resource
    WorkService workService;

    @Test
    public void queryWorkList(){
        Work work = new Work();
        work.setDifficulty("简单");

        int i = workService.countWork(work);
        System.out.println(i);

        List<Work> works = workService.queryWorkList(work, 0, 30);
        System.out.println(works);
    }

}
