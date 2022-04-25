package com.example.project02last.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Quarter;
import com.example.project02last.common.Result;
import com.example.project02last.entity.User;
import com.example.project02last.service.IUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/echarts")
public class EchartsController {
    @Resource
    private IUserService userService;

    //假数据测试接口
    @GetMapping("/example")
    public Result get(){
        Map<String,Object> map=new HashMap<>();
        //CollUtil.newArrayList可以快速创建一个List
        map.put("x", CollUtil.newArrayList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"));
        map.put("y", CollUtil.newArrayList(150, 230, 224, 218, 135, 147, 260));
        return Result.success(map);
    }


    @GetMapping("/members")
    public Result members(){
     List<User> list= userService.list();
     int q1=0;
     int q2=0;
     int q3=0;
     int q4=0;
     for (User user:list){
         Date createTime=user.getCreateTime();
         Quarter quarter= DateUtil.quarterEnum(createTime);//DateUtil日期分季度返回值应该是Q1，Q2，Q3，Q4
         switch (quarter){ //case和quarter一样时就break
             case Q1:q1+=1;break; //统计季度人数
             case Q2:q2+=1;break;
             case Q3:q3+=1;break;
             case Q4:q4+=1;break;
             default:break;
         }
     }
     return Result.success(CollUtil.newArrayList(q1,q2,q3,q4));
    }
}
