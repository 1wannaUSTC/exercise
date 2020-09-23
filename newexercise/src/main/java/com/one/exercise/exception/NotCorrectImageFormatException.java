package com.one.exercise.exception;

/**
 * 不是正确的图片格式异常类
 */
public class NotCorrectImageFormatException extends Exception {
    public NotCorrectImageFormatException(String message){
        super(message);
    }
    public NotCorrectImageFormatException(){
        super("不是正确的图片");
    }
}
