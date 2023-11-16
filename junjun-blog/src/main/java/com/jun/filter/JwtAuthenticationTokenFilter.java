package com.jun.filter;

import com.alibaba.fastjson.JSON;
import com.jun.config.JwtUtil;
import com.jun.domain.LoginUser;
import com.jun.domain.result.ResponseResult;
import com.jun.enums.AppHttpCodeEnum;
import com.jun.utils.RedisCache;
import com.jun.utils.WebUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author 27164
 * @version 1.0
 * @description: TODO
 * @date 2023/10/6 16:46
 */
//博客前台的登录认证过滤器。OncePerRequestFilter是springsecurity提供的类
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {


    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
    //● 获取token，由于我们在把token存入Redis的时候加了前缀，所以获取的时候注意前缀

        String token=httpServletRequest.getHeader("token");
//        判断是否为空token
        if(!StringUtils.hasText(token)){
            //放行
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }

    //● 解析token获取其中的userid

        Claims claims=null;
        try {

            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(httpServletResponse,JSON.toJSONString(result));
            return;

        }
        //获取id
        String  userid = claims.getSubject();

        //● 从redis中获取用户信息
        LoginUser loginUser = redisCache.getCacheObject("bloglogin:"+userid);

        if(Objects.isNull(loginUser)){

            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(httpServletResponse,JSON.toJSONString(result));
            return;

        }

        //把从redis获取到的value，存入到SecurityContextHolder(Security官方提供的类)

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //放行
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
