package com.one.exercise.mapper.custom;

import com.one.exercise.ExerciseApplicationTests;
import com.one.exercise.pojo.QuestionBank;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class QuestionBankCustomTest extends ExerciseApplicationTests {

    QuestionBankCustom questionBankCustom = new QuestionBankCustom();

    @Test
    public void QuestionBankCustom(){
        Set<Long> set = new TreeSet();
        set.add((long) 1);
        set.add((long) 3);
        String s = questionBankCustom.selectQuestionByIdList(set);
        System.out.println(s);
    }

    @Test
    public void updateMoreQuestion(){
        List<QuestionBank> questions = new ArrayList<>();
        QuestionBank question = new QuestionBank();
        question.setQuestionId(1);
        question.setTeacherId(20);
        question.setQuestion("Hello0001");
        question.setAnswer("Hello ooooo");

        QuestionBank question2 = new QuestionBank();
        question2.setQuestionId(2);
        question2.setTeacherId(20);
        question2.setQuestion("Hello0002");
        question2.setAnswer("Hello 222222");

        questions.add(question);
        questions.add(question2);


        String s = questionBankCustom.updateMoreQuestion(questions);
        System.out.println(s);
    }

}
