package com.one.exercise.mapper;

import com.one.exercise.pojo.Admire;
import com.one.exercise.pojo.admire.AdmireVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface AdmireMapper extends Mapper<Admire>, MySqlMapper<Admire> {

    // 根据学生id获取点赞过的动态列表
    List<Long> selectLikeState(long studentIdMe);

    @Select("SELECT * FROM `exercise`.`admire` a,`exercise`.`dynamic_message` dm , (SELECT student_id, nick_name, avatar as face FROM student ) s " +
            "WHERE a.dy_id = dm.dy_id AND s.student_id = a.admire_id " +
            "ORDER BY a.admire_id DESC")
    List<AdmireVO> selectAdmireList();

    // AdmireId
    @Update("UPDATE admire SET is_read = #{admire.isRead} WHERE admire_id = #{admire.admireId}")
    int updateAdmireRead(@Param("admire") Admire admire);
}
