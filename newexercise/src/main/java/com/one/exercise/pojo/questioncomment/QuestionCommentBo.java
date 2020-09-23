package com.one.exercise.pojo.questioncomment;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class QuestionCommentBo {

    private Long questionId;

    private String message;

}