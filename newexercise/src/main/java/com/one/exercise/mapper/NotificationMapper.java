package com.one.exercise.mapper;


import com.one.exercise.mapper.custom.NotificationCustom;
import com.one.exercise.pojo.notification.Notification;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface NotificationMapper extends Mapper<Notification> {

    @SelectProvider(type = NotificationCustom.class, method = "selectNotifyListByMessages")
    List<Notification> selectNotifyListByMessages(@Param("messageIdStr") String messageIdStr, Integer startIndex, Integer pageSize);

    @Update("UPDATE `exercise`.`notification` SET `reads` = `reads` + 1 WHERE `message_id` = #{messageId}")
    int updateReads(Long messageId);
}