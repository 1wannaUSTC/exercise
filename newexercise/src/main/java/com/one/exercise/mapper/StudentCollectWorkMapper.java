package com.one.exercise.mapper;

import com.one.exercise.pojo.StudentCollectWork;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface StudentCollectWorkMapper extends Mapper<StudentCollectWork> {

    int cancelCollectWork(Long workId, Long studentId);

    @Select("SELECT work_id FROM `exercise`.`student_collect_work` WHERE student_id=#{studentId}")
    List<Long> getWorkIdByRelationStudentId(long studentId);
}