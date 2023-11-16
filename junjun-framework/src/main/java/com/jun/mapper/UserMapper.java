package com.jun.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jun.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;


/**
 * 用户表(User)表数据库访问层
 *
 * @author makejava
 * @since 2023-10-04 21:08:01
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
