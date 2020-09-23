package com.one.exercise.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class GlobalCorsConfig {

    /**
     * 配置拦截器
     * GlobalInterceptorConfig
     * Interceptor
     */
    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                // 登录验证
                registry.addInterceptor(new LogCostInterceptor())
                        .addPathPatterns("/**")
                        .excludePathPatterns(
                                "/test/**",
                                "/img_server/**",
                                "/static/**",
                                "/teacher/register",
                                "/teacher/login",
                                "/teacher/qualification",
                                "/student/login",
                                "/teacher/getCheckCode",
                                "/question/downloadQuestionExcel"
                        );
            }
        };
    }

}
