package com.example.project02last.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.project02last.entity.User;
import com.example.project02last.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/*Header（头部）：放有签名算法和令牌类型
  Payload（负载）：你在令牌上附带的信息：比如用户的姓名，这样以后验证了令牌之后就可以直接从这里获取信息而不用再查数据库了
  Signature（签名）：对前两部分的签名，防止数据篡改*/
//@component把该类注解成容器里面的bean就是启动项目的时候会自动创建
@Component
//生成token类  主要防止不登录就直接通过网页进入后端
public class TokenUtils {
    //这里通过静态方法去获取数据必须引用静态对象
    //下面的判断就是包装一个判断类来通过token获取usrId再获取数据
    private static IUserService staticUserService;
    @Resource
    private IUserService userService;
    @PostConstruct
    public void setUserService(){
        //@PostConstruct该项目启动是就会启动这个类
        staticUserService=userService;
    }


    public static String genToken(String userId,String sign){
        return JWT.create().withAudience(userId) //将user id作为token里面的负载
                .withExpiresAt(DateUtil.offsetHour(new Date(),2))//2小时后过期
                .sign(Algorithm.HMAC256(sign));
        //头部放userId
    }


    public  static User getCurrentUser(){//默认调用获取当前登录用户信息
       //用HttpServletRequest从头部获取request对象 通过字段获取token
        try{
        HttpServletRequest request=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token=request.getHeader("token");
        if (StrUtil.isBlank(token)){
            String userId=JWT.decode(token).getAudience().get(0);
            //Integer.valueOf将userId从int类包装成Integer类
            staticUserService.getById(Integer.valueOf(userId));
            }
        }catch (Exception e){
                return  null;
        }
    return null;
    }
}
