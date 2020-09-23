package com.one.exercise.enums;

public enum AdminType {

    NOTADMIN("not-admin",0),ADMIN("admin",1);

    public String type;
    public int index;

    AdminType(String type,int index){
        this.type = type;
        this.index = index;
    }

}
