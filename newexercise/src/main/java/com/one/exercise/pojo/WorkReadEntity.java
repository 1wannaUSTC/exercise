package com.one.exercise.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Setter
@Getter
@ToString
public class WorkReadEntity {

    private Long workId;

    /**
     * Map<问题id, 问题答案>
     */
    private Map<Long,String> answer;

}
