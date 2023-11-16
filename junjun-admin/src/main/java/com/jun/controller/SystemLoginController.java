package com.jun.controller;

import com.jun.domain.LoginUser;
import com.jun.domain.entity.Menu;
import com.jun.domain.entity.User;
import com.jun.domain.entity.vo.AdminUserInfo;
import com.jun.domain.entity.vo.RoutersVo;
import com.jun.domain.entity.vo.UserInfoVo;
import com.jun.domain.result.ResponseResult;
import com.jun.enums.AppHttpCodeEnum;
import com.jun.exception.SystemException;
import com.jun.service.MenuService;
import com.jun.service.RoleService;
import com.jun.service.SystemLoginService;
import com.jun.utils.BeanCopyUtils;
import com.jun.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 27164
 * @version 1.0
 * @description: TODO
 * @date 2023/10/13 19:57
 */
@RestController
public class SystemLoginController {

    @Autowired
    private SystemLoginService systemLoginService;

    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;

    @PostMapping("/user/login")
    public ResponseResult AdminLogin(@RequestBody User user){

        if(!StringUtils.hasText(user.getUserName())){
            //提示 必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return systemLoginService.login(user);
    }

    //---------------------------查询(超级管理员|非超级管理员)的权限和角色信息-----------------------------
    @GetMapping("/getInfo")
    public ResponseResult<AdminUserInfo> getInfo(){

        //获取当前登录的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();

        //根据id查询权限信息
        List<String> perms = menuService.selectPermsByUserId(loginUser.getUser().getId());
        //根据用户id查询角色信息
        List<String> roleList=roleService.selectRoleKeyByUserId(loginUser.getUser().getId());;

        //获取用户信息
        User user = loginUser.getUser();
        //BeanCopyUtils是我们在com.jun.framework写的类
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);

//        封装并返回

        AdminUserInfo adminUserInfo = new AdminUserInfo(perms,roleList,userInfoVo);


        return ResponseResult.okResult(adminUserInfo);

    }

    //-------------------------------------查询路由信息(权限菜单)--------------------------------------

    @GetMapping("/getRouters")
    public ResponseResult<Menu> getRouters(){

        //获取用户id
        Long userId = SecurityUtils.getUserId();

        //根据id查询menu（权限菜单），要求查询结果是tree形式  父子菜单树
       List<Menu> menus= menuService.selectRouterMenuTreeByUserId(userId);

        return ResponseResult.okResult(new RoutersVo(menus));
    }


    //退出登录
    @PostMapping("/user/logout")
    public ResponseResult loginOut(){


        return systemLoginService.loginOut();
    }



}
