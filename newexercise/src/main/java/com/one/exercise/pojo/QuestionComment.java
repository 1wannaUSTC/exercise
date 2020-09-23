package com.one.exercise.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import javax.persistence.*;

@Setter
@Getter
@ToString
@Table(name = "question_comment")
public class QuestionComment {
    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(name = "question_id")
    private Long questionId;

    private String message;

    /**
     * 发起人
     */
    private Long initiator;

    @Column(name = "this_time")
    private Date thisTime;

    private String face;

    @Column(name = "nick_name")
    private String nickName;
}