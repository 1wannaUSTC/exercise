package com.one.exercise.controller;

import com.alibaba.fastjson.JSONObject;
import com.one.exercise.exception.UpdateMoreException;
import com.one.exercise.pojo.*;
import com.one.exercise.pojo.exerciseInfo.ExerciseInfoDTO;
import com.one.exercise.pojo.work.WorkStudentQuery;
import com.one.exercise.service.*;
import com.one.exercise.utils.IdentityUtils;
import com.one.exercise.utils.PageUtil;
import com.one.exercise.utils.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("work")
public class WorkController {

    @Resource
    private WorkService workService;

    @Resource
    private QuestionBankService questionBankService;

    @Resource
    private ExerciseInfoService exerciseInfoService;

    @Resource
    private ExerciseLogService exerciseLogService;

    @Resource
    private StudentRelationTeacherService studentRelationTeacherService;

    @Resource
    private TeacherService teacherService;

    @Resource
    private RestTemplate restTemplate;

    private static Logger logger = LoggerFactory.getLogger(WorkController.class);

    /**
     * 老师所属作业数量
     */
    @GetMapping("count")
    public ResultResponse getCount(int teacherId, int state) {
        int workStateCount = workService.getWorkStateCount(teacherId, state);
        return ResultResponse.RETURN(1, "ok", workStateCount);
    }

    /**
     * 教师端：我的作业列表
     */
    @GetMapping("get")
    public ResultResponse getWork(
            Work work,
            @RequestParam(required = false, defaultValue = "1") Integer startIndex,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            HttpServletRequest request
    ) {

        if (work.getTeacherId() == null && work.getTeacherId() != 0) {
            return ResultResponse.RETURN(2, "fail", "teacherId is null");
        }

        if (!(work.getState() == 1 || work.getState() == 0)) {
            return ResultResponse.RETURN(3, "fail", "state为0或1");
        }

        if (startIndex <= 0) {
            startIndex = 1;
        }

        if (pageSize <= 0) {
            pageSize = 10;
        }

        List<Work> workList = workService.getWorkList(work, (startIndex - 1) * pageSize, pageSize);
        return ResultResponse.RETURN(1, "success", workList);

    }

    /**
     * 学生端：作业列表
     */
    @GetMapping("query")
    public ResultResponse queryWork(
            WorkStudentQuery workStudentQuery,
            @RequestParam(required = false, defaultValue = "1") Integer startPage,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            HttpServletRequest request
    ){

        Identity identity = (Identity)request.getAttribute("identity");
        long studentId = identity.getId();
        boolean green = IdentityUtils.greenAll(identity);
        if (!green){
            return ResultResponse.RETURN(2, "fail: 权限不足", false);
        }

        /**
         * subjectId、difficulty、teacherId、result
         */
        Work work = new Work();
        if (workStudentQuery != null) BeanUtils.copyProperties(workStudentQuery, work);

        int total = workService.countWork(work);
        PageUtil pageUtil = new PageUtil(total, pageSize);

        List<Work> works = workService.queryWorkList(work, pageUtil.getStartIndex(startPage), pageSize);

//        // 获取学生关注的所有老师的id
//        List<Long> teacherIdList = studentRelationTeacherService.selectTeacherIdByStudentId(studentId);
//
//        // 获取学生收藏的所有作业id
//        List<Long> workIdList = workService.getWorkIdByRelationStudentId(studentId);

//        List<WorkFollowCollect> WorkFollowCollects = new ArrayList<>();
//        for (Work w : works) {
//            WorkFollowCollect t = new WorkFollowCollect();
//            BeanUtils.copyProperties(w, t);
//
//            long teacherId = (long)(w.getTeacherId());
//            long workId = (long)(w.getWorkId());
//            t.setTeacherIsFollow( teacherIdList.indexOf(teacherId) >= 0 );
//            t.setWorkIsCollect(workIdList.indexOf(workId) >= 0);
//            WorkFollowCollects.add(t);
//        }

        JSONObject result = new JSONObject();
        result.put("pageInfo", pageUtil);
        result.put("works", works);
//        result.put("teacherIsFollow", true);
//        result.put("workIsCollect", workIsCollect);
        return ResultResponse.RETURN(1, "success", result);
    }

