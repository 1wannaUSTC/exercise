package com.one.exercise.service.impl;

import com.one.exercise.mapper.StudentCollectWorkMapper;
import com.one.exercise.mapper.WorkMapper;
import com.one.exercise.pojo.QuestionBank;
import com.one.exercise.pojo.StudentCollectWork;
import com.one.exercise.pojo.Work;
import com.one.exercise.service.WorkQuestionRelationService;
import com.one.exercise.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class WorkServiceImpl implements WorkService {


    @Resource
    private WorkMapper workMapper;

    @Resource
    private WorkQuestionRelationService workQuestionRelationService;

    @Resource
    private StudentCollectWorkMapper studentCollectWorkMapper;

    @Override
    public int getWorkStateCount(int teacherId,int state) {
        Work work = new Work();
        work.setTeacherId(teacherId);
        work.setState(state);
        int i = workMapper.selectCount(work);
        return i;
    }

    @Override
    public List<Work> getWorkList(Work work, Integer startIndex, Integer pageSize) {
        List<Work> workList = workMapper.getWorkList(work, startIndex, pageSize);
        return workList;
    }

    @Override
    public int saveWork(Work work) {
        int i = workMapper.insertSelective(work);
        return i;
    }

    @Override
    @Transactional
    public int deleteWork(Integer workId) {

        /**
         * 1. 从work表中删除作业
         * 2. 从work_question_relation表中删除对应workid
         */
        if (workId == null ){
            return 0;
        }

        Work work = new Work();
        work.setWorkId(workId);

        int delete = workMapper.delete(work);
        workQuestionRelationService.deleteQuestionToWork(workId);

        return delete;
    }

    @Override
    public int updateWork(Work work) {
        int i = workMapper.updateByPrimaryKeySelective(work);
        return i;
    }

    @Override
    public List<QuestionBank> selectWorkQuestionById(Integer teacherId,Integer workId) {
        return workMapper.selectWorkContent(teacherId, workId);
    }

    @Override
    public Work getWorkByName(Integer teacherId, String workName) {
        Work work = new Work();
        work.setTeacherId(teacherId);
        work.setWorkName(workName);
        Work one = workMapper.selectOne(work);
        return one;
    }

    @Override
    public Work getWorkById(Integer workId) {
        Work work = workMapper.selectByPrimaryKey(workId);
        return work;
    }

    @Override
    public List<Work> queryWorkList(Work work, Integer startIndex, Integer pageSize) {
        List<Work> result = workMapper.queryWorkList(work, startIndex, pageSize);
        return result;
    }

    @Override
    public List<Work> selectVagueWork(String key, Integer startIndex, Integer pageSize) {
        String newKey = "%"+key+"%";
        List<Work> works = workMapper.selectVagueWork(newKey, startIndex, pageSize);
        return works;
    }

    @Override
    public int countVagueWork(String key) {
        int i = workMapper.countVagueWork("%" + key + "%");
        return i;
    }

    @Override
    public int countWork(Work work) {
        int i = workMapper.selectCount(work);
        return i;
    }

    @Override
    public boolean isCollect(Long workId, Long studentId) {
        StudentCollectWork studentCollectWork = new StudentCollectWork();
        studentCollectWork.setStudentId(studentId);
        studentCollectWork.setWorkId(workId);
        StudentCollectWork result = studentCollectWorkMapper.selectOne(studentCollectWork);
        return result != null;
    }

    @Override
    public boolean collectWork(Long workId, Long studentId) {
        StudentCollectWork studentCollectWork = new StudentCollectWork();
        studentCollectWork.setStudentId(studentId);
        studentCollectWork.setWorkId(workId);
        try {
            int i = studentCollectWorkMapper.insertSelective(studentCollectWork);
            return i>0;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    @Transactional
    public boolean cancelCollectWork(Long workId, Long studentId) {
        try {
            int i = studentCollectWorkMapper.cancelCollectWork( workId, studentId);
            return i>0;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public List<Work> selectCollectWork(long studentId, Integer startIndex, Integer pageSize) {
        List<Work> works = workMapper.selectCollectWork(studentId, startIndex, pageSize);
        return works;
    }

    @Override
    public int countCollectWork(long studentId) {
        StudentCollectWork studentCollectWork = new StudentCollectWork();
        studentCollectWork.setStudentId(studentId);
        int i = studentCollectWorkMapper.selectCount(studentCollectWork);
        return i;
    }

    @Override
    public List<Long> getWorkIdByRelationStudentId(long studentId) {
        List<Long> workIdByRelationStudentId = studentCollectWorkMapper.getWorkIdByRelationStudentId(studentId);
        return workIdByRelationStudentId;
    }
}
