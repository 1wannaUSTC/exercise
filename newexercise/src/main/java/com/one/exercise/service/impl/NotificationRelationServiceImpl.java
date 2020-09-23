package com.one.exercise.service.impl;

import com.one.exercise.mapper.NotificationMapper;
import com.one.exercise.mapper.NotificationRelationMapper;
import com.one.exercise.pojo.notification.NotificationRelation;
import com.one.exercise.service.NotificationRelationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NotificationRelationServiceImpl implements NotificationRelationService {

    @Resource
    private NotificationRelationMapper notificationRelationMapper;

    @Resource
    private NotificationMapper notificationMapper;

    @Override
    public int relationList(List<NotificationRelation> notificationRelationList) {
        int i = notificationRelationMapper.insertList(notificationRelationList);
        return i;
    }

    @Override
    public List<NotificationRelation> queryNotifyListByStudentId(Long studentId) {
        NotificationRelation notificationRelation = new NotificationRelation();
        notificationRelation.setReceiver(studentId);

        List<NotificationRelation> result = notificationRelationMapper.select(notificationRelation);
        return result;
    }

    @Override
    @Transactional
    public NotificationRelation readNotify(Long relationId, Long studentId) {
        NotificationRelation notificationRelation = new NotificationRelation();
        notificationRelation.setRelationId(relationId);
        notificationRelation.setReceiver(studentId);

        NotificationRelation result = notificationRelationMapper.selectOne(notificationRelation);

        // 未读，通知已读数量加1
        if (result.getIsRead() == 0 || result.getIsRead() == null){
            int i2 = notificationMapper.updateReads(result.getMessageId());

            notificationRelation.setIsRead(1);

            int i = notificationRelationMapper.updateByPrimaryKeySelective(notificationRelation);

            result.setIsRead(1);
        }

        return result;
    }

    @Override
    @Transactional
    public int deleteNotify(long studentId, Long relationId) {
        try {
            return notificationRelationMapper.deleteNotify(studentId, relationId);
        }catch (Exception e){
            return 0;
        }
    }
}
