package com.one.exercise.service;

import com.one.exercise.pojo.WorkQ;

import java.util.List;

public interface WorkQuestionRelationService {

    /** 将问题添加到作业中 */
    int insertQuestionToWork(int workId, int questionId);

    /** 将多个问题添加到作业中 */
    int insertQuestionToWork(Integer workId, List<Integer> questionIds);

    /** 将问题从作业中删除 */
    int deleteQuestionToWork(int workId, int questionId);

    /** 删除作业 */
    int deleteQuestionToWork(int workId);

    /** 查看我的作业内容 */
    WorkQ selectWorkContent(Integer teacherId, Integer workId);

}
