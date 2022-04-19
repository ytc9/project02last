package com.example.project02last.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.project02last.entity.User;
import com.example.project02last.mapper.UserMapper;
import com.example.project02last.service.UserService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController  /*定义成Controller 必须要有这个才能用GetMapping去映射*/
@RequestMapping("/user")//就是在url地址栏后写/user才能访问后台接口
public class UserController {
    /*Autowired是 接收映射mapper文件的*/
    /*配置响应*/
    @Autowired
    private UserService userService;

    //查询所有数据
    @GetMapping
    public List<User> findAll(){
        return userService.list();//mybatis-plus提供的方法
    }
    //新增接口就是post接口 RequestBody就是把前台穿过来json的数据变成User对象
    //通过service判断是否新增和修改
    //Integer和int区别就是可以穿0参数 添加删除和修改都可以是Integer或int返回类型 返回0为失败 返回1为成功

    //新增和修改接口
    @PostMapping
    public boolean save(@RequestBody User user){
        return userService.saveUser(user);
    }

    //删除接口
    //PathVariable是去读取前端params从地址栏传过来的参数
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id){
       return userService.removeById(id);
    }

    //批量删除接口
    @PostMapping("/del/batch") //Delet接口没办法从前端传纯数组所以要用post接口
    public boolean deleteBatch(@RequestBody List<Integer> ids){  //[1,2,3]
        return userService.removeByIds(ids);
    }

   /*
   分页查询 路径：/user/page?pageNum=1?pageSize=10
   @RequestParam接受url params参数 limit 参数1(pageNum-1)*pageSize

   @GetMapping("/page")
    public Map<String,Object> findPage(@RequestParam Integer pageNum,
                                       @RequestParam Integer pageSize,
                                       @RequestParam String username
    ){
        pageNum=(pageNum-1)*pageSize;//第一个参数为定位页数
        List<User> data=userMapper.selectPage(pageNum,pageSize,username);

        Integer total =userMapper.selectTotal(username);
        Map<String,Object> res=new HashMap<>();//把页数和查询页面的数据传入map然后返回类型转换为map
        res.put("data",data);
        res.put("total",total);
        return  res;
    }*/

    //mybatis-plus版分页查询
    @GetMapping("/page")
    public IPage<User> findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                @RequestParam(defaultValue = "") String username,
                                @RequestParam(defaultValue = "") String email,
                                @RequestParam(defaultValue = "") String address
    ){//参数里面如果不加defaultValue不传值就会报错加了就会置空
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
        return userService.page(page,queryWrapper);
    }
}
