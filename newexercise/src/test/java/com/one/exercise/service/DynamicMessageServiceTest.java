package com.one.exercise.service;

import com.alibaba.fastjson.JSONObject;
import com.one.exercise.ExerciseApplicationTests;
import com.one.exercise.mapper.DynamicMessageMapper;
import com.one.exercise.pojo.DynamicMessage;
import com.one.exercise.pojo.dynamicmessage.DynamicMessageDTO;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

public class DynamicMessageServiceTest extends ExerciseApplicationTests {

    @Resource
    DynamicMessageService dynamicMessageService;

    @Resource
    DynamicMessageMapper dynamicMessageMapper;

    @Test
    public void queryNewest(){
        List<DynamicMessage> dynamicMessage = dynamicMessageMapper.selectNewest(0, 2);
        System.out.println("###############################");
        System.err.println(JSONObject.toJSONString(dynamicMessage));
    }

    @Test
    public void queryNewestService(){
        List<DynamicMessageDTO> dynamicMessageDTOS = dynamicMessageService.queryNewestHot(18, 1, 0, 2);
        System.out.println("###############################");
        System.err.println(JSONObject.toJSONString(dynamicMessageDTOS));
    }

}
