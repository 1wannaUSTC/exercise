package com.one.exercise.pojo.question;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class QuestionDTO {

    private Integer questionId;

    private Integer teacherId;

    /**
     * 是否为公共试题：1是，0不是
     */
    private Integer isPublic;

    /**
     * 专业
     */
    private String major;

    /**
     * 学科
     */
    private String subject;

    /**
     * 习题类型：选择(单选、多选)、填空、判断、简答
     */
    private String type;

    /**
     * 问题
     */
    private String question;

    /**
     * A、xxx&uuid&B、xxx&uuid&C、xxx&uuid&D、xxx
     */
    private String option;

    private String[] options;

    private List<Map<String,Object>> optionList;

    public void flushOptionList(){
        if (options==null || options.length == 0){
            return;
        }
        optionList = new ArrayList();
        for (String s : this.options) {
            Map map = new HashMap();
            map.put("option", s);
            map.put("isOk", 0);
            optionList.add(map);
        }
    }

    /**
     * 用于存放答案：多选(a,b,c)
     */
    private String answer;

    /**
     * 用于分隔
     */
    private String uuidShort;

    /**
     * 分值
     */
    private Integer score;

    /** 难度 */
    private String difficulty;

}
