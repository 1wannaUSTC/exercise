package com.one.exercise.mapper;

import com.one.exercise.pojo.notification.NotificationRelation;
import org.apache.ibatis.annotations.Delete;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface NotificationRelationMapper extends Mapper<NotificationRelation>, MySqlMapper<NotificationRelation> {

    @Delete("DELETE FROM `exercise`.`notification_relation` WHERE `relation_id` = #{relationId} AND receiver = #{studentId}")
    int deleteNotify(long studentId, Long relationId);
}