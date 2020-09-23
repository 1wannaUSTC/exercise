package com.one.exercise.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Setter
@Getter
@ToString
@Table(name = "dynamic_picture")
public class DynamicPicture {
    @Id
    @Column(name = "dynamic_picture_id")
    private Long dynamicPictureId;

    @Column(name = "dy_id")
    private Long dyId;

    @Column(name = "image_path")
    private String imagePath;
}