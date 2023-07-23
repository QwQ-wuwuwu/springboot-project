package com.example.config;

import com.example.interceptor.AdminInterceptor;
import com.example.interceptor.LoginInterceptor;
import com.example.interceptor.TeacherInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;
    @Autowired
    private TeacherInterceptor teacherInterceptor;
    @Autowired
    private AdminInterceptor adminInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/api/**");//要进行拦截的路径
        registry.addInterceptor(teacherInterceptor).addPathPatterns("/api/teacher/**");
        registry.addInterceptor(adminInterceptor).addPathPatterns("/api/admin/**");
    }
}
