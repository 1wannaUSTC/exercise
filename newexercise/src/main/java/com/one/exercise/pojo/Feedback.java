package com.one.exercise.pojo;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "feedback")
public class Feedback {
    /**
     * 反馈表主键id
     */
    @Id
    @Column(name = "feedback_id")
    private Long feedbackId;

    @Column(name = "teacher_id")
    private Long teacherId;

    /**
     * 反馈内容
     */
    private String content;

    /**
     * 联系方式
     */
    private String relation;

    /**
     * 数据有效性，0无效，1有效，2灰度
     */
    private Integer validity;

    /**
     * 对应图片
     */
    @Transient
    private List<FeedbackPicture> feedbackPictures;

}