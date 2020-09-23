package com.one.exercise.controller;

import com.alibaba.fastjson.JSONObject;
import com.one.exercise.pojo.*;
import com.one.exercise.pojo.admire.AdmireVO;
import com.one.exercise.pojo.comment.CommentBO;
import com.one.exercise.pojo.comment.CommentDTO;
import com.one.exercise.pojo.dynamicmessage.DynamicMessageBO;
import com.one.exercise.pojo.dynamicmessage.DynamicMessageDTO;
import com.one.exercise.pojo.writeback.WriteBackBO;
import com.one.exercise.service.AdmireService;
import com.one.exercise.service.CommentService;
import com.one.exercise.service.DynamicMessageService;
import com.one.exercise.service.WriteBackService;
import com.one.exercise.utils.IdentityUtils;
import com.one.exercise.utils.PageUtil;
import com.one.exercise.utils.Tools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("dynamicMessage")
public class DynamicMessageController {

    @Resource
    private DynamicMessageService dynamicMessageService;

    @Resource
    private AdmireService admireService;

    @Resource
    private CommentService commentService;

    @Resource
    private WriteBackService writeBackService;

    @Value("${file.img.imagePathLocal}")
    private String imagePathLocal;

    @Value("${file.img.imagePathWeb}")
    private String imagePathWeb;

    /**
     * 学生端：发布动态
     */
    @PostMapping("save")
    public ResultResponse save(@RequestBody DynamicMessageBO dynamicMessageBO, HttpServletRequest request) {

        Identity identity = (Identity) request.getAttribute("identity");
        long studentId = identity.getId();
        boolean green = IdentityUtils.greenStudent(identity);
        if (!green) {
            return ResultResponse.RETURN(2, "fail: 权限不足", null);
        }
        /**
         * 参数校验
         */
        if (Tools.isNullAllStr(dynamicMessageBO.getTitle(), dynamicMessageBO.getContent())){
            return ResultResponse.RETURN(3, "fail: title或content参数为空", null);
        }

        DynamicMessage dynamicMessage = new DynamicMessage();
        BeanUtils.copyProperties(dynamicMessageBO, dynamicMessage);
        dynamicMessage.setInitiator(studentId);

        /**
         * 获取上传的图片
         */
        List<String> images;
        try {
            images = Arrays.asList(dynamicMessageBO.getPictures());
        }catch (Exception e){
            images = null;
        }

        /**
         * 1. 保存动态
         * 2. 保存图片
         */
        boolean result = dynamicMessageService.saveDynamicMessage(dynamicMessage, images);

        return ResultResponse.RETURN(1, "success", result);
    }

    /**
     * 学生端：获取动态
     * pattern
     *  1 newest    最新
     *  2 hot       推荐
     */
    @PostMapping("query")
    public ResultResponse query(Integer pattern,
                                @RequestParam(required = false, defaultValue = "1") Integer startPage,
                                @RequestParam(required = false, defaultValue = "15") Integer pageSize,
                                HttpServletRequest request){

        System.err.println(startPage + " " + pageSize);

        Identity identity = (Identity) request.getAttribute("identity");
        long studentId = identity.getId();
        boolean green = IdentityUtils.greenStudent(identity);
        if (!green) {
            return ResultResponse.RETURN(2, "fail: 权限不足", null);
        }

        if (pattern == null || !(pattern == 1 || pattern == 2)){
            return ResultResponse.RETURN(3, "fail: pattern参数只能为0或1", null);
        }

        int total = dynamicMessageService.countAll();
        if (total == 0){
            return ResultResponse.RETURN(1, "success: 暂无动态", null);
        }


        PageUtil pageInfo = new PageUtil(total, pageSize);

        List<DynamicMessageDTO> list = dynamicMessageService.queryNewestHot(studentId, pattern, pageInfo.getStartIndex(startPage), pageSize);

        JSONObject result = new JSONObject();
        result.put("pageInfo", pageInfo);
        result.put("dynamicMessageList", list);

        return ResultResponse.RETURN(1, "success", result);
    }

