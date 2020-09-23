package com.one.exercise.controller;

import com.one.exercise.pojo.ExerciseInfo;
import com.one.exercise.pojo.Identity;
import com.one.exercise.pojo.ResultResponse;
import com.one.exercise.service.ExerciseInfoService;
import com.one.exercise.utils.IdentityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("exerciseInfo")
public class ExerciseInfoServiceController {

    @Resource
    private ExerciseInfoService exerciseInfoService;

    @RequestMapping("query")
    public ResultResponse queryExerciseInfoByStudentId(HttpServletRequest request){
        Identity identity = (Identity) request.getAttribute("identity");
        long studentId = identity.getId();
        boolean green = IdentityUtils.greenStudent(identity);
        if (!green) {
            return ResultResponse.RETURN(2, "fail: 权限不足", false);
        }

        // 获取信息
        ExerciseInfo exerciseInfo = exerciseInfoService.selectExerciseInfoByStudentId(studentId);

        return ResultResponse.RETURN(1, "success", exerciseInfo);
    }

}
