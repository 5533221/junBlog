package com.jun.service;

import com.jun.domain.entity.User;
import com.jun.domain.result.ResponseResult;

/**
 * @author 27164
 * @version 1.0
 * @description: TODO
 * @date 2023/10/13 20:00
 */

public interface SystemLoginService {


    ResponseResult login(User user);

    ResponseResult loginOut();
}
