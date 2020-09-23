package com.one.exercise.service;

import com.one.exercise.pojo.Subject;

import java.util.List;

public interface SubjectService {

    /** 获取所有学科列表 */
    List<Subject> selectSubjectList();

    /** 通过subjectId获取科目相关信息 */
    Subject selectSubjectById(Integer subjectId);

    /** 根据teacherID 获取科目列表 */
    List<Subject> selectSubjectListByTeacherId(Integer teacherId);

    /** 选课 */
    List<Subject> insertSubjectListIgnore(Long teacherId, List<Integer> list);
}
