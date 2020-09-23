package com.one.exercise.enums;

/**
 * 认证枚举：
 * 教师认证：0未认证，1认证
 */
public enum Qualification {
    YES(0, "未认证"), NO(1, "认证");

    private Integer index;
    private String qua;

    private Qualification(Integer index,String qua){
        this.index = index;
        this.qua = qua;
    }

    public static String getQua(int index){
        Qualification[] values = Qualification.values();
        for (Qualification value : values) {
            if (value.getIndex()==index) {
                return value.getQua();
            }
        }
        return null;
    }

    public static Integer getIndex(String qua){
        Qualification[] values = Qualification.values();
        for (Qualification value : values) {
            if (value.getQua() == qua) {
                return value.getIndex();
            }
        }
        return null;
    }

    public Integer getIndex() {
        return index;
    }

    public String getQua() {
        return qua;
    }
}