    /**
     * 学生端：学生查看自己发布的动态
     */
    @PostMapping("queryMe")
    public ResultResponse queryMe(HttpServletRequest request,
                                  @RequestParam(required = false, defaultValue = "1") Integer startPage,
                                  @RequestParam(required = false, defaultValue = "15") Integer pageSize){
        Identity identity = (Identity) request.getAttribute("identity");
        long studentId = identity.getId();
        boolean green = IdentityUtils.greenStudent(identity);
        if (!green) {
            return ResultResponse.RETURN(2, "fail: 权限不足", null);
        }

        /**
         * 1. 获取所有该学生所有动态数量
         * 2. 生成分页信息
         * 3. 分页查询
         */
        DynamicMessage dynamicMessage = new DynamicMessage();
        dynamicMessage.setValidity(1);
        dynamicMessage.setInitiator(studentId);
        int total = dynamicMessageService.countAll(dynamicMessage);

        PageUtil pageInfo = new PageUtil(total, pageSize);

        List<DynamicMessageDTO> list = dynamicMessageService.queryMe(studentId, pageInfo.getStartIndex(startPage), pageSize);
        JSONObject result = new JSONObject();
        result.put("pageInfo", pageInfo);
        result.put("dynamicMessage", list);

        return ResultResponse.RETURN(1, "success", result);
    }


    /**
     * 学生端：点赞、取消点赞
     */
    @PostMapping("like")
    public ResultResponse like(HttpServletRequest request,
                               Long dyId,
                               @RequestParam(defaultValue = "true", required = false) Boolean isLike){
        Identity identity = (Identity) request.getAttribute("identity");
        long studentId = identity.getId();
        boolean green = IdentityUtils.greenStudent(identity);
        if (!green) {
            return ResultResponse.RETURN(2, "fail: 权限不足", null);
        }

        if (dyId == null || dyId <= 0){
            return ResultResponse.RETURN(3, "dyId参数错误", null);
        }

        Admire admire = new Admire();
        admire.setAdmireLiker(studentId);
        admire.setDyId(dyId);

        /**
         * 点赞
         */
        boolean isOk = false;
        if (isLike){
            isOk = admireService.insertAdmire(admire);
        }else {
            isOk = admireService.deleteAdmire(admire);
        }


        return ResultResponse.RETURN(1, "success", isOk);
    }

    /**
     * 学生端：点赞列表
     */
    @PostMapping("likeList")
    public ResultResponse likeList(HttpServletRequest request){
        Identity identity = (Identity) request.getAttribute("identity");
        long studentId = identity.getId();
        boolean green = IdentityUtils.greenStudent(identity);

        if (!green) {
            return ResultResponse.RETURN(2, "fail: 权限不足", null);
        }
        List<AdmireVO> admireVOs = admireService.selectAdmireList();
        return ResultResponse.RETURN(1, "success", admireVOs);
    }

    /**
     * 学生端：点赞信息标记已读
     */
    @PostMapping("readLike")
    public ResultResponse readLike(HttpServletRequest request,
                                   Long admireId){
        Identity identity = (Identity) request.getAttribute("identity");
        boolean green = IdentityUtils.greenStudent(identity);
        if (!green) {
            return ResultResponse.RETURN(2, "fail: 权限不足", null);
        }

        Admire admire = new Admire();
        admire.setAdmireId(admireId);
        admire.setIsRead(1);
        boolean isOk = admireService.updateAdmireRead(admire);
        return ResultResponse.RETURN(1, "success", isOk);
    }

    /**
     * 学生端：评论
     */
    @PostMapping("comment")
    public ResultResponse comment(HttpServletRequest request,
                                  CommentBO commentBO){
        Identity identity = (Identity) request.getAttribute("identity");
        long studentId = identity.getId();
        boolean green = IdentityUtils.greenStudent(identity);
        if (!green) {
            return ResultResponse.RETURN(2, "fail: 权限不足", null);
        }
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentBO, comment);
        comment.setInitiator(studentId);

