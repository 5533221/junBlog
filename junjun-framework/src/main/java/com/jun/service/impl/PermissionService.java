package com.jun.service.impl;

import com.jun.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 27164
 * @version 1.0
 * @description: TODO
 * @date 2023/10/20 18:04
 */
@Service("ps")
public class PermissionService {

   public Boolean hasPermission(String permission){

       //判断是否是管理员  如果是管理员  则返回true
       if(SecurityUtils.isAdmin()){

               return true;
       }
       //否则获取当前用户的权限
       List<String> permissions = SecurityUtils.getLoginUser().getPermissions();

       //判断权限中是否包含 所传的参数
       return permissions.contains(permission);
   }


}
