package com.one.exercise.service;

public interface CodeMapService {

    /**
     * 向数据库中添加一个验证码与邮箱的映射
     * @param email
     * @param code
     * @return
     */
    @Deprecated
    int saveCode(String email, String code);

    /**
     * 向redis中添加一个验证码与邮箱的映射
     * @param email
     * @param code
     * @return
     */

    int saveCodeRedis(String email, String code);

    /**
     * 根据邮箱获取一个验证码
     */
    @Deprecated
    String selectCode(String email) throws Exception;

    /**
     * 根据邮箱从redis中获取一个验证码
     */
    String selectCodeRedis(String email);
}
