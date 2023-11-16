package com.jun.exception;

import com.jun.domain.result.ResponseResult;
import com.jun.enums.AppHttpCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 27164
 * @version 1.0
 * @description: TODO
 * @date 2023/10/6 21:41
 */
//全局异常处理。最终都会在这个类进行处理异常
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExceptionHandler(SystemException e){
        //打印异常信息
      log.error("出现了异常",e);

      //从异常对象中获取提示信息封装，然后返回

        return ResponseResult.errorResult(e.getCode(),e.getMsg());
    }
    @ExceptionHandler(Exception.class)
    public ResponseResult systemExceptionHandler(Exception e){
        //打印异常信息
        log.error("出现了异常",e);

        //从异常对象中获取提示信息封装，然后返回

        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR,e.getMessage());
    }


}
