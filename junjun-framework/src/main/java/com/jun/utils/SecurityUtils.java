package com.jun.utils;


import com.jun.domain.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


//在'发送评论'功能那里会用到的工具类
public class SecurityUtils {

    /**
     * 获取用户
     **/
    public static LoginUser getLoginUser() {

        Authentication authentication = getAuthentication();
        return (LoginUser) authentication.getPrincipal();

    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {

        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 指定userid为1的用户就是网站管理员
     * @return
     */
    public static Boolean isAdmin(){
        Long id = getLoginUser().getUser().getId();
        return id != null && id.equals(1L);
    }

    /***
     * 获取用户的id
     *
     * */
    public static Long getUserId() {


        return getLoginUser().getUser().getId();
    }


}
