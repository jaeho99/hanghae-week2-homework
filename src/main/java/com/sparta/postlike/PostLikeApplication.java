package com.sparta.postlike;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableJpaAuditing
@SpringBootApplication
public class PostLikeApplication {

    public static void main(String[] args) {
        SpringApplication.run(PostLikeApplication.class, args);
    }
    @Bean
    public WebMvcConfigurer corsConfigurer() {      //CORS 해결을 위한 코드
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");
            }
        };
    }

}
