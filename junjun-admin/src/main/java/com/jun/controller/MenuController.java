package com.jun.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jun.domain.entity.Menu;
import com.jun.domain.entity.vo.MenuTreeVo;
import com.jun.domain.entity.vo.MenuVo;
import com.jun.domain.entity.vo.RoleMenuTreeSelectVo;
import com.jun.domain.result.ResponseResult;
import com.jun.enums.AppHttpCodeEnum;
import com.jun.exception.SystemException;
import com.jun.service.MenuService;
import com.jun.utils.BeanCopyUtils;
import com.jun.utils.SystemConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 27164
 * @version 1.0
 * @description: TODO
 * @date 2023/10/21 15:12
 */
@RestController
@RequestMapping
public class MenuController {

    @Autowired
    private MenuService menuService;

    //获取菜单列表
    @GetMapping("system/menu/list")
    public ResponseResult getMenuList(Menu menu){

        List<Menu> menuList = menuService.getMenuList(menu);

        //转换参数 成给前台
        List<MenuVo> menuVos = BeanCopyUtils.copyBeanList(menuList, MenuVo.class);

        return ResponseResult.okResult(menuVos);
    }



    //新增菜单
    @PostMapping("system/menu")
    public ResponseResult addMenu(@RequestBody  Menu menu){

        menuService.save(menu);

        return ResponseResult.okResult();
    }

    //修改菜单

    @GetMapping("system/menu/{menuId}")
    public ResponseResult getMenuById(@PathVariable(value = "menuId")Long menuId){


        return menuService.getMenuById(menuId);
    }
    @PutMapping("/system/menu")
    public ResponseResult UpdateMenu(@RequestBody Menu menu){

        if(menu.getId().equals(menu.getParentId())){

           return ResponseResult.errorResult(500,"修改菜单'" + menu.getMenuName() + "'失败，上级菜单不能选择自己");
        }

        menuService.updateById(menu);

        return ResponseResult.okResult();
    }

    //删除菜单
    @DeleteMapping("system/menu/{menuId}")
    public ResponseResult deleteMenuById(@PathVariable(value = "menuId")Long menuId){


        if(hasChildMenu(menuId)){

            return ResponseResult.errorResult(500,"存在子菜单不允许删除");
        }

        menuService.removeById(menuId);

        return ResponseResult.okResult();
    }

    //判断是否还有子元素
    //TODO   后面看视频 理解
    private boolean hasChildMenu(Long menuId) {

        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getParentId,menuId);

        return menuService.count(queryWrapper) != 0;
    }

    //----------------------------新增角色-获取菜单下拉树列表-------------------------------

    @GetMapping("system/menu//treeselect")
    public ResponseResult treeselect(){

        List<Menu> menuList =menuService.getMenuList(new Menu());

        List<MenuTreeVo> options =  SystemConverter.buildMenuSelectTree(menuList);
        return ResponseResult.okResult(options);

    }

    //----------------------修改角色-根据角色id查询对应的角色列表树

    //TODO  不理解
    @GetMapping("system/menu/roleMenuTreeselect/{roleId}")
    public ResponseResult getMenuTreeById(@PathVariable(value = "roleId")Long roleId){
        //
        List<Menu> menus = menuService.getMenuList(new Menu());
        List<Long> checkedKeys = menuService.selectMenuListByRoleId(roleId);
        List<MenuTreeVo> menuTreeVos = SystemConverter.buildMenuSelectTree(menus);

        RoleMenuTreeSelectVo treeSelectVo = new RoleMenuTreeSelectVo(checkedKeys, menuTreeVos);
        return ResponseResult.okResult(treeSelectVo);
    }

}
