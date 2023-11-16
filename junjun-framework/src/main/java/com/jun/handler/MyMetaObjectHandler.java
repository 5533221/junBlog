package com.jun.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.jun.enums.AppHttpCodeEnum;
import com.jun.exception.SystemException;
import com.jun.utils.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;


//这个类是用来配置mybatis的字段自动填充。用于'发送评论'功能，由于我们在评论表无法对下面这四个字段进行插入数据(原因是前端在发送评论时，没有在
//请求体提供下面四个参数，所以后端在往数据库插入数据时，下面四个字段是空值)，所有就需要这个类来帮助我们往下面这四个字段自动的插入值，
//只要我们更新了评论表的字段，那么无法插入值的字段就自动有值了
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    //只要对数据库执行了插入语句，那么就会执行到这个方法
    public void insertFill(MetaObject metaObject) {
        Long userId = null;
       try {
            //获取用户id
            userId = SecurityUtils.getUserId();
        } catch (Exception e) {
            e.printStackTrace();
//            throw new SystemException(AppHttpCodeEnum.NOUSER_OPER);
            userId = -1L;//如果异常了，就说明该用户还没注册，我们就把该用户的userid字段赋值d为-1
        }
        //自动把下面四个字段新增了值。
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("createBy",userId , metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("updateBy", userId, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName(" ", SecurityUtils.getUserId(), metaObject);
    }
}