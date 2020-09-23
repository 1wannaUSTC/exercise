package com.one.exercise.controller;

import com.alibaba.fastjson.JSONObject;
import com.one.exercise.pojo.Identity;
import com.one.exercise.pojo.ResultResponse;
import com.one.exercise.utils.IdentityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("test")
public class HelloController {

    @PostMapping("hello")
    public ResultResponse hello(){
//        Identity identity = (Identity)request.getAttribute("identity");
//        long id = identity.getId();
//       boolean green = IdentityUtils.greenAdminAndTeacher(identity);
//        if (!green){
//            return ResultResponse.RETURN(2, "fail: 权限不足", false);
//        }

        return ResultResponse.RETURN(1, "success", "ok");
    }

    @PostMapping("array")
    public ResultResponse chooseSubject(@RequestBody List<Integer> subjects){
        System.out.println("7777777777777777777777777777");
        return ResultResponse.RETURN(1, "success", subjects);
    }

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("test")
    public JSONObject test(){

            JSONObject jsonObject = new JSONObject();

            String[] s1 = {"吃了饭","吃了饭","吃了饭","吃了饭","吃了饭","吃了饭","吃了饭"};
            List<String> strings1 = Arrays.asList(s1);

            String[] s2 = {"吃了饭","吃了饭","吃了饭","吃了饭","吃了饭","吃了饭","吃了饭"};
            List<String> strings2 = Arrays.asList(s2);

            jsonObject.put("answer", strings1);
            jsonObject.put("reply", strings2);

            JSONObject jsonObjectResponseEntity = restTemplate.postForObject("http://101.200.175.35:8181/api/ai", jsonObject, JSONObject.class);

            List<Integer> score = (List<Integer>)jsonObjectResponseEntity.get("score");
            System.out.println(score.get(0));

            return jsonObjectResponseEntity;
    }

}
