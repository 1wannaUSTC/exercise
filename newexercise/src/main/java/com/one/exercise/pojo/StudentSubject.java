package com.one.exercise.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Table(name = "student_subject")
@Setter
@Getter
@ToString
public class StudentSubject {

    @Id
    @Column(name = "code_map_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "subject_id")
    private Long subjectId;

    @Column(name = "student_id")
    private Long studentId;
}