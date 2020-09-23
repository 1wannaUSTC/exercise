package com.one.exercise.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;


@Setter
@Getter
@ToString
/**
 * 接收客户端传递的参数
 */
public class StudentBO {

    /** id-不可更改 */
    private Long studentId;

    /** 微信开放id-不可更改 */
    private String openId;

    /** 昵称- */
    private String nickName;

    /** 性别- */
    private Integer gender;

    /** 城市 */
    private String city;

    /** 省 */
    private String province;

    /** 头像 */
    private String avatar;

    /** 专业 */
    private String major;

    private Date createDate;

}