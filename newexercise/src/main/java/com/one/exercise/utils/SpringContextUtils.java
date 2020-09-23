package com.one.exercise.utils;

import com.one.exercise.exception.InexistenceException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author hpsyche
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {
    /**
     * 上下文对象实例
     */
    private static ApplicationContext applicationContext;

    public static void getIdByCode(String verifyCode) {
    }

    @Override
    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtils.applicationContext = applicationContext;
    }

    /**
     * 获取applicationContext
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }

    public static Object getBeanByClass(Class clazz){
        return getApplicationContext().getBean(clazz);
    }

}

