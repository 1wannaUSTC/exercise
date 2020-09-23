package com.one.exercise.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@ToString
public class StudentVO {

    /**
     * 学生id
     */
    private Long studentId;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 城市
     */
    private String city;

    /**
     * 省
     */
    private String province;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 专业
     */
    private String major;

    /**
     * 邮箱
     */
    private String email;

    private Date createDate;

}