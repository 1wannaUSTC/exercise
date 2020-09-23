package com.one.exercise.controller;

import com.one.exercise.pojo.Identity;
import com.one.exercise.pojo.ResultResponse;
import com.one.exercise.pojo.Teacher;
import com.one.exercise.pojo.TeacherVO;
import com.one.exercise.pojo.notification.*;
import com.one.exercise.service.NotificationRelationService;
import com.one.exercise.service.NotificationService;
import com.one.exercise.service.StudentRelationTeacherService;
import com.one.exercise.service.TeacherService;
import com.one.exercise.utils.IdentityUtils;
import com.one.exercise.utils.PageUtil;
import com.one.exercise.utils.Tools;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 老师 || 学生 -> 通知
 */
@RestController
@RequestMapping("notify")
public class NotificationController {

    @Resource
    private NotificationService notificationService;

    @Resource
    private NotificationRelationService notificationRelationService;

    @Resource
    private StudentRelationTeacherService studentRelationTeacherService;

    @Resource
    private TeacherService teacherService;

    /**
     * 教师：发送通知
     */
    @PostMapping("send")
    @Transactional
    public ResultResponse sendNotify(NotificationBO notificationBO, HttpServletRequest request){
        Identity identity = (Identity)request.getAttribute("identity");
        long teacherId = identity.getId();
        boolean green = IdentityUtils.greenAdminAndTeacher(identity);
        if (!green){
            return ResultResponse.RETURN(2, "fail: 权限不足", false);
        }

        /**
         * 1. 创建通知
         * 2. 推送通知
         *  2.1 获取老师所有学生id
         *  2.2 将消息推送至所有学生
         */
        try {
            if (Tools.isNullAllStr(notificationBO.getTitle(), notificationBO.getContent())){
                return ResultResponse.RETURN(3, "参数缺失", false);
            }
        }catch (Exception e){
            return ResultResponse.RETURN(3, "参数缺失: " + e.getMessage(), false);
        }

        Notification notification = new Notification();
        BeanUtils.copyProperties(notificationBO, notification);

        notification.setSender(Math.toIntExact(teacherId));
        Notification notificationResult = notificationService.insertNotification(notification);
        long messageId = notificationResult.getMessageId();

        List<Long> idList = studentRelationTeacherService.selectStudentIdByTeacherId(teacherId);

        List<NotificationRelation> relations = new ArrayList<>();
        idList.forEach(item->{
            NotificationRelation notificationRelation = new NotificationRelation();
            notificationRelation.setMessageId(messageId);
            notificationRelation.setSender(teacherId);
            notificationRelation.setReceiver(item);
            notificationRelation.setReadTime(null);
            notificationRelation.setIsRead(0);
            relations.add(notificationRelation);
        });

        int i = notificationRelationService.relationList(relations);

        if (idList.size() == i || i > 0){
            NotificationVO notificationVO = new NotificationVO();
            BeanUtils.copyProperties(notificationResult, notificationVO);
            return ResultResponse.RETURN(1, "success", notificationResult);
        }
        return ResultResponse.RETURN(4, "fail", null);
    }

    /**
     * 学生：阅读通知
     */
    @PostMapping("read")
    public ResultResponse readNotify(Long relationId, HttpServletRequest request){

        Identity identity = (Identity)request.getAttribute("identity");
        long studentId = identity.getId();
        boolean green = IdentityUtils.greenStudent(identity);
        if (!green){
            return ResultResponse.RETURN(2, "fail: 权限不足", false);
        }

        /**
         * 1. 判断是否已读
         *  未读. 学生ID + 通知ID 更新状态为 1 ， 对应的通知，已读数量加一
         *  已读。直接返回notificationRelation
         *
         */
        NotificationRelation notificationRelation = notificationRelationService.readNotify(relationId, studentId);
        NotificationRelationVO notificationRelationVO = new NotificationRelationVO();
        BeanUtils.copyProperties(notificationRelation, notificationRelationVO);

        return ResultResponse.RETURN(1, "success", notificationRelationVO);
    }

    /**
     * 学生：获取通知列表
     */
    @PostMapping("query")
    public ResultResponse queryNotify(@RequestParam(required = false, defaultValue = "1") Integer startPage,
                                      @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                      HttpServletRequest request){

        Identity identity = (Identity)request.getAttribute("identity");
        long studentId = identity.getId();
        boolean green = IdentityUtils.greenStudent(identity);
        if (!green){
            return ResultResponse.RETURN(2, "fail: 权限不足", null);
        }
        /**
         * 1. 获取学生id
         */
        List<NotificationRelation> relations = notificationRelationService.queryNotifyListByStudentId(studentId);

        List<Long> messageIdList = new ArrayList<>();
        for (NotificationRelation relation : relations) {
            messageIdList.add(relation.getMessageId());
        }
        String messageIdStr = messageIdList.toString().replace("[", "(").replace("]", ")");

        if (messageIdList.size() <= 0){
            return ResultResponse.RETURN(1, "success", null);
        }

        /**
         * 创建分页对象
         */
        PageUtil pageUtil = new PageUtil(messageIdList.size(), 10);

        // 通过 messageIdStr 获取通知内容列表
        List<Notification> notifications = notificationService.selectNotifyListByMessages(messageIdStr, pageUtil.getStartIndex(startPage), pageSize);

        // 获取每个老师信息
        List<Integer> teacherIdList = new ArrayList<>();
        for (Notification notification : notifications) {
            teacherIdList.add(notification.getSender());
        }

        List<Teacher> teacherList = teacherService.selectTeacherByIdList(teacherIdList);

        /**
         * List
         *  Map
         *      notification
         *      notificationState
         */
        List<Map<String, Object>> result = new ArrayList<>();
        for (Notification notification : notifications) {

            Map<String, Object> map = new HashMap<>();
            for (NotificationRelation relation : relations) {
                if (notification.getMessageId() == relation.getMessageId()){
                    NotificationVO notificationVO = new NotificationVO();
                    BeanUtils.copyProperties(notification, notificationVO);

                    NotificationRelationVO notificationRelationVO = new NotificationRelationVO();
                    BeanUtils.copyProperties(relation, notificationRelationVO);
                    map.put("notification", notificationVO);
                    map.put("notificationState", notificationRelationVO);

                }
            }

            // 添加发送通知的老师的信息
            int teacherId1 = notification.getSender();
            for (Teacher teacher : teacherList) {
                int teacherId2 = teacher.getTeacherId();
                if (teacherId1 == teacherId2){
                    TeacherVO teacherVo = new TeacherVO();
                    BeanUtils.copyProperties(teacher, teacherVo);
                    map.put("teacher", teacherVo);
                }
            }

            result.add(map);
        }

        return ResultResponse.RETURN(1, "success", result);
    }

    /** 学生：删除通知 */
    @PostMapping("deleteNotify")
    public ResultResponse deleteNotify(Long relationId, HttpServletRequest request){

        Identity identity = (Identity)request.getAttribute("identity");
        long studentId = identity.getId();
        boolean green = IdentityUtils.greenStudent(identity);
        if (!green){
            return ResultResponse.RETURN(2, "fail: 权限不足", false);
        }

        int i = notificationRelationService.deleteNotify(studentId , relationId);

        return ResultResponse.RETURN(1, "success", i > 0);


    }
}
