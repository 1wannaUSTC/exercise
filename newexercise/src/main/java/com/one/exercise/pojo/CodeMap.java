package com.one.exercise.pojo;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "code_map")
public class CodeMap {

    @Id
    @Column(name = "code_map_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codeMapId;

    private String email;

    private String code;

}
