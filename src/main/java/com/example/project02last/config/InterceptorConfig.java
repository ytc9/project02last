package com.example.project02last.config;

import com.example.project02last.config.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//注册拦截器
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**")
                //不是登录的接口都直接放行不需要验证
                .excludePathPatterns("/user/login","/user/register","**/export","**/import");
        //拦截所有请求，通过判断token是否合法来判断是否登录
    }
    //这里不能直接在addInterceptors里面去new JwtInterceptor 只能按单例模式的方式注入
    @Bean
    public JwtInterceptor jwtInterceptor(){
        return new JwtInterceptor();
    }
}
