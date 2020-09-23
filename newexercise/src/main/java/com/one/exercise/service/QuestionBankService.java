package com.one.exercise.service;

import com.one.exercise.exception.InsertException;
import com.one.exercise.exception.UpdateMoreException;
import com.one.exercise.pojo.HomeQuestionNumber;
import com.one.exercise.pojo.QuestionBank;
import com.one.exercise.pojo.Work;
import com.one.exercise.pojo.question.QuestionDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface QuestionBankService {

    /** 保存一道问题 */
    QuestionBank saveQuestion(QuestionBank questionBank) throws InsertException;

    /** 删除一道题目-根据题目id */
    int deleteQuestionById(Integer questionId);
    /** 逻辑删除一道题目-根据题目id */
    int logicDeleteQuestionById(Integer questionId);

    /** 修改Question-根据题目id */
    int updateQuestionById(QuestionBank questionBank);

    /** 查询一个Question-根据题目ID */
    QuestionBank selectQuestionById(Integer questionId);

    /** 查询符合条件的question */
    int getCountByQuestion(QuestionBank questionBank);

    /** 分页获取题库 */
    @Deprecated
    List<QuestionBank> getQuestionSubPage(int startIndex, int pageSize, int isPublic);

    /** 根据对象获取一组questList,分页,筛选功能 */
    List<QuestionBank> getList(int startIndex, int pageSize, QuestionBank questionBank, String errorRate);

    /** 获取个人和公共题库题目总数量: 1 公共, 0 私有, -1 所有 */
    int getQuestionCount(int isPublic);

    /**
     * 获取首页的四个数字
     * 我的题库数量-public0
     * 公共题库数量-public1
     * 未发布作业数量-state0
     * 发布作业数量-state1
     * @return
     */
    HomeQuestionNumber getHomeNumber(Long teacherId);

    /** 随机一题 */
    QuestionBank randomQuestionOne(Long studentId);

    /** 获取问题列表 **/
    List<QuestionBank> selectQuestionByIdList(Set<Long> ids);

    List<QuestionBank> insertQuestionBankList(Integer teacherId, List<QuestionBank> list);

    List<QuestionBank> selectLikeKey(String searchKey, long id, int startIndex, int pageSize);
    int selectLikeKeyCount(String searchKey, long id);

    List<QuestionBank> selectLikeKey(String searchKey, int startIndex, int pageSize);
    int selectLikeKeyCount(String searchKey);

    /**
     * 批量更新问题
     */
    boolean updateMoreQuestion(List<QuestionBank> questionBanks) throws UpdateMoreException;

    /**
     * 从批量问题id中获取指定老师的问题
     * @return
     */
    List<QuestionBank> selectQuestionByIdByTeacherIdList(Set<Long> ids, Integer teacherId);

    /** 是否收藏 */
    boolean isCollect(Long questionId, Long studentId);
    /** 收藏 */
    boolean collectQuestion(Long questionId, Long studentId);
    /** 取消收藏 */
    boolean cancelCollectQuestion(Long workId, Long studentId);

    List<QuestionBank> selectCollectQuestion(Long studentId, Integer startIndex, Integer pageSize);
    int countCollectQuestion(long studentId);
}
