package com.jun.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jun.domain.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author makejava
 * @since 2023-10-15 19:50:11
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleKeyByUserId(Long id);

    //修改用户 根据id查询用户信息
    List<Long> selectRoleIdByUserId(Long userId);



}
