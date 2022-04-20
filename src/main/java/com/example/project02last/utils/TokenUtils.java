package com.example.project02last.utils;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;

/*Header（头部）：放有签名算法和令牌类型
  Payload（负载）：你在令牌上附带的信息：比如用户的姓名，这样以后验证了令牌之后就可以直接从这里获取信息而不用再查数据库了
  Signature（签名）：对前两部分的签名，防止数据篡改*/

//生成token类  主要防止不登录就直接通过网页进入后端
public class TokenUtils {
    public static String genToken(String userId,String sign){
        return JWT.create().withAudience(userId) //将user id作为token里面的负载
                .withExpiresAt(DateUtil.offsetHour(new Date(),2))//2小时后过期
                .sign(Algorithm.HMAC256(sign));
        //头部放userId
    }
}
