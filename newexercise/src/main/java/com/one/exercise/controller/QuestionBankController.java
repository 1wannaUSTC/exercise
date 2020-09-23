package com.one.exercise.controller;

import com.alibaba.fastjson.JSONObject;
import com.one.exercise.enums.QuestionDifficulty;
import com.one.exercise.enums.QuestionType;
import com.one.exercise.pojo.*;
import com.one.exercise.pojo.exerciseInfo.ExerciseInfoDTO;
import com.one.exercise.pojo.question.QuestionDTO;
import com.one.exercise.service.*;
import com.one.exercise.utils.IdentityUtils;
import com.one.exercise.utils.PageUtil;
import com.one.exercise.utils.Tools;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("question")
@Slf4j
public class QuestionBankController {

    @Resource
    private QuestionBankService questionBankService;

    @Resource
    private SubjectService subjectService;

    @Resource
    private WorkService workService;

    @Resource
    private WorkQuestionRelationService workQuestionRelationService;

    @Resource
    private StudentSubjectService studentSubjectService;

    @Resource
    private ExerciseInfoService exerciseInfoService;

    @Resource
    private ExerciseLogService exerciseLogService;

    @GetMapping("count")
    public ResultResponse getCount(int isPublic, HttpServletRequest request) {

        int questionCount = questionBankService.getQuestionCount(isPublic);
        return ResultResponse.RETURN(1, "ok", questionCount);
    }

    @GetMapping("subPage")
    public ResultResponse getQuestionSubPage(int pageStart, int pageSize, int isPublic) {
        /**
         * 返回参数：
         * 1. 问题列表
         * 2. 分页情况
         *      当前页
         *      总页数
         */
        // 获取总记录数
        int questionCount = questionBankService.getQuestionCount(isPublic);
        // 创建分页对象
        PageUtil pageUtil = new PageUtil(questionCount, pageSize);
        // 获取开始的分页索引
        int startIndex = pageUtil.getStartIndex(pageStart);

        // 获取问题列表
        List<QuestionBank> questionSubPage = questionBankService.getQuestionSubPage(startIndex, pageSize, isPublic);

        // Todo
        // List<QuestionDTO> list = Tools.questionDAOListTransformDTO(questionSubPage);

        HashMap<String, Object> map = new HashMap<>();
        map.put("pageMessage", pageUtil);
        map.put("questionList", questionSubPage);
        return ResultResponse.RETURN(1, "ok", map);
    }

    /**
     * 首页的四个数量
     */
    @GetMapping("homeNumbers")
    public ResultResponse getHomeNumber(HttpServletRequest request) {

        Identity identity = (Identity) request.getAttribute("identity");
        boolean green = IdentityUtils.greenAdminAndTeacher(identity);
        if (!green) {
            return ResultResponse.RETURN(2, "fail: 权限不足", null);
        }
        Long id = identity.getId();

        HomeQuestionNumber homeNumber = questionBankService.getHomeNumber(id);
        return ResultResponse.RETURN(1, "ok", homeNumber);
    }

