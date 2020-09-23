package com.one.exercise.mapper;

import com.one.exercise.mapper.custom.StudentSubjectCustom;
import com.one.exercise.pojo.StudentSubject;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface StudentSubjectMapper extends Mapper<StudentSubject>, MySqlMapper<StudentSubject> {

    // 保存科目、忽略重复
    @SelectProvider(type = StudentSubjectCustom.class, method = "insertStudentSubjectList")
    @ResultType(Integer.class)
    void insertStudentSubjectList(@Param("subjectList") List<StudentSubject> subjectList);

}
