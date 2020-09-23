package com.one.exercise.service.impl;

import com.one.exercise.mapper.CommentMapper;
import com.one.exercise.mapper.DynamicMessageMapper;
import com.one.exercise.pojo.Comment;
import com.one.exercise.pojo.comment.CommentDTO;
import com.one.exercise.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private DynamicMessageMapper dynamicMessageMapper;

    @Override
    @Transactional
    public Comment comment(Comment comment) {
        /**
         * 1. 添加评论
         * 2. 更新评论记录
         */
        try {
            int i = commentMapper.insertSelective(comment);
            int i2 = dynamicMessageMapper.updateCommentNumber(comment.getDyId(), i);
            return i2>0 ? commentMapper.selectByPrimaryKey(comment) : null;
        }catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public List<CommentDTO> commentList(Long dyId) {

        return commentMapper.getListByDyId(dyId);
    }

    @Override
    public boolean deleteComment(Comment comment) {

        try {
            int delete = commentMapper.delete(comment);
            if (delete > 0){
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            return false;
        }

    }
}
