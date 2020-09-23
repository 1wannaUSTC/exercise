package com.one.exercise.service;

import com.one.exercise.ExerciseApplicationTests;
import com.one.exercise.exception.UpdateMoreException;
import com.one.exercise.pojo.QuestionBank;
import com.one.exercise.pojo.question.QuestionDTO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class QuestionBankTest extends ExerciseApplicationTests {

    @Autowired
    QuestionBankService questionBankService;

    @Test
    public void getCountByQuestion(){
        QuestionBank questionBank = new QuestionBank();
        questionBank.setIsPublic(1);
        int countByQuestion = questionBankService.getCountByQuestion(questionBank);
        System.out.println(countByQuestion);
    }

    @Test
    public void getQuestionSubPage(){
        List<QuestionBank> questionSubPage = questionBankService.getQuestionSubPage(1, 10, 1);
    }


    @Test
    public void insertQuestionBankList(){
        List<QuestionBank> list = new ArrayList<>();
        QuestionBank questionBank1 = new QuestionBank();
        questionBank1.setTeacherId(15);
        questionBank1.setIsPublic(1);
        questionBank1.setSubjectId(1);
        questionBank1.setType("一般");
        questionBank1.setQuestion("???????");
        questionBank1.setAnswer("123123123");
        questionBank1.setScore(5);
        QuestionBank questionBank2 = new QuestionBank();
        questionBank2.setTeacherId(15);
        questionBank2.setQuestion("????2???");
        questionBank2.setAnswer("12312312322222");
        questionBank2.setIsPublic(1);
        questionBank2.setSubjectId(1);
        questionBank2.setType("一般");
        questionBank2.setScore(5);

        list.add(questionBank1);
        list.add(questionBank2);
        List<QuestionBank> list1 = questionBankService.insertQuestionBankList(15, list);
        System.out.println(list1);
    }

    @Test
    public void updateMoreQuestion(){
        List<QuestionBank> questions = new ArrayList<>();
        QuestionBank question = new QuestionBank();
        question.setQuestionId(1);
        question.setTeacherId(20);
        question.setQuestion("1111111111111111110001");
        question.setAnswer("Hello ooooo");

        QuestionBank question2 = new QuestionBank();
        question2.setQuestionId(2);
        question2.setTeacherId(20);
        question2.setQuestion("22222222222222220002");
        question2.setAnswer("Hello 222222");

        questions.add(question);
        questions.add(question2);


        try {
            boolean b = questionBankService.updateMoreQuestion(questions);
            System.out.println(b);
        } catch (UpdateMoreException e) {
            e.printStackTrace();
        }

    }

}
