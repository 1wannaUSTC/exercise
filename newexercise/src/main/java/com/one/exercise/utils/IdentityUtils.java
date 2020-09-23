package com.one.exercise.utils;

import com.alibaba.fastjson.JSON;
import com.one.exercise.enums.VerifyType;
import com.one.exercise.pojo.Identity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
public class IdentityUtils {

    private static StringRedisTemplate stringRedisTemplate = (StringRedisTemplate) SpringContextUtils.getBeanByClass(StringRedisTemplate.class);

    /** 获取身份对象 */
    public static Identity check(String code) throws Exception {
        try {
            // 不能为空
            if (Tools.isNullStr(code)) {
                return null;
            }
            // 在redis缓存中查找Code是否存在
            String s = "";
            try {
                s = stringRedisTemplate.opsForValue().get(code);
                log.info("stringRedisTemplate key: teacherId===" + s);
            } catch (Exception e) {
                return null;
            }

            // 获取结果不能为空
            if (Tools.isNullStr(s)) {
                return null;
            }


            Identity identity = JSON.parseObject(s, Identity.class);
            if (identity != null) {
                // 重置生存时间
                stringRedisTemplate.expire(code, 30 * 24, TimeUnit.HOURS);
            }
            return identity;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

    /** 生成身份码 */
    public static String build(Identity identity) throws Exception {
        try {
            String v = JSON.toJSONString(identity);
            String code = (UUID.randomUUID().toString() + UUID.randomUUID().toString() + UUID.randomUUID().toString()).replace("-", "");;
            stringRedisTemplate.opsForValue().set(code, v, 60*30, TimeUnit.MINUTES);
            return code;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    /**
     * 权限控制：管理员放行
     * admin > teacher > student
     **/
    public static boolean greenAdmin(Identity identity){
        Integer identityCode = identity.getIdentityCode();
        if (identityCode == VerifyType.ADMIN.index){
            return true;
        }
        return false;
    }

    /**
     * 教师放行
     */
    public static boolean greenTeacher(Identity identity){
        Integer identityCode = identity.getIdentityCode();
        if (identityCode == VerifyType.TEACHER.index){
            return true;
        }
        return false;
    }

    /**
     * 学生放行
     */
    public static boolean greenStudent(Identity identity){
        Integer identityCode = identity.getIdentityCode();
        if (identityCode == VerifyType.STUDENT.index){
            return true;
        }
        return false;
    }

    /**
     * 权限控制：管理员和教师放行
     * admin > teacher > student
     **/
    public static boolean greenAdminAndTeacher(Identity identity){
        Integer identityCode = identity.getIdentityCode();
        if (identityCode == VerifyType.ADMIN.index || identityCode == VerifyType.TEACHER.index){
            return true;
        }
        return false;
    }

    /**
     * 权限控制：所有用户可用
     * admin > teacher > student
     **/
    public static boolean greenAll(Identity identity){
        Integer identityCode = identity.getIdentityCode();
        if (identityCode == VerifyType.ADMIN.index || identityCode == VerifyType.TEACHER.index || identityCode == VerifyType.STUDENT.index){
            return true;
        }
        return false;
    }

}
