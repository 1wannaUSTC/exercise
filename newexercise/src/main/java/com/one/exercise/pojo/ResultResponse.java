package com.one.exercise.pojo;

public class ResultResponse {

    private Integer state;

    private String msg;

    private Object data;

    public ResultResponse(){

    }

    public ResultResponse(Integer state,String msg){
        this.state = state;
        this.msg = msg;
    }

    public ResultResponse(Integer state,String msg,Object data){
        this.state = state;
        this.msg = msg;
        this.data = data;
    }

    public static ResultResponse RETURN(Integer state,String msg,Object data){
        return new ResultResponse(state,msg,data);
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
