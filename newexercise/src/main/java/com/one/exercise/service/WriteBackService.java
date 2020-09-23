package com.one.exercise.service;

import com.one.exercise.pojo.WriteBack;

public interface WriteBackService {

    /**
     * 回复评论
     */
    WriteBack writeBack(WriteBack writeBack);

    /**
     * 删除回复
     */
    boolean deleteWriteBack(WriteBack writeBack);
}