    /**
     * 模糊查找作业，作业名称
     */
    @PostMapping("queryVague")
    public ResultResponse queryVagueWork(String key,
                                         @RequestParam(required = false, defaultValue = "1") Integer startPage,
                                         @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                         HttpServletRequest request){
        Identity identity = (Identity)request.getAttribute("identity");
        boolean green = IdentityUtils.greenAll(identity);
        if (!green){
            return ResultResponse.RETURN(2, "fail: 权限不足", false);
        }

        key = key.trim();

        int total = workService.countVagueWork(key);
        PageUtil pageInfo = new PageUtil(total, pageSize);
        List<Work> works = workService.selectVagueWork(key, pageInfo.getStartIndex(startPage), pageSize);

        JSONObject result = new JSONObject();
        result.put("pageInfo", pageInfo);
        result.put("works", works);
        return ResultResponse.RETURN(1, "success", result);
    }

    /**
     * 学生：收藏状态
     */
    @PostMapping("isCollect")
    public ResultResponse isCollectWork(Long workId, HttpServletRequest request){
        Identity identity = (Identity)request.getAttribute("identity");
        Long studentId = identity.getId();
        boolean green = IdentityUtils.greenStudent(identity);
        if (!green){
            return ResultResponse.RETURN(2, "fail: 权限不足", false);
        }
        if (!(workId > 0 && studentId > 0)){
            return ResultResponse.RETURN(3, "fail: 参数错误", false);
        }
        boolean b = workService.isCollect(workId, studentId);
        return ResultResponse.RETURN(1,"success", b);

    }

    /**
     * 学生：收藏作业
     * cancelOrCollect true-收藏， false-取消收藏
     */
    @PostMapping("collect")
    public ResultResponse collectWork(Long workId, boolean cancelOrCollect, HttpServletRequest request){
        Identity identity = (Identity)request.getAttribute("identity");
        Long studentId = identity.getId();
        boolean green = IdentityUtils.greenStudent(identity);
        if (!green){
            return ResultResponse.RETURN(2, "fail: 权限不足", false);
        }
        if (!(workId > 0 && studentId > 0)){
            return ResultResponse.RETURN(3, "fail: 参数错误", false);
        }

        boolean b = false;
        String message = "出错";
        if (cancelOrCollect){
            b = workService.collectWork(workId, studentId);
            message = b ? "success : 收藏成功" : "success : 已经收藏";
        }else {
            b = workService.cancelCollectWork(workId, studentId);
            message = b ? "success : 取消收藏" : "success : 未收藏过";
        }
        return ResultResponse.RETURN(1, message, b);
    }

    /**
     * 学生：获取收藏的作业列表
     */
    @PostMapping("collectCount")
    public ResultResponse collectWork(@RequestParam(required = false, defaultValue = "1") Integer startPage,
                                      @RequestParam(required = false, defaultValue = "10") Integer pageSize, HttpServletRequest request){
        Identity identity = (Identity)request.getAttribute("identity");
        long studentId = identity.getId();
        boolean green = IdentityUtils.greenStudent(identity);
        if (!green){
            return ResultResponse.RETURN(2, "fail: 权限不足", false);
        }

        int total = workService.countCollectWork(studentId);
        PageUtil pageInfo = new PageUtil(total,pageSize);
        List<Work> works = workService.selectCollectWork(studentId, pageInfo.getStartIndex(startPage), pageSize);

        // 获取每个作业的老师信息
        List<Integer> teacherIdList = new ArrayList<>();
        for (Work work : works) {
            Integer teacherId = work.getTeacherId();
            teacherIdList.add(teacherId);
        }

        List<Teacher> teacherList = teacherService.selectTeacherByIdList(teacherIdList);

        List<Map<String, Object>> list = new ArrayList<>();
        for (Work work : works) {
            int teacherId1 = work.getTeacherId();
            for (Teacher teacher : teacherList) {
                int teacherId2 = teacher.getTeacherId();
                if (teacherId1 == teacherId2){
                    Map<String, Object> map = new HashMap<>();
                    map.put("work", work);

                    TeacherVO teacherVo = new TeacherVO();
                    BeanUtils.copyProperties(teacher, teacherVo);
                    map.put("teacher", teacherVo);

                    list.add(map);
                }
            }
        }

        JSONObject result = new JSONObject();
        result.put("pageInfo", pageInfo);
        result.put("works", list);
        return ResultResponse.RETURN(1, "success", result);

    }

    /**
     * 保存作业
     */
    @PostMapping("save")
    @Transactional
    public ResultResponse saveWork(Work work) {
        logger.error(work.toString());
        work.setWorkId(null);
        /*
        1. 作业是否存在-判断是否存在同名作业
            存在
                返回作业已经存在
        2. 作业添加是否成功
            （1）成功
                返回
            （2）失败
                返回失败信息
         */
        try {
            // 根据作业名称和老师id查找作业是否存在
            Work workByName = workService.getWorkByName(work.getTeacherId(), work.getWorkName());
            if (workByName != null) {
                return ResultResponse.RETURN(2, "已经存在", 0);
            }

            // 作业添加是否成功
            int i = workService.saveWork(work);
            System.err.println(i + "-----" + work.getWorkId());

            // 作业添加成功，根据作业id查找作业
            Work workById = workService.getWorkById(work.getWorkId());
            return ResultResponse.RETURN(1, "添加成功", workById);

        } catch (Exception e) {
            logger.error("=====================WorkController.saveWork:");
            logger.error(e.toString());
            return ResultResponse.RETURN(3, "参数有误", 0);
        }
    }

