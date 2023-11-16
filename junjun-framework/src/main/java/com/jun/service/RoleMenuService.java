package com.jun.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jun.domain.entity.RoleMenu;


/**
 * 角色和菜单关联表(SysRoleMenu)表服务接口
 *
 * @author makejava
 * @since 2023-10-21 20:43:07
 */
public interface RoleMenuService extends IService<RoleMenu> {

    //修改角色-保存修改好的角色信息
    void deleteRoleMenuByRoleId(Long id);



}
