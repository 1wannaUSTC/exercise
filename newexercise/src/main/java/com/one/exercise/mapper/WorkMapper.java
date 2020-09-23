package com.one.exercise.mapper;

import com.one.exercise.mapper.custom.WorkCustom;
import com.one.exercise.pojo.QuestionBank;
import com.one.exercise.pojo.Work;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface WorkMapper extends Mapper<Work> {

    /** SELECT * FROM `work` WHERE major="软件工程" AND subject_name="Java语言基础" AND state=0 AND difficulty="简单" */
    @SelectProvider(type = WorkCustom.class, method = "getWorkList")
    List<Work> getWorkList(
            @Param("work") Work work,
            @Param("startIndex") Integer startIndex,
            @Param("pageSize") Integer pageSize
    );

    @Select("SELECT qb.* FROM work_question_relation AS wqr, `work` AS w, question_bank AS qb\n" +
            "WHERE wqr.work_id=w.work_id AND wqr.question_id=qb.question_id AND w.teacher_id=#{teacherId} AND w.work_id=#{workId}")
    List<QuestionBank> selectWorkContent(Integer teacherId,Integer workId);

    List<Work> queryWorkList(Work work, Integer startIndex, Integer pageSize);

    // 模糊查找作业
    @Select("SELECT * FROM `exercise`.`work` WHERE work_name LIKE #{key} LIMIT #{startIndex},#{pageSize}")
    List<Work> selectVagueWork(String key, Integer startIndex, Integer pageSize);

    // 模糊查找作业
    @Select("SELECT COUNT(*) FROM `exercise`.`work` WHERE work_name LIKE #{key}")
    int countVagueWork(String key);

    // 获取收藏的作业列表
    @Select("SELECT w.* FROM `exercise`.`work` w, `exercise`.`student_collect_work` scw " +
            "WHERE w.work_id = scw.work_id AND student_id = #{studentId} " +
            "ORDER BY scw.collect_id DESC " +
            "LIMIT #{startIndex},#{pageSize}")
    List<Work> selectCollectWork(long studentId, Integer startIndex, Integer pageSize);

}