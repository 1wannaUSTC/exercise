package com.one.exercise.enums;


/** 身份类型 */
public enum VerifyType {
    TEACHER("teacher",1),STUDENT("student",2),ADMIN("admin",3);;

    public String type;
    public int index;

    VerifyType(String type,int index){
        this.type = type;
        this.index = index;
    }

    public static int queryType(String type){
        VerifyType[] values = VerifyType.values();
        for (VerifyType value : values) {
            if (value.type.equalsIgnoreCase(type.trim())){
                return value.index;
            }
        }
        return 0;
    }

}
