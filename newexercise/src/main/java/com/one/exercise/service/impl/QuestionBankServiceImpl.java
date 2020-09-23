package com.one.exercise.service.impl;

import com.one.exercise.exception.InsertException;
import com.one.exercise.exception.UpdateMoreException;
import com.one.exercise.mapper.QuestionBankMapper;
import com.one.exercise.mapper.StudentCollectQuestionMapper;
import com.one.exercise.pojo.*;
import com.one.exercise.service.QuestionBankService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Service
public class QuestionBankServiceImpl implements QuestionBankService {

    @Resource
    private QuestionBankMapper questionBankMapper;

    @Resource
    private StudentCollectQuestionMapper studentCollectQuestionMapper;

    @Override
    public QuestionBank saveQuestion(QuestionBank questionBank) throws InsertException {
        try {
            int i = questionBankMapper.insertSelective(questionBank);
            if (i>0){
                return questionBank;
            }
            return null;
        }catch (Exception e){
            throw new InsertException("问题已经存在");
        }

    }

    @Override
    public int deleteQuestionById(Integer questionId) {
        int i = questionBankMapper.deleteByPrimaryKey(questionId);
        return i;
    }

    @Override
    public int logicDeleteQuestionById(Integer questionId) {
        QuestionBank questionBank = new QuestionBank();
        questionBank.setQuestionId(questionId);
        questionBank.setValidity(0);
        int i = questionBankMapper.updateByPrimaryKeySelective(questionBank);
        return i;
    }

    @Override
    public int updateQuestionById(QuestionBank questionBank) {
        int i = questionBankMapper.updateByPrimaryKeySelective(questionBank);
        return i;
    }

    @Override
    public QuestionBank selectQuestionById(Integer questionId) {
        QuestionBank questionBank = questionBankMapper.selectByPrimaryKey(questionId);
        return questionBank;
    }

    @Override
    public List<QuestionBank> getQuestionSubPage(int startIndex, int pageSize, int isPublic) {
        List<QuestionBank> questionSubPage = questionBankMapper.getQuestionSubPage(startIndex, pageSize, isPublic);
        return questionSubPage;
    }

    @Override
    public List<QuestionBank> getList(int startIndex, int pageSize, QuestionBank questionBank, String errorRate) {
        List<QuestionBank> questionBanks = questionBankMapper.selectQuestionList(startIndex, pageSize, questionBank, errorRate);
        return questionBanks;
    }

    @Override
    public int getQuestionCount(int isPublic) {
        QuestionBank questionBank = new QuestionBank();
        questionBank.setIsPublic(isPublic);
        int i = questionBankMapper.selectCount(questionBank);
        return i;
    }

    @Override
    public HomeQuestionNumber getHomeNumber(Long teacherId) {
        HomeQuestionNumber homeNumber = questionBankMapper.getHomeNumber(teacherId);
        return homeNumber;
    }

    @Override
    public QuestionBank randomQuestionOne(Long studentId) {
        QuestionBank questionBank = questionBankMapper.randomQuestionOne(studentId);
        return questionBank;
    }

    @Override
    public List<QuestionBank> selectQuestionByIdList(Set<Long> ids) {
        return questionBankMapper.selectQuestionByIdList(ids);
    }

    @Override
    public int getCountByQuestion(QuestionBank questionBank) {
        int i = questionBankMapper.selectCount(questionBank);
        return i;
    }

    @Override
    public List<QuestionBank> insertQuestionBankList(Integer teacherId, List<QuestionBank> list) {

        list.forEach(item->{
            item.setTeacherId(teacherId);
            item.setIsPublic(item.getIsPublic() == null ? 0 :  item.getIsPublic());
            item.setSubjectId(item.getSubjectId() == null ? 0 :  item.getSubjectId());
            item.setScore(item.getScore() == null ? 5 :  item.getScore());
            item.setDifficulty(item.getDifficulty() == null ? "一般" :  item.getDifficulty());
            item.setValidity(item.getValidity() == null ? 1 :  item.getValidity());
        });
        // 添加
        questionBankMapper.insertList(list);
        // 查询
        Set set = new TreeSet();
        for (QuestionBank questionBank : list) {
            long questionId = questionBank.getQuestionId();
            set.add(questionId);
        }
        List<QuestionBank> resultList = questionBankMapper.selectQuestionByIdList(set);



        return resultList;
    }

    @Override
    public List<QuestionBank> selectLikeKey(String searchKey, long id, int startIndex, int pageSize) {
        return questionBankMapper.selectLikeKey(searchKey, id, startIndex, pageSize);
    }
    @Override
    public List<QuestionBank> selectLikeKey(String searchKey, int startIndex, int pageSize) {
        return questionBankMapper.selectLikeKey2(searchKey, startIndex, pageSize);
    }


    @Override
    public int selectLikeKeyCount(String searchKey, long id) {
        return questionBankMapper.selectLikeKeyCount(searchKey, id);
    }
    @Override
    public int selectLikeKeyCount(String searchKey) {
        return questionBankMapper.selectLikeKeyCount2(searchKey);
    }


    @Override
    public boolean updateMoreQuestion(List<QuestionBank> questionBanks) throws UpdateMoreException {
        try {
            questionBankMapper.updateMoreQuestion(questionBanks);
        }catch (Exception e){
            throw new UpdateMoreException(e.getMessage());
        }
        return true;
    }

    @Override
    public List<QuestionBank> selectQuestionByIdByTeacherIdList(Set<Long> ids, Integer teacherId) {
        return questionBankMapper.selectQuestionByIdByTeacherIdList(ids, teacherId);
    }

    @Override
    public boolean isCollect(Long questionId, Long studentId) {
        StudentCollectQuestion studentCollectQuestion = new StudentCollectQuestion();
        studentCollectQuestion.setQuestionId(questionId);
        studentCollectQuestion.setStudentId(studentId);
        StudentCollectQuestion result = studentCollectQuestionMapper.selectOne(studentCollectQuestion);
        return result != null;
    }

    @Override
    public boolean collectQuestion(Long questionId, Long studentId) {
        StudentCollectQuestion studentCollectQuestion = new StudentCollectQuestion();
        studentCollectQuestion.setQuestionId(questionId);
        studentCollectQuestion.setStudentId(studentId);
        try {
            int i = studentCollectQuestionMapper.insertSelective(studentCollectQuestion);
            return i > 0;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean cancelCollectQuestion(Long workId, Long studentId) {

        try {
            int i = studentCollectQuestionMapper.cancelCollectQuestion( workId, studentId);
            return i>0;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public List<QuestionBank> selectCollectQuestion(Long studentId, Integer startIndex, Integer pageSize) {
        List<QuestionBank> questionBanks = questionBankMapper.selectCollectQuestion(studentId, startIndex, pageSize);
        System.err.println("##############################");
        System.err.println(questionBanks);
        System.err.println("##############################");
        return questionBanks;
    }
    @Override
    public int countCollectQuestion(long studentId) {
        StudentCollectQuestion studentCollectQuestion = new StudentCollectQuestion();
        studentCollectQuestion.setStudentId(studentId);
        int i = studentCollectQuestionMapper.selectCount(studentCollectQuestion);
        return i;
    }


}
