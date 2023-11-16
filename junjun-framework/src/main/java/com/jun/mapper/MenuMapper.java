package com.jun.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jun.domain.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 35238
 * @date 2023/8/4 0004 13:23
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    //查询普通用户的权限信息
    List<String> selectPermsByOtherUserId(Long userId);

    //查询超级管理员的所有菜单
    List<Menu> selectAllRouterMenu();

    //查询普通用户的菜单
    List<Menu> selectMenuByUserId(Long userId);

    //修改角色 根据id查询对应角色菜单列表树
    List<Long> selectMenuListByRoleId(Long roleId);
}