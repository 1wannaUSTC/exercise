package com.one.exercise.service.impl;

import com.one.exercise.mapper.NotificationMapper;
import com.one.exercise.pojo.notification.Notification;
import com.one.exercise.service.NotificationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Resource
    private NotificationMapper notificationMapper;

    @Override
    public Notification insertNotification(Notification notification) {
        int i = notificationMapper.insertSelective(notification);

        Notification notificationResult = notificationMapper.selectByPrimaryKey(notification.getMessageId());

        return notificationResult;
    }

    @Override
    public List<Notification> selectNotifyListByMessages(String messageIdStr, Integer startIndex, Integer pageSize) {
        return notificationMapper.selectNotifyListByMessages(messageIdStr, startIndex, pageSize);
    }
}
