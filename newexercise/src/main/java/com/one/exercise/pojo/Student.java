package com.one.exercise.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Table(name = "student")
@Setter
@Getter
@ToString
public class Student {
    /** 学生id */
    @Id
    @Column(name = "student_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    /** 微信开放id */
    @Column(name = "open_id")
    private String openId;

    /** 昵称 */
    @Column(name = "nick_name")
    private String nickName;

    /** 性别 */
    private Integer gender;

    /** 城市 */
    private String city;

    /** 省 */
    private String province;

    /** 头像 */
    private String avatar;

    /** 专业 */
    private String major;

    /** 有效数据: 0无效、1有效、2灰色数据 */
    private Integer validity;

    private String email;
    private String password;

    @Column(name = "create_date")
    private Date createDate;
}