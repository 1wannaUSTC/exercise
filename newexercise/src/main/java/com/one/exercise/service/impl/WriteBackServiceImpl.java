package com.one.exercise.service.impl;

import com.one.exercise.mapper.DynamicMessageMapper;
import com.one.exercise.mapper.WriteBackMapper;
import com.one.exercise.pojo.WriteBack;
import com.one.exercise.service.WriteBackService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class WriteBackServiceImpl implements WriteBackService {

    @Resource
    private WriteBackMapper writeBackMapper;

    @Resource
    private DynamicMessageMapper dynamicMessageMapper;

    @Override
    @Transactional
    public WriteBack writeBack(WriteBack writeBack) {
        /**
         * 1. 添加评论
         * 2. 刷新评论信息
         */
        try {
            int i = writeBackMapper.insertSelective(writeBack);
            int i1 = dynamicMessageMapper.updateCommentNumber(writeBack.getDyId(), i);
            return i1 >0 ? writeBackMapper.selectByPrimaryKey(writeBack) : null;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    @Transactional
    public boolean deleteWriteBack(WriteBack writeBack) {
        try {
            int delete = writeBackMapper.delete(writeBack);
            return delete>0;
        }catch (Exception e){
            return false;
        }
    }
}
