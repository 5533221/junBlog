package com.jun.controller;

import com.jun.annoation.SystemLog;
import com.jun.domain.entity.User;
import com.jun.domain.result.ResponseResult;
import com.jun.service.UserService;
import com.jun.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 27164
 * @version 1.0
 * @description: TODO
 * @date 2023/10/8 17:51
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/userInfo")
    public ResponseResult userinfo(){


        return userService.getUserInfo();
    }

    @PutMapping("/userInfo")
    @SystemLog(businessName = "更新用户信息")
    public ResponseResult UpdateUserinfo(@RequestBody User user){


        return userService.UpdateUserinfo(user);
    }

    @PostMapping("/register")
    @SystemLog(businessName = "注册用户")
    public ResponseResult register(@RequestBody User user){

        return userService.register(user);
    }


}
