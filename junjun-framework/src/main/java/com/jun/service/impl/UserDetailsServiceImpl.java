package com.jun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jun.constants.SystemCanstants;
import com.jun.domain.LoginUser;
import com.jun.domain.entity.Menu;
import com.jun.domain.entity.User;
import com.jun.mapper.MenuMapper;
import com.jun.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author 27164
 * @version 1.0
 * @description: TODO
 * @date 2023/10/4 21:15
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MenuMapper menuMapper;

    @Override
    //在这里之前，我们已经拿到了登录的用户名和密码。UserDetails是SpringSecurity官方提供的接口
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        //根据拿到的用户名，并结合查询条件(数据库是否有这个用户名)，去查询用户信息
        queryWrapper.eq(User::getUserName,username);
        User user = userMapper.selectOne(queryWrapper);

        //判断是否查询到用户，也就是这个用户是否存在，如果没查到就抛出异常
        if(Objects.isNull(user)){
            throw new RuntimeException("用户不存在");
        }

        //TODO 查询权限信息，并封装
        //返回查询到的用户信息。注意下面那行直接返回user会报错，我们需要在huanf-framework工程的domain目录新
        //建LoginUser类，在LoginUser类实现UserDetails接口，然后下面那行就返回LoginUser对象

        //判断当前用户是否是后台  还是前台
        if(SystemCanstants.IS_ADMAIN.equals(user.getType())){

            //是后台id  查询权限关键字，即list集合
            List<String> list = menuMapper.selectPermsByOtherUserId(user.getId());
            return new LoginUser(user,list);
        }


        return new LoginUser(user,null);
    }
}
