package com.jun.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jun.domain.entity.Role;
import com.jun.domain.result.ResponseResult;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2023-10-15 19:50:12
 */
public interface RoleService extends IService<Role> {

    //查询用户的角色信息
    List<String> selectRoleKeyByUserId(Long userId);

    //查询角色列表
    ResponseResult getRoleList(Integer pageSize, Integer pageNum, Role role);


    //新增角色
    void insertRole(Role role);

    //修改角色-保存修改好的角色
    void updateRole(Role role);

    List<Role> selectAllRole();


    //修改用户 根据id查询用户信息
    List<Long> selectRoleIdByUserId(Long userId);

}
