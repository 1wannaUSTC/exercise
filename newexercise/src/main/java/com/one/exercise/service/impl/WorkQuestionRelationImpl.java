package com.one.exercise.service.impl;

import com.one.exercise.mapper.WorkQuestionRelationMapper;
import com.one.exercise.pojo.WorkQ;
import com.one.exercise.pojo.WorkQuestionRelation;
import com.one.exercise.service.WorkQuestionRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkQuestionRelationImpl implements WorkQuestionRelationService {

    @Autowired
    private WorkQuestionRelationMapper workQuestionRelationMapper;


    @Override
    public int insertQuestionToWork(int workId, int questionId) {
        int i = workQuestionRelationMapper.insertQuestionToWork(workId, questionId);
        return i;
    }

    @Override
    @Transactional
    public int insertQuestionToWork(Integer workId, List<Integer> questionIds) {
        List<WorkQuestionRelation> list = new ArrayList();
        questionIds.forEach(item->{
            WorkQuestionRelation workQuestionRelation = new WorkQuestionRelation();
            workQuestionRelation.setWorkId(workId);
            workQuestionRelation.setQuestionId(item);
            list.add(workQuestionRelation);
        });
        int i = workQuestionRelationMapper.insertList(list);
        return i;
    }

    @Override
    public int deleteQuestionToWork(int workId, int questionId) {

        WorkQuestionRelation workQuestionRelation = new WorkQuestionRelation();
        workQuestionRelation.setWorkId(workId);
        workQuestionRelation.setQuestionId(questionId);

        int i = workQuestionRelationMapper.delete(workQuestionRelation);

        return i;

    }

    @Override
    public int deleteQuestionToWork(int workId) {
        WorkQuestionRelation workQuestionRelation = new WorkQuestionRelation();
        workQuestionRelation.setWorkId(workId);

        int i = workQuestionRelationMapper.delete(workQuestionRelation);

        return i;
    }

    @Override
    public WorkQ selectWorkContent(Integer teacherId, Integer workId) {
        // TODO
        WorkQ questionBankList = workQuestionRelationMapper.selectWorkContent(teacherId, workId);
        return questionBankList;
    }
}
