package com.one.exercise.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.one.exercise.pojo.ResultResponse;
import com.one.exercise.utils.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 这是用做教师认证的controller
 */
@RestController
@RequestMapping("teacher")
public class TeacherQualificationController {

    /** 教师认证：判断该用户是否为教师 */
    @GetMapping("qualification")
    public ResultResponse qualification(String name, String IDNumber, HttpServletResponse response){

        String testResultSuccess = "{\"教师资格证数据状态\":\"艾科瑞特，让企业业绩长青\",\"教师资格证数据实体信息\":{\"姓名\":\"张三\",\"身份证号\":\"500228199912125566\",\"出生年月日\":\"19910625\",\"笔试成绩实体信息\":[{\"笔试顺序\":\"笔试成绩1\",\"笔试科目\":\"201-综合素质(小学)\",\"笔试成绩\":\"85\",\"笔试状态\":\"合格\",\"笔试准考证号\":\"42051811204520\",\"笔试日期\":\"2018年上半年\",\"笔试有效期\":\"2020年6月30日\",\"笔试省份\":\"湖北省\"},{\"笔试顺序\":\"笔试成绩2\",\"笔试科目\":\"202-教育教学知识与能力\",\"笔试成绩\":\"78\",\"笔试状态\":\"合格\",\"笔试准考证号\":\"42051811204520\",\"笔试日期\":\"2018年上半年\",\"笔试有效期\":\"2020年6月30日\",\"笔试省份\":\"湖北省\"}],\"面试成绩实体信息\":[{\"面试顺序\":\"面试成绩1\",\"面试科目\":\"241-小学语文\",\"面试状态\":\"合格\",\"面试准考证号\":\"42011812276990\",\"面试日期\":\"2018年上半年\",\"面试省份\":\"湖北省\"}],\"合格证实体信息\":[{\"合格证顺序\":\"合格证1\",\"合格证\":\"小学（语文）\",\"合格证号\":\"2018422004699\",\"合格证有效期\":\"2021年6月30日\"}]}}";

        if("张三".equals(name) && "500228199912125566".equals(IDNumber)){
            JSONObject jsonObject = JSON.parseObject(testResultSuccess);
            JSONObject one = (JSONObject)jsonObject.get("教师资格证数据实体信息");
            List<Map<String,String>> list =  (List<Map<String,String>>)one.get("合格证实体信息");
            Map<String, String> map = list.get(0);
            Set<String> set = map.keySet();
            if (set.size() == 0 ){
                return ResultResponse.RETURN(1, "success", false);
            }
            return ResultResponse.RETURN(1, "success", true);
        }


        //API产品路径
        String host = "http://iteacher.market.alicloudapi.com";
        String path = "/icredit_ai_market/teacher_qualification_search/v1";
        String method = "GET";

        //阿里云APPCODE
        String appcode = "942f9103297e4f508570e3dd2272bf40";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();

        //参数配置
        //教师姓名，如：马进卫
        querys.put("NAME", name);
        //教师身份证号，如：411328199106254625
        querys.put("PERSON_ID", IDNumber);

        try {
            HttpResponse result = HttpUtils.doGet(host, path, method, headers, querys);

            String entity = EntityUtils.toString(result.getEntity());
            JSONObject jsonObject = JSON.parseObject(entity);

            JSONObject one = (JSONObject)jsonObject.get("教师资格证数据实体信息");
            List<Map<String,String>> list =  (List<Map<String,String>>)one.get("合格证实体信息");

            if (list.size()==0){
                return ResultResponse.RETURN(1, "success", false);
            }else {
                Map<String, String> map = list.get(0);
                Set<String> set = map.keySet();
                if (set.size() == 0 ){
                    return ResultResponse.RETURN(1, "success", false);
                }
            }
            return ResultResponse.RETURN(1, "success", true);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
            return ResultResponse.RETURN(2, "fail: 第三方接口过期", null);
        }

    }


}