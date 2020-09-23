package com.one.exercise.service.impl;

import com.one.exercise.mapper.TeacherMapper;
import com.one.exercise.pojo.Teacher;
import com.one.exercise.service.TeacherService;
import com.one.exercise.utils.PageUtil;
import com.one.exercise.utils.Tools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public int saveTeacher(Teacher teacher) {
        int i = 0;
        i = teacherMapper.insertSelective(teacher);
        log.info("向数据库保存一个teacher对象,TeacherServiceImpl.saveTeacher : " + i);
        return i;
    }

    @Override
    public Teacher saveOneTeacher(Teacher teacher) {
        teacherMapper.insertSelective(teacher);
        Teacher resultTeacher = teacherMapper.selectByPrimaryKey(teacher.getTeacherId());
        return resultTeacher;
    }

    public int delete(String number) {
        int i = 0;
        Example example = new Example(Teacher.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("number",number);
        i = teacherMapper.deleteByExample(example);
        return i;
    }

    @Override
    public int logicDelete(Integer teacherId) {
        Teacher teacher = new Teacher();
        teacher.setTeacherId(teacherId);
        teacher.setValidity(0);
        int i = teacherMapper.updateByPrimaryKeySelective(teacher);
        return i;
    }

    @Override
    public Teacher selectTeacherByEmail(String email) {
        Teacher teacher = new Teacher();
        teacher.setEmail(email);
        Teacher one = null;
        try{
            one = teacherMapper.selectOne(teacher);
            if (one == null){
                return null;
            }
        }catch (Exception e){
            return null;
        }
        return one;
    }

    @Override
    public boolean hasTeacherByEmail(String email) {
        Teacher teacher = selectTeacherByEmail(email);
        if (teacher == null){
            return false;
        }
        return true;

    }

    @Override
    public Teacher selectTeacherByEmailAndPw(String email,String password) {
        Teacher teacher = new Teacher();
        teacher.setEmail(email);
        teacher.setPassword(password);
        Teacher one = null;
        try{
            one = teacherMapper.selectOne(teacher);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return one;
    }

    @Override
    public Teacher updateTeacherById(Teacher teacher) {
        int i = teacherMapper.updateByPrimaryKeySelective(teacher);
        if (i>0){
            Teacher teacher1 = teacherMapper.selectByPrimaryKey(teacher);
            return teacher1;
        }else {
            return null;
        }
    }

    @Override
    public int updateTeacherFollow(Integer teacherId, Integer quantity) {
        return teacherMapper.updateTeacherFollow(teacherId, quantity);
    }

    @Override
    public Teacher selectTeacherById(int teacherId) {

        Teacher teacher = new Teacher();
        teacher.setTeacherId(teacherId);
        Teacher teacher1 = teacherMapper.selectByPrimaryKey(teacher);
        return teacher1;
    }

    @Override
    public Map<String, Object> selectTeacherList(int startPage, int pageSize) {
        /**
         * 统计出去自身外的有效数据
         */
        Map<String, Object> map = new HashMap<String, Object>();

        int total = teacherMapper.selectCountTeacher();
        PageUtil pageUtil = new PageUtil(total, pageSize);
        map.put("pageInfo", pageUtil);

        List<Teacher> teacherList = teacherMapper.selectTeacherList(pageUtil.getStartIndex(startPage), pageSize);
        map.put("teacherList", teacherList);

        return map;
    }

    @Override
    public List<Teacher> selectTeacherList(Integer subjectId, int startIndex, int pageSize) {
        List<Teacher> teachers = teacherMapper.selectSubjectTeacherList(subjectId, startIndex, pageSize);
        return teachers;
    }

    @Override
    public int countSubjectTeacherList(Integer subjectId) {
        int i = teacherMapper.countSubjectTeacherList(subjectId);
        return i;
    }

    @Override
    public List<Teacher> selectTeacherByIdList(List<Integer> teacherIdList) {
        return teacherMapper.selectTeacherByIdList(teacherIdList);
    }
}
