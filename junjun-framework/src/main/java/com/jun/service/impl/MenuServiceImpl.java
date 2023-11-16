package com.jun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.jun.constants.SystemCanstants;
import com.jun.domain.entity.Menu;

import com.jun.domain.entity.vo.MenuVo;
import com.jun.domain.result.ResponseResult;
import com.jun.mapper.MenuMapper;
import com.jun.service.MenuService;
import com.jun.utils.BeanCopyUtils;
import com.jun.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 35238
 * @date 2023/8/4 0004 13:25
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Override
    //查询用户的权限信息
    public List<String> selectPermsByUserId(Long id) {
        //根据用户id查询用户的权限信息。用户id为id代表管理员，如果是管理员就返回所有的权限
        if(id == 1L){
            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
            //查询条件是permissions中需要有所有菜单类型为C或者F的权限。SystemCanstants是我们在huanf-framework工程写的类
            wrapper.in(Menu::getMenuType, "C","F");
            //查询条件是permissions中需要有状态为正常的权限。SystemCanstants是我们在huanf-framework工程写的类
            wrapper.eq(Menu::getStatus, SystemCanstants.STATUS_NORMAL);
            //查询条件是permissions中需要未被删除的权限的权限
            List<Menu> menus = list(wrapper);
            List<String> perms = menus.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
            return perms;
        }

        //如果不是管理员就返回对应用户所具有的权限
        List<String> otherPerms = getBaseMapper().selectPermsByOtherUserId(id);
        return otherPerms;
    }

    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Long userId) {

        MenuMapper mapper = getBaseMapper();

        List<Menu> menus=null;
        //判断是否管理员
        if(SecurityUtils.isAdmin()){

            menus= mapper.selectAllRouterMenu();
            //返回所有菜单
        }else{

            //根据id查询菜单
            menus= mapper.selectMenuByUserId(userId);

        }

          List<Menu> menuTree= builderMenuTree(menus,0L);

        //封装为树形

        return menuTree;
    }

    //-------------------后台 获取菜单列表------------------------------
    @Override
    public  List<Menu> getMenuList(Menu menu) {

        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(menu.getMenuName()),Menu::getMenuName,menu.getMenuName());
        queryWrapper.like(StringUtils.hasText(menu.getStatus()),Menu::getStatus,menu.getStatus());
        queryWrapper.orderByAsc(Menu::getParentId,Menu::getOrderNum);
        List<Menu> menus = list(queryWrapper);

        return menus;
    }

    //-----------------------------获取单个菜单
    @Override
    public ResponseResult getMenuById(Long menuId) {

        Menu menu = getById(menuId);

        return ResponseResult.okResult(menu);
    }




    //转为树形  赋值 children
     private List<Menu> builderMenuTree(List<Menu> menus, Long parentId) {

         List<Menu> list = menus.stream()
                 .filter(menu -> menu.getParentId().equals(parentId))
                 .map(menu -> menu.setChildren(getChildren(menu, menus)))
                 .collect(Collectors.toList());

         return list;
    }

    //将数据库查询出的children  赋值给 过滤出来的    menu的id 和 查询出来的menu 的parentId 相同
    private List<Menu> getChildren(Menu menu, List<Menu> menus) {

        List<Menu> menuList = menus.stream()
                .filter(m -> m.getParentId().equals(menu.getId()))
                .map(m -> m.setChildren(getChildren(m, menus)))
                .collect(Collectors.toList());

        return  menuList;
    }



    //-----------------------修改角色  根据角色id查询对应角色菜单列表树

    @Override
    public List<Long> selectMenuListByRoleId(Long roleId) {

        return getBaseMapper().selectMenuListByRoleId(roleId);
    }


}