package com.jun.handler;

import com.alibaba.fastjson.JSON;
import com.jun.domain.result.ResponseResult;
import com.jun.enums.AppHttpCodeEnum;
import com.jun.utils.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 27164
 * @version 1.0
 * @description: TODO
 * @date 2023/10/6 20:46
 */


@Component
//自定义授权失败的处理器
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {

        //输出错误信息
        e.printStackTrace();

        //无权限操作 403
        ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
        //使用spring提供的JSON工具类，把上一行的result转换成JSON，然后响应给前端。WebUtils是我们写的类
        WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));


    }
}
