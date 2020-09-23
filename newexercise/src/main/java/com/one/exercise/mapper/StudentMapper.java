package com.one.exercise.mapper;

import com.one.exercise.mapper.custom.StudentCustom;
import com.one.exercise.pojo.Student;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;
import java.util.Set;

public interface StudentMapper extends Mapper<Student>, MySqlMapper<Student> {

    @Select("SELECT * FROM `exercise`.`student` WHERE validity=1 LIMIT #{startIndex},#{pageSize}")
    List<Student> selectStudentList(int startIndex, int pageSize);

    @SelectProvider(type = StudentCustom.class, method = "selectStudentByIds")
    List<Student> selectStudentByIds(@Param("idSet") Set<Long> idSet);
}
