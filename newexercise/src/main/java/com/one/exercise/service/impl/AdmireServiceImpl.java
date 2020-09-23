package com.one.exercise.service.impl;

import com.one.exercise.mapper.AdmireMapper;
import com.one.exercise.mapper.DynamicMessageMapper;
import com.one.exercise.pojo.Admire;
import com.one.exercise.pojo.admire.AdmireVO;
import com.one.exercise.service.AdmireService;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class AdmireServiceImpl implements AdmireService {

    @Resource
    private AdmireMapper admireMapper;

    @Resource
    private DynamicMessageMapper dynamicMessageMapper;

    @Override
    @Transactional
    public boolean insertAdmire(Admire admire) {
        /**
         * 1. 向点赞表中追加数据
         * 2. 更新点赞数量
         */
        admire.setAdmireId(null);
        try {
            int i1 = admireMapper.insertSelective(admire);
            int i2 = dynamicMessageMapper.updateLikeNumber(admire.getDyId(), i1);
            return i2 > 0;
        }catch (Exception e){
            log.error(e.getMessage());
            return false;
        }
    }

    @Override
    public List<AdmireVO> selectAdmireList() {
        return admireMapper.selectAdmireList();
    }

    @Override
    @Transactional
    public boolean updateAdmireRead(Admire admire) {
        try {
            int i = admireMapper.updateAdmireRead(admire);
            return i > 0;
        }catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteAdmire(Admire admire) {
        if (admire.getDyId() != null && admire.getAdmireId() != null){
            int delete = admireMapper.delete(admire);
            return delete>0;
        }
        return false;
    }
}
