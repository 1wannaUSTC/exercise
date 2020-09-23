package com.one.exercise.service;

import com.one.exercise.pojo.notification.Notification;

import java.util.List;

public interface NotificationService {

    // 创建通知: 如果常见成功，返回通知id，否则返回0
    Notification insertNotification(Notification notification);

    /**
     * 通过传递 (1, 2, 3, 4) 此形式字符串获取 Notification 列表
     * @param messageIdStr
     * @return
     */
    List<Notification> selectNotifyListByMessages(String messageIdStr, Integer startIndex, Integer pageSize);
}
