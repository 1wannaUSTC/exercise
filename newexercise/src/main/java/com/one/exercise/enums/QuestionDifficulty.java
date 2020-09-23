package com.one.exercise.enums;

public enum QuestionDifficulty {

    // easy、ordinary、difficulty
    EASY(1,"简单"),ORDINARY(2,"一般"),DIFFICULTY(3, "困难");

    private int index;
    private String type;

    QuestionDifficulty(int index, String type) {
        this.index = index;
        this.type = type;
    }

    public static String queryType(int index){
        QuestionDifficulty[] values = QuestionDifficulty.values();
        for (QuestionDifficulty value : values) {
            int i = value.index;
            if (i == index){
                return value.type;
            }
        }
        return null;
    }

    public static int queryIndex(String type){
        QuestionDifficulty[] values = QuestionDifficulty.values();
        for (QuestionDifficulty value : values) {
            if (value.type.equalsIgnoreCase(type)){
                return value.index;
            }
        }
        return 0;
    }

}
