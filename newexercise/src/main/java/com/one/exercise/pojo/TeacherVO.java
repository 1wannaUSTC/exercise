package com.one.exercise.pojo;

import lombok.Data;

/**
 * 返回到前端
 */
@Data
public class TeacherVO {

    /** 教师id，使用了自动递增策略 */
    private Integer teacherId;

    /** 用户登录账号 */
    private String number;

    /** 邮箱号 */
    private String email;

    /** 手机号 */
    private String phone;

    /** 姓名 */
    private String name;

    /** 性别；0-保密，1-男，2女 */
    private Integer gender;

    /** 所教授的课程：C语言,java语言 */
    private String course;

    /** 关注老师的学生数量 */
    private Integer follow;

    /** 教师认证：0未认证，1认证 */
    private Integer qualification;

    /** 用户头像 */
    private String iFace;

    /** 用户verifyCode */
    private String verifyCode;

    /** 是否为管理员 */
    private Integer isAdmin;

}