package com.one.exercise.service;

import com.one.exercise.pojo.Teacher;

import java.util.List;
import java.util.Map;

public interface TeacherService {

    /** 增 */
    // 添加用户
    /** 向数据库保存一个teacher对象，null不会保存 */
    int saveTeacher(Teacher teacher);
    Teacher saveOneTeacher(Teacher teacher);

    /** 删 */
    /** 删除指定number的用户 */
    int delete(String number);
    /** 逻辑删除指定id的用户 */
    int logicDelete(Integer teacherId);

    /** 改 */
    /** 根据id更新,并返回teacher */
    Teacher updateTeacherById(Teacher teacher);
    /** 老师的关注数量加减 */
    int updateTeacherFollow(Integer teacherId, Integer quantity);

    /** 查 */
    /** 根据邮箱获取一个teacher */
    Teacher selectTeacherByEmail(String email);
    /** 根据邮箱查找teacher是否存在 */
    boolean hasTeacherByEmail(String email);
    /** 根据邮箱和密码找到一个teacher */
    Teacher selectTeacherByEmailAndPw(String email, String password);
    /** 根据id获取teacher */
    Teacher selectTeacherById(int teacherId);
    /** 获取教师列表 */
    Map<String, Object> selectTeacherList(int startPage, int pageSize);

    /** 获取教师列表 */
    List<Teacher> selectTeacherList(Integer subjectId, int startIndex, int pageSize);
    /** 获取指定学科的老师数量 */
    int countSubjectTeacherList(Integer subjectId);

    /** 根据教师idList获取教师列表 */
    List<Teacher> selectTeacherByIdList(List<Integer> teacherIdList);
}