    /**
     * 添加问题
     */
    @PostMapping("save")
    public ResultResponse saveQuestion(QuestionBankVO questionBankVO) {

        /**
         * 参数校验
         * 1. 判断 isPublic 是否为 0 或 1
         * 2. 判断 subjectId 是否在学科列表中
         *  查询并返回
         * 3. 判断 type 习题类型
         * 4. question、option、answer、uuidShort是否为空
         * 5. difficulty 是否为 简单、一般、困难-QuestionDifficulty
         */

        QuestionBank questionBank = new QuestionBank();
        BeanUtils.copyProperties(questionBankVO, questionBank);
        log.info("question-saveQuestion::::" + questionBank.toString());

        // 1
        if (questionBank.getIsPublic() != 0 && questionBank.getIsPublic() != 1) {
            return ResultResponse.RETURN(5, "isPublic为0或1", null);
        }

        // 2
        try {
            Subject subjectOne = subjectService.selectSubjectById(questionBank.getSubjectId());
            if (subjectOne == null) {
                return ResultResponse.RETURN(6, "subjectId不是正确的学科Id、或subjectId为空", null);
            } else {
                questionBank.setSubject(subjectOne.getSubjectName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultResponse.RETURN(6, "subjectId不是正确的学科Id、或subjectId为空", null);
        }

        // 3
        if (
                Tools.isNullStr(questionBank.getType()) || QuestionType.queryIndex(questionBank.getType().trim()) == 0
        ) {
            return ResultResponse.RETURN(7, "题型只能为：单选、多选、简答", null);
        }

        // 4  如果为选择题，需要判断选项是否为null以及UuidShort是否为null
        if (QuestionType.SINGLECHOICE.type.equals(questionBank.getType().trim()) || QuestionType.MULTIPLECHOICE.type.equals(questionBank.getType().trim())) {
            if (Tools.isNullAllStr(questionBank.getOption(), questionBank.getUuidShort())) {
                return ResultResponse.RETURN(8, "当题目类型为单选或多选时，option 和 uuidShort 不能为空", null);
            }
        }
        if (Tools.isNullStr(questionBank.getAnswer())) {
            return ResultResponse.RETURN(9, "answer不能为空", null);
        }

        // 5
        if (QuestionDifficulty.queryIndex(questionBank.getDifficulty()) == 0) {
            return ResultResponse.RETURN(10, "difficulty值为简单、一般、困难", null);
        }

        QuestionBank result = null;
        try {
            result = questionBankService.saveQuestion(questionBank);
            if (result == null) {
                return ResultResponse.RETURN(2, "问题已经存在", null);
            }
        } catch (Exception e) {
            log.error("question-saveQuestion::::~~~~~~" + e);
            return ResultResponse.RETURN(3, e.getMessage(), null);
        }
        // QuestionDTO questionDTO = Tools.questionDAOTransformDTO(result);
        return ResultResponse.RETURN(1, "添加成功", result);
    }

    /**
     * 通用问题筛选：筛选问题
     */
    @PostMapping("screen")
    public ResultResponse screenQuestion(int pageStart, int pageSize, QuestionBank questionBank, String errorRate) {

        if (Tools.isNullStr(errorRate)){
            errorRate = "NO";
        }else {
            if (! (errorRate.trim().equalsIgnoreCase("desc") || errorRate.trim().equalsIgnoreCase("asc"))){
                errorRate = "NO";
            }
        }


        if (Tools.isNullStr(questionBank.getType())) {
            questionBank.setType(null);
        }

        if (Tools.isNullStr(questionBank.getDifficulty())) {
            questionBank.setDifficulty(null);
        }

        System.err.println(questionBank);

        // 获取总记录数
        int questionCount = questionBankService.getCountByQuestion(questionBank);
        // 创建分页对象
        PageUtil pageUtil = new PageUtil(questionCount, pageSize);
        // 获取开始的分页索引
        int startIndex = pageUtil.getStartIndex(pageStart);

        List<QuestionBank> questionList = questionBankService.getList(startIndex, pageSize, questionBank, errorRate);
        // List<QuestionDTO> list = Tools.questionDAOListTransformDTO(questionList);

        HashMap<String, Object> map = new HashMap<>();
        map.put("pageMessage", pageUtil);
        map.put("questionList", questionList);
        return ResultResponse.RETURN(1, "ok", map);
    }

    /**
     * 修改问题
     */
    @PostMapping("modify")
    public ResultResponse modifyQuestion(QuestionBank questionBank) {

        int i = 0;
        try {
            i = questionBankService.updateQuestionById(questionBank);
        } catch (Exception e) {
            return ResultResponse.RETURN(1, "success", 0);
        }

        if (i > 0) {
            return ResultResponse.RETURN(1, "success", 1);
        }
        return ResultResponse.RETURN(1, "success", 0);
    }

    /**
     * 随机一题
     */
    @GetMapping("randomQuestionOne")
    public ResultResponse randomQuestionOne(Long studentId) {
        /**
         * 1. 通过学生id获取科目
         * 2. 通过科目获取题目
         *  2.1 获取相关题目总数
         *  2.2 产生随机数
         */

        // 判断学生是否已经选课
        List<Subject> subjectList = studentSubjectService.querySubject(studentId);
        if (subjectList == null) {
            return ResultResponse.RETURN(2, "fail: 请先选择课程", null);
        }

        QuestionBank questionBank = questionBankService.randomQuestionOne(studentId);
        QuestionDTO questionDTO = Tools.questionDAOTransformDTO(questionBank);
        return ResultResponse.RETURN(1, "success", questionDTO);
    }

    /**
     * 判断题目正确性
     */
    @PostMapping("readOne")
    @Transactional
    public ResultResponse readQuestionOne(Long questionId, String answer, HttpServletRequest request) {

        Identity identity = (Identity) request.getAttribute("identity");
        long studentId = identity.getId();
        boolean green = IdentityUtils.greenStudent(identity);
        if (!green) {
            return ResultResponse.RETURN(2, "fail: 权限不足", false);
        }

        /**
         * 1. 参数获取学生id
         *  判断题目难度
         *  判断是否正确
         *  在info中为当前学生更新信息
         * 2. 更新exercise_log
         */

        JSONObject jsonObject = new JSONObject();
        QuestionBank questionBank = questionBankService.selectQuestionById(Math.toIntExact(questionId));
        if (Tools.isNullAllStr(answer, questionBank.getAnswer())) {
            jsonObject.put("correct", false);
            jsonObject.put("answer", null);
            return ResultResponse.RETURN(1, "success", jsonObject);
        }

        // info
        ExerciseInfoDTO exerciseInfoDTO = new ExerciseInfoDTO(studentId);

        // log
        ExerciseLog exerciseLog = new ExerciseLog();
        exerciseLog.setStudentId(studentId);
        exerciseLog.setQuestionId(Long.valueOf(questionBank.getQuestionId()));

        // Tools.similarityString(questionBank.getAnswer(), answer)
        // questionBank.getAnswer().equalsIgnoreCase(answer)

        // TODO modify
        double similarityNumber = Tools.similarityString(questionBank.getAnswer(), answer);

        // TODO
        if (similarityNumber >= 1  && questionBank.getAnswer().length() == answer.length()) {
            jsonObject.put("correct", true);
            jsonObject.put("score", questionBank.getScore());
            jsonObject.put("get", questionBank.getScore());
            // 正确（正确+1, 总数+1）
            exerciseInfoDTO.setCorrect(1);
            switch (questionBank.getDifficulty()){
                case "困难":
                    exerciseInfoDTO.setDifficulty(1);
                    exerciseInfoDTO.setDifficultyCorrect(1);
                    break;
                case "一般":
                    exerciseInfoDTO.setGeneral(1);
                    exerciseInfoDTO.setGeneralCorrect(1);
                    break;
                case "简单":
                    exerciseInfoDTO.setEasy(1);
                    exerciseInfoDTO.setEasyCorrect(1);
                    break;
                default:
                    return ResultResponse.RETURN(5, "无法判断问题类型", null);
            }
            exerciseLog.setIsCorrect(1);
        } else if (questionBank.getType().equals("简答")){

            // TODO
            double v = similarityNumber;
            if (v > 0.6){
                jsonObject.put("correct", true);// 正确性
                exerciseInfoDTO.setCorrect(1);
                switch (questionBank.getDifficulty()){
                    case "困难":
                        exerciseInfoDTO.setDifficulty(1);
                        exerciseInfoDTO.setDifficultyCorrect(1);
                        break;
                    case "一般":
                        exerciseInfoDTO.setGeneral(1);
                        exerciseInfoDTO.setGeneralCorrect(1);
                        break;
                    case "简单":
                        exerciseInfoDTO.setEasy(1);
                        exerciseInfoDTO.setEasyCorrect(1);
                        break;
                    default:
                        return ResultResponse.RETURN(5, "无法判断问题类型", null);
                }
            }else {
                jsonObject.put("correct", false);// 正确性
                exerciseInfoDTO.setError(1);
                switch (questionBank.getDifficulty()){
                    case "困难":
                        exerciseInfoDTO.setDifficulty(1);
                        break;
                    case "一般":
                        exerciseInfoDTO.setGeneral(1);
                        break;
                    case "简单":
                        exerciseInfoDTO.setEasy(1);
                        break;
                    default:
                        return ResultResponse.RETURN(5, "无法判断问题类型", null);
                }
            }
            jsonObject.put("score", questionBank.getScore());
            jsonObject.put("get", questionBank.getScore() * v);

        }else  {
            // 错误
            jsonObject.put("correct", false);
            jsonObject.put("score", questionBank.getScore());
            jsonObject.put("get", 0);
            exerciseInfoDTO.setError(1);
            switch (questionBank.getDifficulty()){
                case "困难":
                    exerciseInfoDTO.setDifficulty(1);
                    break;
                case "一般":
                    exerciseInfoDTO.setGeneral(1);
                    break;
                case "简单":
                    exerciseInfoDTO.setEasy(1);
                    break;
                default:
                    return ResultResponse.RETURN(5, "无法判断问题类型", null);
            }
            exerciseLog.setIsCorrect(0);
        }

        // 更新exercise_log
        int logI = exerciseLogService.insertLog(exerciseLog);
        if (logI > 0){
            // 在info中为当前学生更新信息
            System.err.println(exerciseInfoDTO);
            int i = exerciseInfoService.updateInfo(exerciseInfoDTO);
            System.err.println(i);
        }

        jsonObject.put("answer", questionBank.getAnswer());
        return ResultResponse.RETURN(1, "success", jsonObject);
    }

    /**
     * 教师端：搜索问题
     */
    @PostMapping("search")
    public ResultResponse searchQuestion(String searchKey, int pageStart, int pageSize, HttpServletRequest request) {
        Identity identity = (Identity) request.getAttribute("identity");
        boolean green = IdentityUtils.greenAll(identity);
        if (!green) {
            return ResultResponse.RETURN(2, "fail: 权限不足: 不在用户列表", null);
        }

        // 获取用户id
        int id = Math.toIntExact(identity.getId());

        // 获取总记录数
        int questionCount = questionBankService.selectLikeKeyCount(searchKey, id);
        // 创建分页对象
        PageUtil pageUtil = new PageUtil(questionCount, pageSize);
        // 获取开始的分页索引
        int startIndex = pageUtil.getStartIndex(pageStart);

        List<QuestionBank> searchList = questionBankService.selectLikeKey(searchKey, id, startIndex, pageSize);

        // List<QuestionDTO> list = Tools.questionDAOListTransformDTO(searchList);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pageInfo", pageUtil);
        jsonObject.put("searchList", searchList);
        return ResultResponse.RETURN(1, "success", jsonObject);
    }

    /**
     * 学生端：模糊搜索
     */
    @PostMapping("search2")
    public ResultResponse searchQuestion2(String searchKey, int pageStart, int pageSize, HttpServletRequest request) {
        Identity identity = (Identity) request.getAttribute("identity");
        boolean green = IdentityUtils.greenAll(identity);
        if (!green) {
            return ResultResponse.RETURN(2, "fail: 权限不足: 不在用户列表", null);
        }

        // 获取总记录数
        int questionCount = questionBankService.selectLikeKeyCount(searchKey);
        // 创建分页对象
        PageUtil pageUtil = new PageUtil(questionCount, pageSize);
        // 获取开始的分页索引
        int startIndex = pageUtil.getStartIndex(pageStart);

        List<QuestionBank> searchList = questionBankService.selectLikeKey(searchKey, startIndex, pageSize);

        List<QuestionDTO> list = Tools.questionDAOListTransformDTO(searchList);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pageInfo", pageUtil);
        jsonObject.put("searchList", list);
        return ResultResponse.RETURN(1, "success", jsonObject);
    }

    /** 学生：问题是否被收藏 */
    @PostMapping("isCollect")
    public ResultResponse collectWork(Long questionId, HttpServletRequest request){
        Identity identity = (Identity)request.getAttribute("identity");
        Long studentId = identity.getId();
        boolean green = IdentityUtils.greenStudent(identity);
        if (!green){
            return ResultResponse.RETURN(2, "fail: 权限不足", false);
        }
        if (!(questionId > 0 && studentId > 0)){
            return ResultResponse.RETURN(3, "fail: 参数错误", false);
        }

        boolean collect = questionBankService.isCollect(questionId, studentId);
        return ResultResponse.RETURN(1, "success", collect);

    }

    /** 学生：收藏问题与取消收藏 */
    @PostMapping("collect")
    public ResultResponse collectWork(Long questionId, boolean cancelOrCollect, HttpServletRequest request){
        Identity identity = (Identity)request.getAttribute("identity");
        Long studentId = identity.getId();
        boolean green = IdentityUtils.greenStudent(identity);
        if (!green){
            return ResultResponse.RETURN(2, "fail: 权限不足", false);
        }
        if (!(questionId > 0 && studentId > 0)){
            return ResultResponse.RETURN(3, "fail: 参数错误", false);
        }
        boolean b = false;
        String message = "出错";
        if (cancelOrCollect){
            b = questionBankService.collectQuestion(questionId, studentId);
            message = b ? "success : 收藏成功" : "success : 已经收藏";
        }else {
            b = questionBankService.cancelCollectQuestion(questionId, studentId);
            message = b ? "success : 取消收藏" : "success : 未收藏过";
        }
        return ResultResponse.RETURN(1, message, b);
    }

    /** 学生：获取收藏问题的列表 */
    @PostMapping("collectCount")
    public ResultResponse countCollectWork(@RequestParam(required = false, defaultValue = "1") Integer startPage,
                                      @RequestParam(required = false, defaultValue = "10") Integer pageSize, HttpServletRequest request){
        Identity identity = (Identity)request.getAttribute("identity");
        long studentId = identity.getId();
        boolean green = IdentityUtils.greenStudent(identity);
        if (!green){
            return ResultResponse.RETURN(2, "fail: 权限不足", false);
        }

        int total = questionBankService.countCollectQuestion(studentId);
        PageUtil pageInfo = new PageUtil(total,pageSize);
        List<QuestionBank> QuestionBanks = questionBankService.selectCollectQuestion(studentId, pageInfo.getStartIndex(startPage), pageSize);

        JSONObject result = new JSONObject();
        result.put("pageInfo", pageInfo);
        result.put("QuestionBanks", QuestionBanks);
        return ResultResponse.RETURN(1, "success", result);
    }

    @Value("${file.temp.tempPathLocal}")
    private String tempPath;
    /** 批量导入问题 */
    @PostMapping("importMore")
    public ResultResponse importMoreQuestion(MultipartFile excel, Integer workId, HttpServletRequest request) {

        Identity identity = (Identity) request.getAttribute("identity");
        boolean green = IdentityUtils.greenAdminAndTeacher(identity);
        if (!green) {
            return ResultResponse.RETURN(2, "fail: 权限不足", null);
        }

        // 获取用户id
        long id = identity.getId();

        try {
            if (excel.isEmpty()) {
                return ResultResponse.RETURN(3, "fail:表不存在", null);
            }
        } catch (Exception e) {
            return ResultResponse.RETURN(3, "fail:表不存在", null);
        }

        // 获取文件名称
        String fileName = excel.getOriginalFilename();// test1.xlsx

        // 获取扩展名
        int last = fileName.lastIndexOf(".");
        String expandedName = fileName.substring(last + 1);
        if (Tools.isNullStr(expandedName) ||
                !(expandedName.equalsIgnoreCase("xls") ||
                        expandedName.equalsIgnoreCase("xlsx"))
        ) {
            return ResultResponse.RETURN(3, "fail:表只能为xls或xlsx", null);
        }


        // 将文件暂存在本地
        String tempFileName = tempPath + UUID.randomUUID().toString().replace("-", "") + ".xlsx";
        File file = new File(tempFileName);
        try {
            // 将二进制流写入file
            excel.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultResponse.RETURN(4, "fail:错误的表文件", null);
        }

        // 根据作业id获取作业，得到作业所属的subjectId
        Work work = workService.getWorkById(workId);
        if (work.getSubjectId() == null || work.getSubjectId() == 0) {
            work.setWorkId(1);
            // return ResultResponse.RETURN(5, "fail:请为当前作业设置一个所属科目", null);
        }

        // 获取达到问题的集合
        List<QuestionBank> list = disposeXlsx(tempFileName, id, work.getSubjectId());

        // 删除临时文件
        file.delete();

        // 返回插入的问题
        List<QuestionBank> resultQList = questionBankService.insertQuestionBankList((int) id, list);
        List<Integer> list1 = new ArrayList<>();
        resultQList.forEach(item -> {
            list1.add(item.getQuestionId());
        });

        // 将问题添加到作业中
        int i = workQuestionRelationService.insertQuestionToWork(workId, list1);

        // 获取作业内容
        WorkQ workQ = workQuestionRelationService.selectWorkContent((int) id, workId);

//        WorkQDTO workQDTO = new WorkQDTO();
//        BeanUtils.copyProperties(workQ, workQDTO);
//        List<QuestionDTO> list2 = Tools.questionDAOListTransformDTO(workQ.getQuestionList());
//        workQDTO.setQuestions(list2);

        return ResultResponse.RETURN(1, "success", workQ);

    }

    public List<QuestionBank> disposeXlsx(String path, Long teacherId, Integer subjectId) {
        try {
            FileInputStream file = new FileInputStream(path);
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);

            XSSFRow row = null;

            List<QuestionBank> list = new ArrayList<>();


            for (int i = 1; sheet.getRow(i) != null; i++) {
                QuestionBank q = new QuestionBank();
                row = sheet.getRow(i);
                if (row.getCell(0) != null) {
                    q.setType(row.getCell(0).toString());
                } else {
                    q.setType(null);
                }

                if (row.getCell(1) != null) {
                    q.setQuestion(row.getCell(1).toString());
                } else {
                    q.setQuestion(null);
                }

                if (row.getCell(2) != null) {

                    String option = row.getCell(2).toString();
                    String reg = "(,*[a-d|A-D]\\.)|(，*[a-d|A-D]、)|(，*[a-d|A-D]\\.)|(,*[a-d|A-D]、)";
                    String[] split = option.split(reg);
                    for (int ii = 1; ii < 5; ii++) {
                        if (split[ii] == null) {
                            split[ii] = "";
                        }
                    }
                    String option2 = "A." + split[1] + "&qfjl&" + "B." +
                            split[2] + "&qfjl&" + "C." + split[3] + "&qfjl&" + "D." + split[4];
                    q.setUuidShort("&qfjl&");
                    q.setOption(option2);
                } else {
                    q.setOption(null);
                }

                if (row.getCell(3) != null) {
                    q.setAnswer(row.getCell(3).toString());
                } else {
                    q.setAnswer(null);
                }

                if (row.getCell(4) != null) {
                    int score = (int) Double.parseDouble(row.getCell(4).toString());
                    q.setScore(score);
                } else {
                    q.setScore(5);
                }

                if (row.getCell(5) != null) {
                    q.setDifficulty(row.getCell(5).toString());
                } else {
                    q.setDifficulty("一般");
                }
                q.setTeacherId(Math.toIntExact(teacherId));
                q.setSubjectId(subjectId);
                list.add(q);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    @Value("${file.file.questionTemplateLocalPath}")
    private String questionTemplateLocalPath;
    /** 下载Excel模板 */
    @GetMapping("downloadQuestionExcel")
    public ResultResponse downloadQuestionExcel(HttpServletRequest request, HttpServletResponse response) {
        // 要下载的文件
        String downFileName = "question_template.xlsx";

        // 设置相应头，告诉浏览器该文件可以下载
        response.setContentType(request.getServletContext().getMimeType(downFileName));
        response.setHeader("Content-Disposition", "attachment;filename=" + downFileName);

        // 获取download文件夹下的文件绝对路径
        String excelLocalPath = questionTemplateLocalPath + downFileName;
        try {
            // 将文件写到response缓冲区
            InputStream inputStream = new FileInputStream(excelLocalPath);
            response.setHeader("Content-Length", String.valueOf(inputStream.available()));
            // 读取到文件
            ServletOutputStream outputStream = response.getOutputStream();
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
            }
            inputStream.close();
            // outputStream: response获得的刘不用手动关闭，会自动关闭

            return ResultResponse.RETURN(1, "success", 1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return ResultResponse.RETURN(2, "fail1:" + e.getMessage(), null);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultResponse.RETURN(2, "fail2:" + e.getMessage(), null);
        }

    }

}
