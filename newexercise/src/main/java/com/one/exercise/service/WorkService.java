package com.one.exercise.service;

import com.one.exercise.pojo.QuestionBank;
import com.one.exercise.pojo.StudentCollectWork;
import com.one.exercise.pojo.Work;

import java.util.List;

public interface WorkService {

    /** 根据老师id和作业状态 查询老师为发布作业或发布作业的数量 */
    int getWorkStateCount(int teacherId, int state);

    /** 根据老师id和作业状态 查询老师为发布作业或发布作业的 作业列表 */
    List<Work> getWorkList(Work work, Integer startIndex, Integer pageSize);

    /** 根据作业名称查找作业是否存在 */
    Work getWorkByName(Integer teacherId, String workName);

    /** 根据作业id查找作业 */
    Work getWorkById(Integer workId);

    /** 学生端：作业列表 */
    List<Work> queryWorkList(Work work, Integer startIndex, Integer pageSize);

    /** 模糊查找作业，查找作业名称 */
    List<Work> selectVagueWork(String key, Integer startIndex, Integer pageSize);
    int countVagueWork(String key);

    /** 根据状态获取作业数量 */
    int countWork(Work work);

    /** 添加作业 */
    int saveWork(Work work);

    /** 删除未发布的作业 */
    int deleteWork(Integer workId);

    /** 修改作业信息 */
    int updateWork(Work work);

    /** 获取作业内容 */
    List<QuestionBank> selectWorkQuestionById(Integer teacherId,Integer workId);

    /** 收藏状态 */
    boolean isCollect(Long workId, Long studentId);

    /** 学生：收藏作业 */
    boolean collectWork(Long workId, Long studentId);

    /** 取消收藏 */
    boolean cancelCollectWork(Long workId, Long studentId);

    /** 获取收藏的作业列表 */
    List<Work> selectCollectWork(long studentId, Integer startIndex, Integer pageSize);
    int countCollectWork(long studentId);

    /** 获取学生收藏的所有作业的id */
    List<Long> getWorkIdByRelationStudentId(long studentId);
}
