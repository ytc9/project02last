package com.example.project02last.config.interceptor;


import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.project02last.common.Constants;
import com.example.project02last.entity.User;
import com.example.project02last.exception.ServiceException;
import com.example.project02last.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //通过前端传来的token进行验证拦截
        String token = request.getHeader("token");
        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)){
            return true;
        }
        //如果token为空返回自定义异常
        if (StrUtil.isBlank(token)){
            throw new ServiceException(Constants.CODE_401,"无token,请重新登录");
        }
        //用decode方法 获取token中的user id
        String userId;
        try{
            //将得到的userId由解密token而得到
            //get(0)也就是获取token的负载了(在tokenUtils里面userid放在了负载里)
            userId= JWT.decode(token).getAudience().get(0);
        }catch (JWTDecodeException j){
            throw new ServiceException(Constants.CODE_401,"token验证失败,请重新登录");
        }
        //根据获取的user id去数据库中查找到user对象
        User user=userService.getById(userId);
        if (user==null){
            throw new ServiceException(Constants.CODE_401,"用户不存在,请重新登录");
        }
        //用户密码加签验证token
        JWTVerifier jwtVerifier= JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try{
            jwtVerifier.verify(token);
        }catch (JWTVerificationException e){
            throw new ServiceException(Constants.CODE_401,"token验证失败,请重新登录");
        }
        return true;
    }
}
