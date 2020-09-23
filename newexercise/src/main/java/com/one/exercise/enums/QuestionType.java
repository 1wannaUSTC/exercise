package com.one.exercise.enums;

public enum QuestionType {

    SINGLECHOICE(1,"单选"),MULTIPLECHOICE(2,"多选"),SHORTANSWER(3, "简答");

    public int index;
    public String type;

    QuestionType(int index, String type) {
        this.index = index;
        this.type = type;
    }

    public static String queryType(int index){
        QuestionType[] values = QuestionType.values();
        for (QuestionType value : values) {
            int i = value.index;
            if (i == index){
                return value.type;
            }
        }
        return null;
    }

    public static int queryIndex(String type){
        QuestionType[] values = QuestionType.values();
        for (QuestionType value : values) {
            if (value.type.equalsIgnoreCase(type)){
                return value.index;
            }
        }
        return 0;
    }

}
