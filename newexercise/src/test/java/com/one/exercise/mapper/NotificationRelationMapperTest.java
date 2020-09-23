package com.one.exercise.mapper;

import com.one.exercise.ExerciseApplicationTests;
import com.one.exercise.pojo.notification.NotificationRelation;
import com.one.exercise.service.NotificationRelationService;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotificationRelationMapperTest extends ExerciseApplicationTests {

    @Resource
    private NotificationRelationMapper notificationRelationMapper;

    @Resource
    NotificationRelationService notificationRelationService;


    @Test
    public void relationList(){

        List<Long> idList = new ArrayList<>();
        idList.add((long) 5);
        idList.add((long) 6);
        idList.add((long) 7);

        List<NotificationRelation> relations = new ArrayList<>();
        idList.forEach(item->{
            NotificationRelation notificationRelation = new NotificationRelation();
            notificationRelation.setSender((long) 20);
            notificationRelation.setReceiver(item);
            notificationRelation.setReadTime(null);
            notificationRelation.setIsRead(0);
            relations.add(notificationRelation);
        });

        int i = notificationRelationMapper.insertList(relations);
        System.out.println(i);
    }

    @Test
    public void queryNotifyListByStudentId(){
        List<NotificationRelation> relations = notificationRelationService.queryNotifyListByStudentId((long) 13);

        System.out.println(relations);
    }

    @Test
    public void readNotify(){
        NotificationRelation notificationRelation = notificationRelationService.readNotify((long) 9, (long) 18);
        System.out.println(notificationRelation);
    }

}
