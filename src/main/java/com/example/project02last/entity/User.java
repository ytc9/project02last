package com.example.project02last.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

//@TableName和@TableId都要加不然会让mybatis-plus报错

@Data
@ToString
@TableName(value = "user")//这里由于框架所以要指定表名
public class User {
    @TableId(type = IdType.AUTO)//这里不指定的话不传id没问题如果传id会报错 AUTO表示自增
    private Integer id;
    private String username;
    @JsonIgnore //这里注解了之后不会把password传给前端
    private String password;
    private String nickname;
    private String email;
    private String phone;
    private String address;
    //这里可以指定数据的名字 实体属性名字就能够不一致 如果是驼峰也能直接识别
    @TableField(value = "avatar_url")
    private String avatar;
}
/*这有lombok可以不用写get和set 直接用@Data*/
