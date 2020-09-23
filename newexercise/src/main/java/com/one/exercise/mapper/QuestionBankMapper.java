package com.one.exercise.mapper;

import com.one.exercise.mapper.custom.QuestionBankCustom;
import com.one.exercise.pojo.HomeQuestionNumber;
import com.one.exercise.pojo.QuestionBank;
import com.one.exercise.pojo.Work;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;
import java.util.Set;

public interface QuestionBankMapper extends Mapper<QuestionBank>, MySqlMapper<QuestionBank> {

    @Select("select * from question_bank where is_Public=#{isPublic} ORDER BY question_id DESC limit #{startIndex},#{pageSize} ")
    List<QuestionBank> getQuestionSubPage(int startIndex, int pageSize, int isPublic);

    /**
     * 对题库进行筛选查询
     * @param startIndex
     * @param pageSize
     * @param questionBank
     * @return
     */
    @SelectProvider(type = QuestionBankCustom.class, method = "getQuestionList")
    List<QuestionBank> selectQuestionList(
            @Param("startIndex") int startIndex,
            @Param("pageSize") int pageSize,
            @Param("questionBank") QuestionBank questionBank,
            @Param("errorRate") String errorRate);

    /**
     * 获取首页的四个数字
     * 我的题库数量-public0
     * 公共题库数量-public1
     * 未发布作业数量-state0
     * 发布作业数量-state1
     * @return
     */
    @Select({"SELECT a.yes as public1 ,b.not as public0,c.yes as state1,d.not as state0 FROM \n" +
            "\t(SELECT COUNT(*) as `yes` FROM question_bank WHERE is_public=1  AND teacher_id=#{teacherId}) as a,\n" +
            "\t(SELECT COUNT(*) as `not` FROM question_bank WHERE is_public=0 AND teacher_id=#{teacherId}) as b,\n" +
            "\t(SELECT COUNT(*) as `yes` FROM `work` WHERE state=1 AND teacher_id=#{teacherId}) as c,\n" +
            "\t(SELECT COUNT(*) as `not` FROM `work` WHERE state=0 AND teacher_id=#{teacherId}) as d"
            })
    @ResultType(HomeQuestionNumber.class)
    HomeQuestionNumber getHomeNumber(Long teacherId);

    /** 通过studentid获取随机一题，随机一题 */
    @Select("SELECT qb.* FROM (\n" +
            "\tSELECT * FROM `question_bank` WHERE `subject` IN (\n" +
            "\t\tSELECT subject_name FROM `exercise`.`subject` WHERE subject_id in (SELECT subject_id FROM `exercise`.`student_subject` WHERE student_id=#{studentId})\n" +
            "\t)   ORDER BY question_id\n" +
            "\t\n" +
            ") as qb \n" +
            "WHERE qb.question_id >= (\n" +
            "\tSELECT floor(\n" +
            "\t\tRAND() * (\n" +
            "\t\t\tSELECT MAX(question_id) FROM `question_bank` WHERE `subject` IN (\n" +
            "\t\t\t\tSELECT subject_name FROM `exercise`.`subject` WHERE subject_id in (SELECT subject_id FROM `exercise`.`student_subject` WHERE student_id=#{studentId})\n" +
            "\t\t\t)   ORDER BY question_id\n" +
            "\t\t)\n" +
            "\t)\n" +
            ") \n" +
            "ORDER BY qb.question_id LIMIT 1;")
    QuestionBank randomQuestionOne(Long studentId);

    @SelectProvider(type = QuestionBankCustom.class, method = "selectQuestionByIdList")
    List<QuestionBank> selectQuestionByIdList(@Param("ids") Set<Long> ids);

    @SelectProvider(type = QuestionBankCustom.class, method = "selectLikeKey")
    List<QuestionBank> selectLikeKey(
            @Param("searchKey") String searchKey,
            @Param("id") long id, int startIndex, int pageSize);

    @SelectProvider(type = QuestionBankCustom.class, method = "selectLikeKey2")
    List<QuestionBank> selectLikeKey2(@Param("searchKey") String searchKey, int startIndex, int pageSize);

    @SelectProvider(type = QuestionBankCustom.class, method = "selectLikeKeyCount")
    int selectLikeKeyCount(
            @Param("searchKey") String searchKey,
            @Param("id") long id);
    @SelectProvider(type = QuestionBankCustom.class, method = "selectLikeKeyCount2")
    int selectLikeKeyCount2(@Param("searchKey") String searchKey);

    @SelectProvider(type = QuestionBankCustom.class, method = "updateMoreQuestion")
    void updateMoreQuestion(@Param("questions") List<QuestionBank> questionBanks);

    // 从批量问题id中获取指定老师的问题-SELECT * FROM question_bank WHERE teacher_id = 15 AND question_id in (72, 73, 74)
    @SelectProvider(type = QuestionBankCustom.class, method = "selectQuestionByIdByTeacherIdList")
    List<QuestionBank> selectQuestionByIdByTeacherIdList(@Param("ids") Set<Long> ids, @Param("teacherId") Integer teacherId);

    // 学生：获取收藏的问题
    @Select("SELECT qb.* FROM `exercise`.`question_bank` qb, `exercise`.`student_collect_question` scq " +
            "WHERE qb.question_id = scq.question_id AND student_id = #{studentId} " +
            "ORDER BY  scq.collect_id DESC " +
            "LIMIT #{startIndex},#{pageSize}")
    List<QuestionBank> selectCollectQuestion(Long studentId, Integer startIndex, Integer pageSize);
}