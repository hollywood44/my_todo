package com.share.my_todo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/static/assets/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8077","http://my-todo-front.s3-website.ap-northeast-2.amazonaws.com") // 자원 공유를 허락할 Origin을 지정
                .allowedMethods("GET","POST","PUT","DELETE","OPTIONS","PATCH")
                .allowCredentials(true)// 쿠키 요청
//                .exposedHeaders(HttpHeaders.AUTHORIZATION)
                .maxAge(3600); // preflight 요청에 대한 응답을 브라우저에서 캐싱하는 시간
    }
}
