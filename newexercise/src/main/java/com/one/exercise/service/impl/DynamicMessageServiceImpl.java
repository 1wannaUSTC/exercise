package com.one.exercise.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.one.exercise.mapper.AdmireMapper;
import com.one.exercise.mapper.DynamicMessageMapper;
import com.one.exercise.mapper.DynamicPictureMapper;
import com.one.exercise.mapper.StudentMapper;
import com.one.exercise.pojo.DynamicMessage;
import com.one.exercise.pojo.DynamicPicture;
import com.one.exercise.pojo.Student;
import com.one.exercise.pojo.dynamicmessage.DynamicMessageDTO;
import com.one.exercise.service.DynamicMessageService;
import com.one.exercise.utils.Tools;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.rmi.runtime.Log;

import javax.annotation.Resource;
import java.util.*;

@Service
public class DynamicMessageServiceImpl implements DynamicMessageService {

    @Resource
    private DynamicMessageMapper dynamicMessageMapper;

    @Resource
    private DynamicPictureMapper dynamicPictureMapper;

    @Resource
    private StudentMapper studentMapper;

    @Resource
    private AdmireMapper admireMapper;

    @Override
    @Transactional
    public boolean saveDynamicMessage(DynamicMessage dynamicMessage, List<String> images) {

        dynamicMessage.setDyId(null);
        int i = dynamicMessageMapper.insertSelective(dynamicMessage);

        if (images == null || images.size() == 0) {
            return i > 0;
        }

        List<DynamicPicture> dynamicPictures = new ArrayList<>();
        long dyId = dynamicMessage.getDyId();
        for (String image : images) {
            DynamicPicture dynamicPicture = new DynamicPicture();
            dynamicPicture.setDynamicPictureId(null);
            dynamicPicture.setDyId(dyId);
            dynamicPicture.setImagePath(image);
            dynamicPictures.add(dynamicPicture);
        }
        int i1 = dynamicPictureMapper.insertList(dynamicPictures);

        return i > 0 && i1 > 0;
    }

    @Override
    public int countAll() {
        DynamicMessage dynamicMessage = new DynamicMessage();
        dynamicMessage.setValidity(1);
        return dynamicMessageMapper.selectCount(dynamicMessage);
    }

    @Override
    public int countAll(DynamicMessage dynamicMessage) {
        return dynamicMessageMapper.selectCount(dynamicMessage);
    }

    @Override
    public List<DynamicMessageDTO> queryNewestHot(long studentIdMe, int pattern, int startIndex, Integer pageSize) {
        List<DynamicMessage> list = null;
        if (pattern == 1){
            list = dynamicMessageMapper.selectNewest(startIndex, pageSize);
        }else if (pattern == 2){
            list = dynamicMessageMapper.selectHot(startIndex, pageSize);
        }

        Set<Long> idSet = new TreeSet<>();
        for (DynamicMessage dynamicMessage : list) {
            idSet.add(dynamicMessage.getInitiator());
        }

        // 根据学生id获取点赞过的动态列表
        List<Long> dyIdList = admireMapper.selectLikeState(studentIdMe);

        // 根据学生id获取学生对象
        List<Student> students = studentMapper.selectStudentByIds(idSet);


        List<DynamicMessageDTO> dynamicMessageDTOs = new ArrayList<>();

        for (DynamicMessage dynamicMessage : list) {
            DynamicMessageDTO dynamicMessageDTO = new DynamicMessageDTO();
            BeanUtils.copyProperties(dynamicMessage, dynamicMessageDTO);
            List<DynamicPicture> pictures = dynamicMessageDTO.getPictures();

            Iterator<DynamicPicture> iterator = pictures.iterator();
            while (iterator.hasNext()){
                DynamicPicture next = iterator.next();
                if (Tools.isNullStr(next.getImagePath())){
                    iterator.remove();
                }
            }

//            for (int i = 0; i < pictures.size(); i++) {
//                if (Tools.isNullStr(pictures.get(i).getImagePath())){
//                    pictures.remove(pictures.get(i));
//                }
//            }

            for (Student student : students) {
                long studentId = student.getStudentId();
                long initiator = dynamicMessage.getInitiator();
                if (studentId == initiator){
                    dynamicMessageDTO.setNickname(student.getNickName());
                    dynamicMessageDTO.setFace(student.getAvatar());
                    dynamicMessageDTO.setIsLike(dyIdList.contains(dynamicMessage.getDyId()));
                    dynamicMessageDTOs.add(dynamicMessageDTO);
                    break;
                }
            }
        }
        return dynamicMessageDTOs;
    }

    @Override
    public List<DynamicMessage> queryNewest(int startIndex, Integer pageSize) {
        return dynamicMessageMapper.selectNewest(startIndex, pageSize);
    }

    @Override
    public List<DynamicMessage> queryHot(int startIndex, Integer pageSize) {
        return dynamicMessageMapper.selectHot(startIndex, pageSize);
    }

    @Override
    public List<DynamicMessageDTO> queryMe(long studentId, int startIndex, Integer pageSize) {
        List<DynamicMessage> dynamicMessages = dynamicMessageMapper.queryMe(studentId, startIndex, pageSize);

        // 根据学生id获取点赞过的动态列表
        List<Long> dyIdList = admireMapper.selectLikeState(studentId);

        List<DynamicMessageDTO> dynamicMessageDTOs = new ArrayList<>();
        for (DynamicMessage dynamicMessage : dynamicMessages) {
            DynamicMessageDTO dynamicMessageDTO = new DynamicMessageDTO();
            BeanUtils.copyProperties(dynamicMessage, dynamicMessageDTO);
            dynamicMessageDTO.setIsLike(dyIdList.contains(dynamicMessage.getDyId()));

            Iterator<DynamicPicture> iterator = dynamicMessageDTO.getPictures().iterator();
            while (iterator.hasNext()){
                DynamicPicture next = iterator.next();
                if (Tools.isNullStr(next.getImagePath())){
                    iterator.remove();
                }
            }

            dynamicMessageDTOs.add(dynamicMessageDTO);
        }
        return dynamicMessageDTOs;
    }

    @Override
    public boolean deleteDynamicMessage(DynamicMessage dynamicMessage) {

        try {
            int delete = dynamicMessageMapper.delete(dynamicMessage);

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
