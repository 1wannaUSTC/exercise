package com.one.exercise.controller;

import com.one.exercise.pojo.Identity;
import com.one.exercise.pojo.QuestionBank;
import com.one.exercise.pojo.ResultResponse;
import com.one.exercise.pojo.WorkQ;
import com.one.exercise.pojo.question.QuestionDTO;
import com.one.exercise.pojo.work.WorkQDTO;
import com.one.exercise.service.StudentRelationTeacherService;
import com.one.exercise.service.WorkQuestionRelationService;
import com.one.exercise.service.WorkService;
import com.one.exercise.utils.IdentityUtils;
import com.one.exercise.utils.Tools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("work_question")
@Slf4j
public class WorkQuestionRelationController {

    @Resource
    private WorkQuestionRelationService workQuestionRelationService;

    @Resource
    private StudentRelationTeacherService studentRelationTeacherService;

    @Resource
    private WorkService workService;

    /** 添加 */
    @PostMapping("save")
    public ResultResponse save(int workId, int questionId){
        log.info(workId + " === " + questionId);
        int i = workQuestionRelationService.insertQuestionToWork(workId, questionId);
        return ResultResponse.RETURN(1, "success", i);
    }

    /** 添加多个问题  */
    @PostMapping("saves/{workId}")
    public ResultResponse save(@PathVariable("workId") Integer workId, @RequestBody List<Integer>questionIds ){
        int i = workQuestionRelationService.insertQuestionToWork(workId, questionIds);
        Map<String, Object> map = new HashMap<>();
        map.put("save", i);
        return ResultResponse.RETURN(1,"success", map);
    }

    /** 删除 */
    @PostMapping("remove_question")
    public ResultResponse remove(int workId, int questionId){
        int i = workQuestionRelationService.deleteQuestionToWork(workId, questionId);

        if (i >= 1){
            return ResultResponse.RETURN(1, "success", i);
        }else {
            return ResultResponse.RETURN(2, "fail", i);
        }
    }

    /** 删除作业 - 前端可能用不到 - 在删除作业时使用 */
    @PostMapping("save_work")
    public ResultResponse remove(int workId){
        int i = workQuestionRelationService.deleteQuestionToWork(workId);
        if (i >= 1){
            return ResultResponse.RETURN(1, "success", i);
        }else {
            return ResultResponse.RETURN(2, "fail", i);
        }
    }

    /** 查看作业内容 */
    @PostMapping("queryWorkContent")
    public ResultResponse queryWorkContent(int workId, HttpServletRequest request){

        Identity identity = (Identity)request.getAttribute("identity");
        boolean green = IdentityUtils.greenAll(identity);
        if (!green){
            return ResultResponse.RETURN(2, "fail: 权限不足", null);
        }

        long id = identity.getId();

        WorkQ workQ = workQuestionRelationService.selectWorkContent((int)id, workId);

//        WorkQDTO workQDTO = new WorkQDTO();
//        BeanUtils.copyProperties(workQ, workQDTO);
//        List<QuestionDTO> list2 = Tools.questionDAOListTransformDTO(workQ.getQuestionList());
//        workQDTO.setQuestions(list2);

        return ResultResponse.RETURN(1, "success", workQ);
    }

    /**
     *  学生端：查看作业内容
     */
    @PostMapping("queryWorkContent2")
    public ResultResponse queryWorkContent(int teacherId,int workId, HttpServletRequest request){

        Identity identity = (Identity)request.getAttribute("identity");
        boolean green = IdentityUtils.greenAll(identity);
        if (!green){
            return ResultResponse.RETURN(2, "fail: 权限不足", null);
        }
        Long studentId = identity.getId();

        WorkQ workQ = workQuestionRelationService.selectWorkContent(teacherId, workId);

        List<QuestionBank> questionList = workQ.getQuestionList();
        List<QuestionBank> singleSelection = new ArrayList<>();
        List<QuestionBank> multiSelect = new ArrayList<>();
        List<QuestionBank> shortAnswer = new ArrayList<>();

        for (QuestionBank questionBank : questionList) {
            String type = questionBank.getType();
            switch (type){
                case "单选":
                    singleSelection.add(questionBank);
                    break;
                case "多选":
                    multiSelect.add(questionBank);
                    break;
                case "简答":
                    shortAnswer.add(questionBank);
                    break;
                default:
                    log.error("查看作业内容中，没有对应的题目类型");
                    break;
            }
        }
        List<QuestionBank> newQuestionList = new ArrayList<>();
        newQuestionList.addAll(singleSelection);
        newQuestionList.addAll(multiSelect);
        newQuestionList.addAll(shortAnswer);

        workQ.setQuestionList(newQuestionList);


        // 获取学生关注的所有老师的id
        List<Long> teacherIdList = studentRelationTeacherService.selectTeacherIdByStudentId(studentId);

        // 获取学生收藏的所有作业id
        List<Long> workIdList = workService.getWorkIdByRelationStudentId(studentId);

        WorkQDTO workQDTO = new WorkQDTO();
        BeanUtils.copyProperties(workQ, workQDTO);
        List<QuestionDTO> list2 = Tools.questionDAOListTransformDTO(workQ.getQuestionList());
        workQDTO.setQuestions(list2);

        long teacherIdQ = (long)(workQ.getTeacherId());
        long workIdQ = (long)(workQ.getWorkId());
        workQDTO.setTeacherIsFollow( teacherIdList.indexOf(teacherIdQ) >= 0 );
        workQDTO.setWorkIsCollect(workIdList.indexOf(workIdQ) >= 0);
        System.err.println(workQ);

        return ResultResponse.RETURN(1, "success", workQDTO);
    }

}
