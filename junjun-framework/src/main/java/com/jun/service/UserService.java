package com.jun.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jun.domain.entity.User;
import com.jun.domain.result.ResponseResult;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2023-10-07 14:34:10
 */
public interface UserService extends IService<User> {

    ResponseResult getUserInfo();

    ResponseResult UpdateUserinfo(User user);

    ResponseResult register(User user);

    ResponseResult getUserByPage(User user, Integer pageNum, Integer pageSize);

    boolean checkUserNameUnique(String userName);

    boolean checkPhoneUnique(User user);

    boolean checkEmailUnique(User user);

    ResponseResult addUser(User user);

    //修改用户
    void updateUser(User user);
}
