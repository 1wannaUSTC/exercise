package com.one.exercise.service;

import com.one.exercise.pojo.Admire;
import com.one.exercise.pojo.admire.AdmireVO;

import java.util.List;

public interface AdmireService {

    // 点赞
    boolean insertAdmire(Admire admire);

    // 获取点赞列表
    List<AdmireVO> selectAdmireList();

    // 标记点赞
    boolean updateAdmireRead(Admire admire);

    // 取消点赞
    boolean deleteAdmire(Admire admire);
}
