package com.jun.handler;

import com.alibaba.fastjson.JSON;
import com.jun.domain.result.ResponseResult;
import com.jun.enums.AppHttpCodeEnum;
import com.jun.utils.WebUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 27164
 * @version 1.0
 * @description: TODO
 * @date 2023/10/6 20:39
 */
//自定义认证失败的处理器
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {



    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

        //输出异常信息
        e.printStackTrace();

        ResponseResult result=null;
        //判断是登录密码错误 还是 无权限的错误
        if(e instanceof BadCredentialsException){
            //登录错误 505 密码或用户名
            result = ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR.getCode(), e.getMessage());

        }else if(e instanceof InsufficientAuthenticationException){
            //需要登录后操作 401
            result=ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);

        }else{

            result=ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR,"认证或授权错误！");
        }
        //使用spring提供的JSON工具类，把上一行的result转换成JSON，然后响应给前端。WebUtils是我们写的类
        WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));


    }
}
