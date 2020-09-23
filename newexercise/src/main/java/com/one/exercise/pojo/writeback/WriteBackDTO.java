package com.one.exercise.pojo.writeback;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@ToString
public class WriteBackDTO {

    /**
     * 回复id
     */
    private Long backId;

    /**
     * 发起回复人
     */
    private Long backSender;

    private String senderNickname;

    private String senderAvatar;

    /**
     * 收到回复人
     */
    private Long backReceiver;

    private String receiverNickname;

    private String receiverAvatar;

    /**
     * 回复内容
     */
    private String message;

    /**
     * 时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date time;

}