    /**
     * 删除作业
     */
    @PostMapping("remove")
    public ResultResponse removeWork(Integer workId) {
        if (workId == null) {
            return ResultResponse.RETURN(2, "fail", "workId不能为空");
        }
        int i = workService.deleteWork(workId);
        return ResultResponse.RETURN(1, "success", i);
    }

    /**
     * 根据作业id修改作业基本信息
     */
    @PostMapping("modify")
    public ResultResponse modifyWork(Work work) {
        if (work.getWorkId() == null) {
            return ResultResponse.RETURN(2, "fail", "workId不能为空");
        }
        int i = workService.updateWork(work);
        return ResultResponse.RETURN(2, "success", i);
    }

    /**
     * 作业批阅
     **/
    @PostMapping("read")
    public ResultResponse workRead(@RequestBody WorkReadEntity workReadEntity, HttpServletRequest request) {

        Identity identity = (Identity)request.getAttribute("identity");
        long studentId = identity.getId();
        boolean green = IdentityUtils.greenStudent(identity);
        if (!green){
            return ResultResponse.RETURN(2, "fail: 权限不足", false);
        }

        /**
         * 1. 参数 作业id、问题id、答案
         * 2. 根据作业id获取作业
         * 3. 根据问题id获取问题
         * 4. 对比答案
         * 5. 计算总分、计算得分、计算正确率
         * 返回分数以及正确率
         */
        Long workId = workReadEntity.getWorkId();

        Map<Long, String> answer = workReadEntity.getAnswer();
        Set<Long> keys = answer.keySet();

        // 根据作业id获取作业
        Work work = workService.getWorkById(Math.toIntExact(workId));

        // 根据问题id列表获取问题
        List<QuestionBank> questionBanks = questionBankService.selectQuestionByIdList(keys);

        /*
        记录info的更新数据
         */
        int difficulty = 0;
        int difficultyCorrect = 0;
        int general = 0;
        int generalCorrect = 0;
        int easy = 0;
        int easyCorrect = 0;
        int correct = 0;
        int error = 0;
        /*
        日志List
         */
        List<ExerciseLog> exerciseLogList = new ArrayList<>();
        /*
        work—log
        得分比率
         */

        // 比较问题id，问题id相同时比较答案
        // map 记录正确答案
        Map<Integer, Map<String, Object>> map = new HashMap<>();
        // 记录得分
        float score = 0;
        // 记录总分
        float totalPoints = 0;
        for (QuestionBank item : questionBanks) {
            Integer questionId = item.getQuestionId();
            String s = answer.get((long) questionId);

            Map<String, Object> info = new HashMap<>();
            if (Tools.isNullAllStr(item.getAnswer(), s)) {
                info.put("correct", false);// 正确性
                info.put("answer", null);
                info.put("score", item.getScore());
                info.put("get", 0);
                error++;
            } else {
                /**
                 * type = 简答
                 */
                if (item.getAnswer().equalsIgnoreCase(s) ) {
                    info.put("correct", true);// 正确性
                    info.put("answer", item.getAnswer());
                    info.put("score", item.getScore());
                    info.put("get", item.getScore());
                    score += item.getScore();
                    correct++;
                } else if (item.getType().equals("简答")){
                    double v = this.similarityString(item.getAnswer(), s);

                    if (v > 0.6){
                        info.put("correct", true);// 正确性
                        correct++;
                    }else {
                        info.put("correct", false);// 正确性
                        error++;
                    }
                    info.put("answer", item.getAnswer());
                    info.put("score", item.getScore());
                    info.put("get", item.getScore() * v);
                    score += item.getScore() * v;
                }else {
                    info.put("correct", false);// 正确性
                    info.put("answer", item.getAnswer());
                    info.put("score", item.getScore());
                    info.put("get", 0);
                    error++;
                }
            }

            // log
            ExerciseLog exerciseLog = new ExerciseLog();
            exerciseLog.setStudentId(studentId);
            exerciseLog.setQuestionId(Long.valueOf(item.getQuestionId()));
            int ic = ((boolean)(info.get("correct"))) ? 1 : 0;
            exerciseLog.setIsCorrect(ic);
            exerciseLog.setFinishTime(null);
            exerciseLogList.add(exerciseLog);

            // info
            if (ic == 1){
                switch (item.getDifficulty()){
                    case "困难":
                        difficulty++;
                        difficultyCorrect++;
                        break;
                    case "一般":
                        general++;
                        generalCorrect++;
                        break;
                    case "简单":
                        easy++;
                        easyCorrect++;
                        break;
                    default:
                        return ResultResponse.RETURN(5, "无法判断问题类型", null);
                }
            }else {
                switch (item.getDifficulty()){
                    case "困难":
                        difficulty++;
                        break;
                    case "一般":
                        general++;
                        break;
                    case "简单":
                        easy++;
                        break;
                    default:
                        return ResultResponse.RETURN(5, "无法判断问题类型", null);
                }
            }

            map.put(questionId, info);
            totalPoints += item.getScore();
        }
        System.err.println(score + "/" + totalPoints);
        // 正确率
        float accuracy = score / totalPoints;

        // 更新info 更新log
        // 在info中为当前学生更新信息
        int logI = exerciseLogService.insertLogList(exerciseLogList);

        ExerciseInfoDTO exerciseInfoDTO = new ExerciseInfoDTO(studentId);
        exerciseInfoDTO.setDifficulty(difficulty);
        exerciseInfoDTO.setDifficultyCorrect(difficultyCorrect);
        exerciseInfoDTO.setGeneral(general);
        exerciseInfoDTO.setGeneralCorrect(generalCorrect);
        exerciseInfoDTO.setEasy(easy);
        exerciseInfoDTO.setEasyCorrect(easyCorrect);
        exerciseInfoDTO.setError(error);
        exerciseInfoDTO.setCorrect(correct);

        if (logI > 0){
            exerciseInfoService.updateInfo(exerciseInfoDTO);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("work", work);
        jsonObject.put("resultAnswer", map);
        jsonObject.put("score", score); // 答题得分
        jsonObject.put("totalPoints", totalPoints); // 所有问题分数合计
        jsonObject.put("accuracy", accuracy); // 分数百分比
        jsonObject.put("workResult", work.getResult() * accuracy); // 换算得分：百分比 * 作业分数

        return ResultResponse.RETURN(1, "success", jsonObject);
    }

    public double similarityString(String answer, String reply){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("answer", answer);
        jsonObject.put("reply", reply);
        JSONObject jsonObjectResponseEntity = restTemplate.postForObject("http://101.200.175.35:8181/api/ai", jsonObject, JSONObject.class);
        Integer score = (Integer)jsonObjectResponseEntity.get("score");
        return score;
    }

    /**
     * 批量修改作业内容
     */
    @PostMapping("modifyMoreContent/{workId}")
    public ResultResponse modifyMoreContent(@RequestBody List<QuestionBank> questionBanks, @PathVariable(name = "workId") Integer workId, HttpServletRequest request){
        Identity identity = (Identity)request.getAttribute("identity");
        boolean green = IdentityUtils.greenAdminAndTeacher(identity);
        if (!green){
            return ResultResponse.RETURN(2, "fail: 权限不足", false);
        }
        Long id = identity.getId();

        /**
         * 1. 判断作业是否属于该老师
         * 2. 判断作业是否属于该老师
         * 3. 批量判断问题是否归属于该老师（批量获取该老师相关的题目）
         * 4. 对问题惊醒批量更新（提示：此处的更新会影响到题库中的单独试题）
         * 5. 逐一进行修改（批量修改）
         */

        Work workById = workService.getWorkById(workId);
        if (workById==null || (long)workById.getTeacherId() != id){
            return ResultResponse.RETURN(3, "fail: 该老师不拥有此作业", false);
        }

        Set<Long> ids = new TreeSet<>();
        questionBanks.forEach(item->{
            ids.add((long)item.getQuestionId());
        });

        List<QuestionBank> dbq = questionBankService.selectQuestionByIdByTeacherIdList(ids, Math.toIntExact(id));

        for (QuestionBank questionBank : dbq) {
            for (QuestionBank bank : questionBanks) {
                if (Objects.equals(questionBank.getQuestionId(), bank.getQuestionId())){
                    questionBank.setQuestion(bank.getQuestion());
                    questionBank.setOption(bank.getOption());
                    questionBank.setAnswer(bank.getAnswer());
                }
            }
        }

        try {
            boolean b = questionBankService.updateMoreQuestion(dbq);
            if (b) return ResultResponse.RETURN(1, "success", true);
            else return ResultResponse.RETURN(4, "fail：无需重复修改", false);
        } catch (UpdateMoreException e) {
            e.printStackTrace();
            return ResultResponse.RETURN(500, e.getMessage(), false);
        }
    }
}
