package com.one.exercise.pojo;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "teacher")
public class Teacher {
    /** 教师id，使用了自动递增策略 */
    @Id
    @Column(name = "teacher_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer teacherId;

    /** 用户登录账号 */
    private String number;

    /** 邮箱号 */
    private String email;

    /** 手机号 */
    private String phone;

    /** 姓名 */
    private String name;

    /** 登录密码 */
    private String password;

    /** 性别；0-保密，1-男，2女 */
    private Integer gender;

    /** 所教授的课程：C语言,java语言 */
    private String course;

    /** 关注老师的学生数量 */
    private Integer follow;

    /** 教师认证：0未认证，1认证 */
    private Integer qualification;

    /** 用户头像 */
    @Column(name = "i_face")
    private String iFace;

    /** 是否为管理员 0不是 1是*/
    @Column(name = "is_admin")
    private Integer isAdmin;

    /** 是否为有效数据：0无效，1有效，2灰色数据: 默认为1 */
    private Integer validity;

}