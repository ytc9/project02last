package com.example.project02last.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.project02last.entity.User;
import org.apache.ibatis.annotations.*;
import java.util.List;



// @Mapper 是通过xml文件里面namespace来找到dao层 类似于@Repository("User")
// 和mybatis映射是一样的功能 这里我们用了mybatis-plus里面 @MapperScan
//
public interface UserMapper extends BaseMapper<User> {
  /*
    @Select("select * from user")
    List<User> findAll();

    @Insert("insert into user(username,password,nickname,email,phone,address) values (#{username},#{password}," +
            "#{nickname},#{email},#{phone},#{address})")
    int insert(User usr);

    //这里用mybatis xml实现动态sql
    int update(User user);

    //这里param是让spring框架能读取到deleteId 的别名id不是url
    @Delete("delete from user where id=#{id}")
    Integer deleteId(@Param("id") Integer id);


    //concat拼接函数实际为like %#{username}%用于分页模糊查询

    @Select("select * from user where username like concat('%',#{username},'%') limit #{pageNum},#{pageSize}")
    List<User> selectPage(Integer pageNum, Integer pageSize, String username);

    @Select("select count(*) from user where username like concat('%',#{username},'%')")//返回数据的总条数
    Integer selectTotal(String username);
    */
}
