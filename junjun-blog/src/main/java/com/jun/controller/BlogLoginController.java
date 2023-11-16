package com.jun.controller;


import com.jun.domain.LoginUser;
import com.jun.domain.entity.User;
import com.jun.domain.result.ResponseResult;
import com.jun.enums.AppHttpCodeEnum;
import com.jun.exception.SystemException;
import com.jun.service.BlogLoginService;
import com.jun.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author 35238
 * @date 2023/7/22 0022 21:31
 */
@RestController
public class BlogLoginController {

    @Autowired
    //BlogLoginService是我们在service目录写的接口
    private BlogLoginService blogLoginService;

    @Autowired
    private RedisCache redisCache;

    @PostMapping("/login")
    //ResponseResult是我们在huanf-framework工程里面写的实体类
    public ResponseResult login(@RequestBody User user){

        if(!StringUtils.hasText(user.getUserName())){
             //必须填写用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);

        }

        return blogLoginService.login(user);
    }

    @PostMapping("/logout")
    public ResponseResult logout(){


            return blogLoginService.logout();
    }

}