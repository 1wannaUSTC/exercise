package com.one.exercise.mapper.custom;

import org.apache.ibatis.annotations.Param;

public class NotificationCustom {

    public String selectNotifyListByMessages(@Param("messageIdStr") String messageIdStr){
        StringBuilder sb = new StringBuilder(" SELECT * FROM `exercise`.`notification` WHERE message_id in  ");
        sb.append(messageIdStr);
        sb.append(" ");
        sb.append(" LIMIT #{startIndex},#{pageSize} ");
        return sb.toString();
    }

}
