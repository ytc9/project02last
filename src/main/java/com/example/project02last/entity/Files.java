package com.example.project02last.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "file")
public class Files {
@TableId(type = IdType.AUTO)
private Integer id;
private String name;
private String type;
private Long size;  //数据库对应bigint
private String url;
private Boolean isDelete; //数据库对应tinyint
private Boolean enable;
private String md5;
}
