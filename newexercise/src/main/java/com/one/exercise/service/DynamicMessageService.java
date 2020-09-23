package com.one.exercise.service;

import com.one.exercise.pojo.DynamicMessage;
import com.one.exercise.pojo.dynamicmessage.DynamicMessageDTO;

import java.util.List;

public interface DynamicMessageService {

    /** 学生端：发布动态 */
    boolean saveDynamicMessage(DynamicMessage dynamicMessage, List<String> images);

    /** 统计所有发布数量 */
    int countAll();
    /**
     * 条件统计
     */
    int countAll(DynamicMessage dynamicMessage);

    /** */
    List<DynamicMessageDTO> queryNewestHot(long studentId, int pattern, int startIndex, Integer pageSize);
    /** 获取最新发布 **/
    List<DynamicMessage> queryNewest(int startIndex, Integer pageSize);
    /** 获取推荐 */
    List<DynamicMessage> queryHot(int startIndex, Integer pageSize);

    /** 获取我发布的动态 */
    List<DynamicMessageDTO> queryMe(long studentId, int startIndex, Integer pageSize);

    /** 删除动态 */
    boolean deleteDynamicMessage(DynamicMessage dynamicMessage);
}
