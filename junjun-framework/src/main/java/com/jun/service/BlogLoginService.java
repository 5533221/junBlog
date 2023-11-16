package com.jun.service;

import com.jun.domain.entity.User;
import com.jun.domain.result.ResponseResult;


public interface BlogLoginService {

    ResponseResult login(User user);


    ResponseResult logout();
}
