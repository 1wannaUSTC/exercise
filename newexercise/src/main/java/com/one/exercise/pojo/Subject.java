package com.one.exercise.pojo;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "subject")
public class Subject {

    @Id
    @Column(name = "subject_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer subjectId;

    @Column(name = "subject_name")
    private String subjectName;

    private String icon;

}