        Comment resultComment = commentService.comment(comment);
        return ResultResponse.RETURN(1, "success", resultComment);
    }

    /**
     * 学生端：回复
     */
    @PostMapping("writeBack")
    public ResultResponse writeBack(WriteBackBO writeBackBO,
                                    HttpServletRequest request){

        Identity identity = (Identity) request.getAttribute("identity");
        long studentId = identity.getId();
        boolean green = IdentityUtils.greenStudent(identity);
        if (!green) {
            return ResultResponse.RETURN(2, "fail: 权限不足", null);
        }

        if (writeBackBO.getBackReceiver() == null || writeBackBO.getBackReceiver() <=0 ||
                writeBackBO.getCommentId() == null || writeBackBO.getCommentId() <= 0 ||
                writeBackBO.getMessage() == null || writeBackBO.getMessage().trim().length() == 0){
            return ResultResponse.RETURN(3, "参数错误", null);
        }

        WriteBack writeBack = new WriteBack();
        BeanUtils.copyProperties(writeBackBO, writeBack);
        writeBack.setBackSender(studentId);

        WriteBack resultWriteBack = writeBackService.writeBack(writeBack);

        return ResultResponse.RETURN(1, "success", resultWriteBack);
    }

    /**
     * 学生端：获取评论列表
     */
    @PostMapping("commentList")
    public ResultResponse commentList(HttpServletRequest request, Long dyId,
                                      @RequestParam(required = false, defaultValue = "1") Integer startPage,
                                      @RequestParam(required = false, defaultValue = "50") Integer pageSize){
        Identity identity = (Identity) request.getAttribute("identity");
        long studentId = identity.getId();
        boolean green = IdentityUtils.greenStudent(identity);
        if (!green) {
            return ResultResponse.RETURN(2, "fail: 权限不足", null);
        }

        List<CommentDTO> comments = commentService.commentList(dyId);
        return ResultResponse.RETURN(1, "success", comments);
    }


    /**
     * 学生端：删除动态
     */
    @PostMapping("removeDy")
    public ResultResponse removeDy(HttpServletRequest request, Long dyId){
        Identity identity = (Identity) request.getAttribute("identity");
        long studentId = identity.getId();
        boolean green = IdentityUtils.greenStudent(identity);
        if (!green) {
            return ResultResponse.RETURN(2, "fail: 权限不足", null);
        }

        if (dyId == null || dyId <= 0){
            return ResultResponse.RETURN(3, "fail: dyId参数错误", false);
        }

        DynamicMessage dynamicMessage = new DynamicMessage();
        dynamicMessage.setDyId(dyId);
        dynamicMessage.setInitiator(studentId);
        boolean isDelete =  dynamicMessageService.deleteDynamicMessage(dynamicMessage);

        return ResultResponse.RETURN(1, "success", isDelete);
    }

    /**
     * 学生端：删除评论
     */
    @PostMapping("removeCom")
    public ResultResponse removeCom(HttpServletRequest request, Long commentId){
        Identity identity = (Identity) request.getAttribute("identity");
        long studentId = identity.getId();
        boolean green = IdentityUtils.greenStudent(identity);
        if (!green) {
            return ResultResponse.RETURN(2, "fail: 权限不足", null);
        }

        Comment comment = new Comment();
        comment.setCommentId(commentId);
        comment.setInitiator(studentId);

        boolean isOk = commentService.deleteComment(comment);

        return ResultResponse.RETURN(1, "success", isOk);

    }

    /**
     * 学生端：删除回复
     */
    @PostMapping("removeBack")
    public ResultResponse removeBack(HttpServletRequest request, Long backId){
        Identity identity = (Identity) request.getAttribute("identity");
        long studentId = identity.getId();
        boolean green = IdentityUtils.greenStudent(identity);
        if (!green) {
            return ResultResponse.RETURN(2, "fail: 权限不足", null);
        }

        WriteBack writeBack = new WriteBack();
        writeBack.setBackSender(studentId);
        writeBack.setBackId(backId);
        boolean isOk = writeBackService.deleteWriteBack(writeBack);

        return ResultResponse.RETURN(1, "success", isOk);
    }


}
