package com.one.exercise.utils;

import com.one.exercise.pojo.QuestionBank;
import com.one.exercise.pojo.question.QuestionDTO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class Tools {

    /**
     * QuestionBank 转 QuestionDTO
     */
    public static List<QuestionDTO> questionDAOListTransformDTO(List<QuestionBank> questionList) {
        List<QuestionDTO> list = new ArrayList<>();
        if (questionList != null) {
            questionList.forEach(item -> {
                QuestionDTO questionDTO = new QuestionDTO();
                BeanUtils.copyProperties(item, questionDTO);
                questionDTO.setOptions(Tools.splitStr(item.getOption(), item.getUuidShort()));
                questionDTO.flushOptionList();
                list.add(questionDTO);
            });
        }
        return list;
    }

    public static QuestionDTO questionDAOTransformDTO(QuestionBank question) {

        if (question != null) {

            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setOptions(Tools.splitStr(question.getOption(), question.getUuidShort()));
            return questionDTO;

        }
        return null;
    }

    /**
     * 特殊的字符串分割工具
     * 无法被分割返回null
     */
    public static String[] splitStr(String str, String item) {
        if (!Tools.isNullAllStr(str, item)) {
            String[] split = str.trim().split(item);

            List<String> als = new ArrayList<>();
            for (String s : split) {
                if (s != null && s.trim().length() > 0) {
                    als.add(s.trim());
                }
            }
            String[] sItem = new String[als.size()];
            als.toArray(sItem);
            return sItem;
        } else {
            return null;
        }
    }

    /**
     * 判断字符串是否为空
     *
     * @param s
     * @return true为空，false不为空
     */
    public static boolean isNullStr(String s) {
        try {
            if (s == null || "".equals(s.trim()) || s.trim().length() == 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 判断多个字符串是否为null
     * true 为空、false 不为空
     */
    public static Boolean isNullAllStr(String... strings) {
        try {
            if (strings == null || strings.length == 0) {
                return true;
            }
            for (String s : strings) {
                if (s == null || s.trim().length() == 0) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 比较两个字符串的相似度
     * @param standard 参数，标准字符串
     * @param input 输入
     * @return
     */
    public static double similarityString(String standard, String input){

        // 标准
        String s1 = standard;

        // 待比较
        String s2 = input;

        String item = s2 + "";
        int matching = 0;
        for(int i = 0; i < s1.length(); i++){
            char c1 = s1.charAt(i);
            for (int j = 0; j < item.length(); j++) {
                char c2 = item.charAt(j);

                if (c1 == c2 || c1 == c2 + 32 || c1 == c2 -32){
                    matching++;
                    item = item.substring(0, j) + item.substring(j+1);
                    break;
                }

            }
        }

        return (double)matching / s1.length();

    }

}
