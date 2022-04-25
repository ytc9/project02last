package com.example.project02last.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.project02last.common.Constants;
import com.example.project02last.common.Result;
import com.example.project02last.controller.dto.UserDTO;
import com.example.project02last.entity.User;
import com.example.project02last.service.IUserService;
import com.example.project02last.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 * @author 杨添辰
 * @since 2022-04-17
 */
@RestController
@RequestMapping("/user")
public class UserController {


/*@Autowired和Resource一样*/
@Resource
private IUserService userService;

//登录接口
@PostMapping("/login")
public Result Login(@RequestBody UserDTO userDTO) {
        String username=userDTO.getUsername();
        String password=userDTO.getPassword();
        //逻辑判断输入的账号和密码是否为空
        if (StrUtil.isBlank(username)||StrUtil.isBlank(password)){
                return Result.error(Constants.CODE_400,"参数错误");
        }  //这里用的是自己定义的类去返回
        UserDTO dto=userService.login(userDTO);
        return Result.success(dto);
}

//注册接口
@PostMapping("/register")
public Result register(@RequestBody UserDTO userDTO) {
        String username=userDTO.getUsername();
        String password=userDTO.getPassword();
        if (StrUtil.isBlank(username)||StrUtil.isBlank(password)){
                return Result.error(Constants.CODE_400,"参数错误");
        }
        User dto=userService.register(userDTO);
        return Result.success(dto);
        }

//更新和修改接口
@PostMapping
public Result save(@RequestBody User user) {
        return Result.success(userService.saveOrUpdate(user)) ;
        }

//批量删除接口
@PostMapping("/del/batch") //Delet接口没办法从前端传纯数组所以要用post接口
public Result deleteBatch(@RequestBody List<Integer> ids){  //[1,2,3]
        return Result.success(userService.removeByIds(ids));
        }

//全查接口暂时没用
@GetMapping
public Result findAll() {
        return Result.success(userService.list());
        }

//单个删除接口
@DeleteMapping("/{id}")
public Result delete(@PathVariable Integer id) {
        return Result.success(userService.removeById(id));
        }
        //PathVariable 就是从url里面接受参数
//
@GetMapping("/{id}")
public Result findOne(@PathVariable Integer id) {
        return Result.success(userService.getById(id));
        }
        //RequestParam就是从 ,{}对象式里面接受参数

//个人信息接口
@GetMapping("/username/{username}")
public Result findOne(@PathVariable String username) {
        //QueryWrapper默认就是User类型所以userService.getOne(queryWrapper)也是User类型
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",username);
        return Result.success(userService.getOne(queryWrapper));
        }

//分页查询接口(包括搜索接口)
@GetMapping("/page")
public Result findPage(@RequestParam Integer pageNum,
                       @RequestParam Integer pageSize,
                       @RequestParam(defaultValue = "") String username,
                       @RequestParam(defaultValue = "") String email,
                       @RequestParam(defaultValue = "") String address) {
        //参数里面如果不加defaultValue不传值就会报错加了就会置空
        IPage<User> page=new Page<>(pageNum,pageSize);
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        if (!"".equals(username)){
                queryWrapper.like("username",username);
        }
        if (!"".equals(email)){
                queryWrapper.like("email",email);
        }
        if (!"".equals(address)){
                queryWrapper.like("address",address);
        }  //这里必须判断是否是空值不然还是会like %%字符串去拼接就查不到数据
        /*如果要用or在queryWrapper里面可以加上*/
        queryWrapper.orderByDesc("id");
        /*给新增的数据倒序*/
        return Result.success(userService.page(page,queryWrapper));
        }

        //文件以excel文件导出  HttpServletResponse response是用来下载链接的对象
 //文件导出接口
@GetMapping("/export")
public void export(HttpServletResponse response) throws Exception {
        List<User> list=userService.list();
        ExcelWriter excelWriter= ExcelUtil.getWriter(true);
        //自定义标题别名 把数据库和excel对应的列匹配
        excelWriter.addHeaderAlias("username","用户名");
        excelWriter.addHeaderAlias("password","密码");
        excelWriter.addHeaderAlias("nickname","昵称");
        excelWriter.addHeaderAlias("email","邮箱");
        excelWriter.addHeaderAlias("phone","电话");
        excelWriter.addHeaderAlias("address","地址");
        excelWriter.addHeaderAlias("createTime","创建时间");
        excelWriter.addHeaderAlias("avatar","头像");
        //将list对象写入excel
        excelWriter.write(list,true);
        //设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName= URLEncoder.encode("用户信息","utf-8");
        response.setHeader("Content-Disposition","attachment;filename"+fileName+".xlsx");
       //创建输出的流
        ServletOutputStream out=response.getOutputStream();
        excelWriter.flush(out,true);
        out.close();
        excelWriter.close();
}

//文件导入接口
//MultipartFile file导入文件的集合类
@PostMapping("/import")
public Result imp(MultipartFile file) throws IOException {
        InputStream inputStream=file.getInputStream();
        ExcelReader reader=ExcelUtil.getReader(inputStream);
        //这里只能用遍历对象的方式去匹配Bean里面的字符串去把中文模板匹配转换为Bean的列字段
        List<List<Object>> list=reader.read(1);//读取上传对象的列字段
        List<User> users= CollUtil.newArrayList();
        for (List<Object> row:list)
        {
                User user=new User();
                user.setUsername(row.get(0).toString());
                user.setPassword(row.get(1).toString());
                user.setNickname(row.get(2).toString());
                user.setEmail(row.get(3).toString());
                user.setPhone(row.get(4).toString());
                user.setAddress(row.get(5).toString());
                user.setAvatar(row.get(6).toString());
                users.add(user);
        }
        /*List<User> list=reader.readAll(User.class);*/
        //将读取的excel文件转换为List对象再用mybatis plus导入saveBatch导入数据库
        userService.saveBatch(users);
        return Result.success(true);
}

}



