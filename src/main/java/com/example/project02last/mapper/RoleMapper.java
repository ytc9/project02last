package com.example.project02last.mapper;

import com.example.project02last.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 杨添辰
 * @since 2022-04-25
 */
public interface RoleMapper extends BaseMapper<Role> {

    @Select("select id from role where flag=#{flag}")
    Integer selectByFlag(@Param("flag") String flag);
}
