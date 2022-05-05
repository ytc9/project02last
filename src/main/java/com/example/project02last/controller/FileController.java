package com.example.project02last.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.project02last.common.Result;
import com.example.project02last.entity.Files;
import com.example.project02last.entity.User;
import com.example.project02last.mapper.FileMapper;
import com.example.project02last.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

//文件上传接口类
@RestController
@RequestMapping("/file")
public class FileController {
    @Resource
    private FileMapper fileMapper;
     //获取yml里面的路径
    @Value("${files.upload.path}")
    private String fileUploadPath;

    //封装md5函数
    private Files getFileByMd5(String md5){
        QueryWrapper<Files> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("md5",md5);
        //md5可能查询到多条记录所以这里返回值也要
        //如果md5重复了也会存入数据库但是不会存储文件
        List<Files> filesList= fileMapper.selectList(queryWrapper);
        return filesList.size()==0?null:filesList.get(0);
    }

    //上传接口
    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {
        String originalFilename=file.getOriginalFilename();//获取文件名称
        String type=FileUtil.extName(originalFilename);//获取文件类型 包括后缀
        long size=file.getSize();

        //用IdUtil 定义文件的唯一表示码
        String uid= IdUtil.fastSimpleUUID();
        //补全文件名，表示和文件类型
        String fileUid=uid+StrUtil.DOT+type;

        File uploadFile= new File(fileUploadPath+fileUid);

        if(!uploadFile.getParentFile().exists()){
            //这里是按照fileUploadPath创建路径,如果路径不包含有fileUploadPath里面的路径就会创建新的目录
            uploadFile.getParentFile().mkdirs();
        }
        //用md5判断是否存储文件
        String md5;
        String url;
        //文件存储到磁盘
        file.transferTo(uploadFile);

        md5= SecureUtil.md5(uploadFile);
        Files dbFiles=getFileByMd5(md5);
        if (dbFiles!=null){
            url=dbFiles.getUrl();
            uploadFile.delete();
        }else {
            url="http://localhost:9090/file/"+fileUid;
        }

        //通过bean类存储文件信息 存储数据库
        Files saveFile =new Files();
        saveFile.setName(originalFilename);
        saveFile.setType(type);
        saveFile.setSize(size/1024);
        saveFile.setUrl(url);
        saveFile.setMd5(md5);
        //将数据插入数据库 这里是用的basemapper接口不是mybatis-plus的接口
        fileMapper.insert(saveFile);

        return url;
    }

    //下载接口(可复用)
    @GetMapping("/{fileUid}")
    public void download(@PathVariable String fileUid, HttpServletResponse response) throws IOException {
        //根据文件的唯一标识来获取文件
        File uploadFile=new File(fileUploadPath+fileUid);
        ServletOutputStream os=response.getOutputStream();
        //设置请求头和设置内容的类型
        response.addHeader("Content-Dispositon","attachment;filename="+ URLEncoder.encode(fileUid,"utf-8"));
        response.setContentType("application/octet-stream");
        //读取文件的字节流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();
    }

    //单个删除接口
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        //由于这里不是使用mybatis plus的接口所以只能用基本方式
        Files files=fileMapper.selectById(id);
        //这里是逻辑删除所以只设置下字段变量就行
        files.setIsDelete(true);
        fileMapper.updateById(files);
        return Result.success();
    }

    //保存更新接口
    @PostMapping("/update")
    public Result update(@RequestBody Files files) {
        //updateById会自动去寻找对应的对象和传id是一样的
        return Result.success(fileMapper.updateById(files)) ;
    }

    //批量删除接口
    @PostMapping("/del/batch") //Delet接口没办法从前端传纯数组所以要用post接口
    public Result deleteBatch(@RequestBody List<Integer> ids){  //[1,2,3]
        QueryWrapper<Files> queryWrapper=new QueryWrapper<>();
        //sql in后面接多个数据的id
        queryWrapper.in("id",ids);
        List<Files> files = fileMapper.selectList(queryWrapper);
        for (Files file:files){
            file.setIsDelete(true);
            fileMapper.updateById(file);
        }
        return Result.success();
    }


    //分页查询接口
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name) {
        //参数里面如果不加defaultValue不传值就会报错加了就会置空
        QueryWrapper<Files> queryWrapper=new QueryWrapper<>();
        //查询未删除的记录
        queryWrapper.eq("is_delete",false);
        /*给新增的数据倒序*/
        queryWrapper.orderByDesc("id");
        if (!"".equals(name)){
            queryWrapper.like("name",name);
        }
        IPage<Files> page=new Page<>(pageNum,pageSize);
        //Page和selectPage区别是分页数据量大时用page比较好 而且page要用mybatis plus的接口
        return Result.success(fileMapper.selectPage(page,queryWrapper));
    }

}
