package com.one.exercise.mapper;

import com.one.exercise.mapper.custom.TeacherCustom;
import com.one.exercise.pojo.Teacher;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TeacherMapper extends Mapper<Teacher> {

    @Select("SELECT * FROM `exercise`.`teacher`  WHERE validity=1 and is_admin=0  LIMIT #{startIndex},#{pageSize}")
    List<Teacher> selectTeacherList(int startIndex, int pageSize);

    @Select("SELECT count(`teacher_id`) FROM `teacher` WHERE is_admin=0 AND validity=1")
    int selectCountTeacher();

    /** 获取教师列表：分页筛选: 按照学科查找 */
    List<Teacher> selectSubjectTeacherList(Integer subjectId, int startIndex, int pageSize);
    /** 获得指定学科的老师数量 */
    int countSubjectTeacherList(Integer subjectId);

    /** 老师的关注数量加减 */
    int updateTeacherFollow(Integer teacherId, Integer quantity);

    /** 根据教师idList获取教师列表 */
    @SelectProvider(type = TeacherCustom.class, method = "selectTeacherByIdList")
    List<Teacher> selectTeacherByIdList(@Param("teacherIdList") List<Integer> teacherIdList);
}