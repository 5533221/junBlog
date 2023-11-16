package com.jun.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jun.domain.entity.Link;
import com.jun.domain.entity.Menu;
import com.jun.domain.entity.vo.MenuVo;
import com.jun.domain.result.ResponseResult;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2023-10-15 19:49:51
 */
public interface MenuService  extends IService<Menu>{

    List<String> selectPermsByUserId(Long id);

    List<Menu> selectRouterMenuTreeByUserId(Long userId);

    List<Menu> getMenuList(Menu menu);

    ResponseResult getMenuById(Long menuId);

    //修改角色 根据id查询对应角色菜单列表树
    List<Long> selectMenuListByRoleId(Long roleId);

}
