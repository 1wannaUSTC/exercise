package com.one.exercise;

import com.one.exercise.config.CorsConfig;
import com.one.exercise.config.GlobalCorsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@Import({
        CorsConfig.class,
        GlobalCorsConfig.class
})
@MapperScan(basePackages = "com.one.exercise.mapper")
public class ExerciseApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExerciseApplication.class, args);
    }


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
