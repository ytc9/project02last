package com.example.project02last.config;

import com.example.project02last.config.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//注册拦截器实现JwtInterceptor
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //这里不能直接在addInterceptors里面去new JwtInterceptor 只能按单例模式的方式注入
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**")
                //不是登录的接口都直接放行不需要验证
                .excludePathPatterns("/user/login","/user/register","/**/export","/**/import","/file/**");
        //拦截所有请求，通过判断token是否合法来判断是否登录
    }

    /*@Bean是一个方法级别上的注解，主要用在@Configuration注解的类里，
    也可以用在@Component注解的类里。@Bean注解主要是把容器交给springboot里的spring容器来管理*/
    @Bean
    public JwtInterceptor jwtInterceptor(){
        return new JwtInterceptor();
    }
}
