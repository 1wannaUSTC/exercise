package com.one.exercise.enums;

/**
 * 性别枚举
 * 性别；0-保密Unknown，1-男，2女
 */
public enum Gender {
    BOY("男",1),GIRL("女",2),UNKNOWN("未知",0);

    private String sex;
    private int index;

    private Gender(String sex,int index){
        this.sex = sex;
        this.index = index;
    }

    public static String getSex(int index){
        Gender[] values = Gender.values();
        for (Gender value : values) {
            if (value.getIndex()==index) {
                return value.getSex();
            }
        }
        return null;
    }

    public static Integer getIndex(String sex){
        Gender[] values = Gender.values();
        for (Gender value : values) {
            if (value.getSex().equals(sex)) {
                return value.getIndex();
            }
        }
        return null;
    }

    public String getSex() {
        return sex;
    }


    public int getIndex() {
        return index;
    }


}
