package com.one.exercise.pojo;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "feedback_picture")
public class FeedbackPicture {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 反馈id
     */
    @Column(name = "feedback_id")
    private Long feedbackId;

    /**
     * 图片路径
     */
    @Column(name = "picture_path")
    private String picturePath;

    public FeedbackPicture(Long feedbackId, String picturePath) {
        this.feedbackId = feedbackId;
        this.picturePath = picturePath;
    }
}