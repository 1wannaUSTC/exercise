package com.one.exercise.mapper;

import com.one.exercise.pojo.QuestionBank;
import com.one.exercise.pojo.WorkQ;
import com.one.exercise.pojo.WorkQuestionRelation;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface WorkQuestionRelationMapper extends Mapper<WorkQuestionRelation>, MySqlMapper<WorkQuestionRelation> {

    @Select("SELECT q.* FROM `work` w, `question_bank` q, `work_question_relation` r WHERE q.question_id=r.question_id and w.work_id=r.work_id and r.work_id=#{workId}")
    List<QuestionBank> selectWorkQuestion(int workId);

    /** 查看作业内容 */
    @Select("SELECT * FROM `work` w WHERE w.work_id=#{workId} AND #{teacherId}")
    @Results({
            @Result(property = "workId",column = "work_id"),
            @Result(
                    property = "questionList",
                    javaType = List.class,
                    column ="work_id",
                    many = @Many(select = "com.one.exercise.mapper.WorkQuestionRelationMapper.selectWorkQuestion")
            )
    })
    WorkQ selectWorkContent(int teacherId, int workId);

    /**
     * 添加问题到作业
     * @param workId
     * @param questionId
     * @return
     */
    @Insert("INSERT IGNORE INTO work_question_relation (work_id, question_id) VALUES (#{workId}, #{questionId})")
    int insertQuestionToWork(int workId, int questionId);

}
