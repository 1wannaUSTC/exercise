package com.one.exercise.mapper;

import com.one.exercise.pojo.DynamicMessage;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface DynamicMessageMapper extends Mapper<DynamicMessage> {

    /** 获取最新 */
    List<DynamicMessage> selectNewest(int startIndex, Integer pageSize);
    /** 获取最热（推荐） */
    List<DynamicMessage> selectHot(int startIndex, Integer pageSize);

    /** 获取当前用户获取所有的动态 */
    List<DynamicMessage> queryMe(long studentId, int startIndex, Integer pageSize);

    /** 更新点赞数量 */
    int updateLikeNumber(Long dyId, int i);

    /** 更新评论数量 */
    int updateCommentNumber(Long dyId, int i);
}