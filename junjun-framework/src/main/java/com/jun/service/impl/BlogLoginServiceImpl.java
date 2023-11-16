package com.jun.service.impl;

import com.jun.config.JwtUtil;
import com.jun.domain.LoginUser;
import com.jun.domain.entity.BlogUserLoginVo;
import com.jun.domain.entity.User;
import com.jun.domain.entity.vo.UserInfoVo;
import com.jun.domain.result.ResponseResult;
import com.jun.enums.AppHttpCodeEnum;
import com.jun.exception.SystemException;
import com.jun.service.BlogLoginService;
import com.jun.utils.BeanCopyUtils;
import com.jun.utils.RedisCache;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author 27164
 * @version 1.0
 * @description: TODO
 * @date 2023/10/4 21:28
 */
@Service
public class BlogLoginServiceImpl implements BlogLoginService {

    @Autowired
    //AuthenticationManager是security官方提供的接口
    private AuthenticationManager authenticationManager;

    @Autowired
    //RedisCache是我们在huanf-framework工程的config目录写的类
    private RedisCache redisCache;


    @Override
    public ResponseResult login(User user) {
        //封装登录的用户名和密码
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        //在下一行之前，封装的数据会先走UserDetailsServiceImpl实现类，这个实现类在我们的huanf-framework工程的service/impl目录里面
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //上面那一行会得到所有的认证用户信息authenticate。然后下一行需要判断用户认证是否通过，如果authenticate的值是null，就说明认证没有通过
        if(Objects.isNull(authenticate)){
                //用户名或密码错误
            throw new SystemException(AppHttpCodeEnum.LOGIN_ERROR);
        }
        //获取userid
        LoginUser loginUser= (LoginUser) authenticate.getPrincipal();
        String userId = String.valueOf(loginUser.getUser().getId());
        //把这个userid通过我们写的JwtUtil工具类转成密文，这个密文就是token值
        String jwt = JwtUtil.createJWT(userId);

        //下面那行的第一个参数: 把上面那行的jwt，也就是token值保存到Redis。存到时候是键值对的形式，值就是jwt，key要加上 "bloglogin:" 前缀
        //下面那行的第二个参数: 要把哪个对象存入Redis。我们写的是loginUser，里面有权限信息，后面会用到
        redisCache.setCacheObject("bloglogin:"+userId,loginUser);

        //把User转化为UserInfoVo，再放入vo对象的第二个参数
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);

        BlogUserLoginVo blogUserLoginVo = new BlogUserLoginVo(jwt, userInfoVo);
        //封装响应返回
        return ResponseResult.okResult(blogUserLoginVo);
    }

    //退出登录
    @Override
    public ResponseResult logout() {

        //获取SecurityContextHolder中的id
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        //获取用户id
        String userid = String.valueOf(loginUser.getUser().getId());

        //删除redis中的id
        redisCache.deleteObject("bloglogin"+userid);


        return ResponseResult.okResult(200,"注销成功");
    }
}
