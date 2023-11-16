package com.jun.exception;

import com.jun.enums.AppHttpCodeEnum;

/**
 * @author 27164
 * @version 1.0
 * @description: TODO
 * @date 2023/10/6 21:38
 */
//统一异常类
public class SystemException extends RuntimeException{

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
    //定义一个构造方法
    public SystemException(AppHttpCodeEnum appHttpCodeEnum){
        super(appHttpCodeEnum.getMsg());
        this.code=appHttpCodeEnum.getCode();
        this.msg=appHttpCodeEnum.getMsg();
    }

}
