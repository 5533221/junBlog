package com.jun.service.impl;

import com.jun.config.JwtUtil;
import com.jun.domain.LoginUser;
import com.jun.domain.entity.User;
import com.jun.domain.result.ResponseResult;
import com.jun.service.SystemLoginService;
import com.jun.utils.RedisCache;
import com.jun.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

/**
 * @author 27164
 * @version 1.0
 * @description: TODO
 * @date 2023/10/13 20:01
 */
@Service
public class SystemLoginServiceImpl implements SystemLoginService {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {

        //调用providerManager方法进行认证  认证通过生成jwt  用户信息存入redis
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());

        Authentication authenticate = authenticationManager.authenticate(authenticationToken);


        //上面那一行会得到所有的认证用户信息authenticate。然后下一行需要判断用户认证是否通过，如果authenticate的值是null，就说明认证没有通过
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }

        //获取用户id
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String id = loginUser.getUser().getId().toString();

        //生成jwt
        String jwt = JwtUtil.createJWT(id);

        //后台存入redis
        redisCache.setCacheObject("adminlogin:"+id,loginUser);

        HashMap<String, String> map = new HashMap<>();
        map.put("token",jwt);

        //返回map集合  token  ： “”
        return ResponseResult.okResult(map);
    }

    //后台退出登录
    @Override
    public ResponseResult loginOut() {

        //获取当前用户的id
        Long id = SecurityUtils.getUserId();

        //删除存在redis中的用户信息
        redisCache.deleteObject("login:"+id);

        return ResponseResult.okResult();
    }
}
