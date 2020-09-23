package com.one.exercise.service;

import com.one.exercise.pojo.notification.NotificationRelation;

import java.util.List;

public interface NotificationRelationService {

    int relationList(List<NotificationRelation> notificationRelationList);

    /**
     * 学生获取通知
     * receiver <=> StudentId
     * @return
     */
    List<NotificationRelation> queryNotifyListByStudentId(Long studentId);

    /**
     * 阅读通知
     */
    NotificationRelation readNotify(Long relationId, Long studentId);


    /**
     * 学生删除通知
     * @param studentId
     * @param relationId
     * @return
     */
    int deleteNotify(long studentId, Long relationId);
}
