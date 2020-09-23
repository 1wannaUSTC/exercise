package com.one.exercise.controller;

import com.one.exercise.pojo.CountWeek;
import com.one.exercise.pojo.Identity;
import com.one.exercise.pojo.ResultResponse;
import com.one.exercise.service.ExerciseLogService;
import com.one.exercise.utils.IdentityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("exerciseLog")
public class ExerciseLogController {

    @Resource
    private ExerciseLogService exerciseLogService;

    /**
     * 学生端：统计七日做题信息
     */
    @PostMapping("countWeek")
    public ResultResponse countWeek(HttpServletRequest request){
        Identity identity = (Identity)request.getAttribute("identity");
        long studentId = identity.getId();
        boolean green = IdentityUtils.greenStudent(identity);
        if (!green){
            return ResultResponse.RETURN(2, "fail: 权限不足", null);
        }

        LocalDate now = LocalDate.now();
        List<String> dateWeek = new ArrayList<>();

        for (int i = 6; i >= 0; i--){
            dateWeek.add(now.minusDays(i).toString());
        }

        CountWeek  countWeek = exerciseLogService.countWeekByStudent(studentId, dateWeek);

        List<Integer> iList = new ArrayList<>();
        iList.add(countWeek.getOne0());
        iList.add(countWeek.getOne1());
        iList.add(countWeek.getOne2());
        iList.add(countWeek.getOne3());
        iList.add(countWeek.getOne4());
        iList.add(countWeek.getOne5());
        iList.add(countWeek.getOne6());


        return ResultResponse.RETURN(1, "success", iList);
    }

}
