package com.example.project02last.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

//文件上传类
@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${files.upload.path}")
    private String fileUploadPath;

    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {
        String originalFilename=file.getOriginalFilename();//获取文件名称
        String type=FileUtil.extName(originalFilename);//获取文件类型 包括后缀
        long size=file.getSize();
        //文件存储到磁盘
        File uploadParentFile=new File(fileUploadPath);
        if(!uploadParentFile.exists()){
            //这里是按照fileUploadPath创建路径
            //判断是否有文件或目录同名存在,若不存在创建一个新的文件目录
            uploadParentFile.mkdirs();
        }
        //用IdUtil 定义文件的唯一表示码
        String uid= IdUtil.fastSimpleUUID();
        //补全文件名，表示和文件类型
        File uploadFile= new File(fileUploadPath+uid+StrUtil.DOT+type);
        //把获取到的文件存储到磁盘目录
        file.transferTo(uploadFile);
        return "";
    }
}
