package com.example.project02last.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//定义接口统一返回包装类


//无参构造和有参构造
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    //和前端的交流数据 成功和失败
    private String code;
    private String msg;
    private Object data;

    public static Result success(){//单例模式返回对象
        return new Result(Constants.CODE_200,"",null);
    }
    public static Result success(Object data){//单例模式返回对象
        return new Result(Constants.CODE_200,"",data);
    }

    public static Result error(String code,String msg){//单例模式返回对象
        return new Result(code,msg,null);
    }

    public static Result error(){
        return new Result(Constants.CODE_500,"系统错误",null);
    }
}
