package com.jun.controller;

import com.jun.domain.entity.Role;
import com.jun.domain.entity.User;
import com.jun.domain.entity.vo.UserInfoAndRoleVo;
import com.jun.domain.result.ResponseResult;
import com.jun.enums.AppHttpCodeEnum;
import com.jun.exception.SystemException;
import com.jun.service.RoleService;
import com.jun.service.UserService;
import com.jun.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 27164
 * @version 1.0
 * @description: TODO
 * @date 2023/10/22 13:36
 */
@RestController
@RequestMapping
public class UserController {


    @Autowired
    private UserService userService;


    //分页查询 用户  可以手机号 状态 用户名 模糊查询
    @GetMapping("system/user/list")
    public ResponseResult getUserList(User user, Integer pageNum, Integer pageSize){


        return  userService.getUserByPage(user,pageNum,pageSize);

    }


    //新增用户
    @PostMapping("system/user")
    public ResponseResult addUser(@RequestBody User user){

        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        if (!userService.checkUserNameUnique(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if (!userService.checkPhoneUnique(user)){
            throw new SystemException(AppHttpCodeEnum.PHONENUMBER_EXIST);
        }
        if (!userService.checkEmailUnique(user)){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }

        return userService.addUser(user);
    }


    //删除用户
    @DeleteMapping("system/user/{userIds}")
    public ResponseResult deleteUserByIds(@PathVariable(value = "userIds") List<Long> userIds){

        if(userIds.contains(SecurityUtils.getUserId())){
            return ResponseResult.errorResult(500,"不能删除当前使用的用户");
        }

        userService.removeByIds(userIds);

        return ResponseResult.okResult();
    }

    @Autowired
    private RoleService roleService;

    //修改用户①  获取用户信息
    @GetMapping("system/user/{userId}")
    public ResponseResult getInfoAndRoleIds(@PathVariable(value = "userId")Long userId){

        List<Role> roleList = roleService.selectAllRole();

        User user = userService.getById(userId);
        List<Long> rolesId = roleService.selectRoleIdByUserId(userId);

        UserInfoAndRoleVo userInfoAndRoleVo = new UserInfoAndRoleVo(user, roleList, rolesId);

        return ResponseResult.okResult(userInfoAndRoleVo);
    }



    //修改用户②
    @PutMapping("system/user")
    public ResponseResult editUser(@RequestBody User user){

        userService.updateUser(user);
        return ResponseResult.okResult();
    }



}
