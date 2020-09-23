package com.one.exercise.service.impl;

import com.one.exercise.mapper.CodeMapMapper;
import com.one.exercise.pojo.CodeMap;
import com.one.exercise.service.CodeMapService;
import com.one.exercise.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class CodeMapServiceImpl implements CodeMapService {

    @Autowired
    private CodeMapMapper codeMapMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public int saveCode(String email, String code) {
        CodeMap codeMap = new CodeMap();
        codeMap.setEmail(email);
        codeMap.setCode(code);
        return codeMapMapper.insertSelective(codeMap);
    }

    @Override
    public String selectCode(String email) throws Exception {
        String code = "------";
        CodeMap codeMap = new CodeMap();
        codeMap.setEmail(email);
        CodeMap one =  codeMapMapper.selectOne(codeMap);
        return one.getCode();
    }

    @Override
    public String selectCodeRedis(String email) {
        String s = stringRedisTemplate.opsForValue().get(email);
        return s;
    }

    /**
     * 将验证码保存到redis缓存中
     * @param email
     * @param code
     * @return
     */
    @Override
    public int saveCodeRedis(String email, String code) {

        // 获取code
        String s = stringRedisTemplate.opsForValue().get(email);
        // 如果为null，表示过期，添加
        if ( Tools.isNullStr(s)){
            stringRedisTemplate.opsForValue().set(email,code,5*60, TimeUnit.SECONDS);
            Long expire = stringRedisTemplate.getExpire(email);
            System.out.println(expire);
            return 1;
        }else {
            return 0;
        }

    }
}
