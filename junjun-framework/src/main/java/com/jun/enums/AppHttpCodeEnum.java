package com.jun.enums;

/**
 * @author 27164
 * @version 1.0
 * @description: TODO
 * @date 2023/9/30 23:50
 */
public enum AppHttpCodeEnum {

    // 成功
    SUCCESS(200,"操作成功"),
    // 登录
    NEED_LOGIN(401,"需要登录后操作"),
    NO_OPERATOR_AUTH(403,"无权限操作"),
    SYSTEM_ERROR(500,"出现错误"),
    USERNAME_EXIST(501,"用户名已存在"),
    PHONENUMBER_EXIST(502,"手机号已存在"), EMAIL_EXIST(503, "邮箱已存在"),
    REQUIRE_USERNAME(504, "必需填写用户名"),
    LOGIN_ERROR(505,"用户名或密码错误"),
    COMMENT_NOTNULL(506,"评论不能为空" ),
    NOUSER_OPER(518,"当前为无用户" ),
    FILE_TYPE_ERROR(507,"图片必须需要png/jpg格式" ),
    USERNAME_NOT_NULL(509,"用户名不能为空" )
    ,EMAIL_NOT_NULL(510,"邮箱不能为空" )
    ,NICKNAME_NOT_NULL(511,"昵称不能为空" )
    ,PASSWORD_NOT_NULL(512,"密码不能为空" ),
    FILE_FAIL(300,"文件上传失败" );

    int code;
    String msg;

    AppHttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


}
