package com.jun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jun.domain.entity.User;
import com.jun.domain.entity.UserRole;
import com.jun.domain.entity.vo.PageVo;
import com.jun.domain.entity.vo.UserInfoVo;
import com.jun.domain.entity.vo.UserVo;
import com.jun.domain.result.ResponseResult;
import com.jun.enums.AppHttpCodeEnum;
import com.jun.exception.SystemException;
import com.jun.mapper.UserMapper;
import com.jun.service.UserRoleService;
import com.jun.service.UserService;
import com.jun.utils.BeanCopyUtils;
import com.jun.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2023-10-07 14:34:10
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseResult getUserInfo() {

        //获取id
        Long id = SecurityUtils.getUserId();

        //获取用户
        User user = getById(id);

        //封装为userinfoVo
        UserInfoVo vo = BeanCopyUtils.copyBean(user, UserInfoVo.class);

        return ResponseResult.okResult(vo);
    }


    //修改用户信息
    @Override
    public ResponseResult UpdateUserinfo(User user) {

        updateById(user);

        return ResponseResult.okResult();
    }

    //注册功能
    @Override
    public ResponseResult register(User user) {

        //判断是否为空
        if(!StringUtils.hasText(user.getUserName())){

            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getNickName())){

            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getEmail())){

            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getPassword())){

            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }

        //判断信息是否存在
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,user.getUserName());
        int userCount = count(queryWrapper);
        if(userCount<0){

            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }

        //密码加密
        String passwordByEncoder = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordByEncoder);

        //保存数据库
        save(user);

        return ResponseResult.okResult();
    }


    //查询所有用户
    @Override
    public ResponseResult getUserByPage(User user, Integer pageNum, Integer pageSize) {

        //模糊查询  手机号  状态 用户名
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(user.getUserName()),User::getUserName,user.getUserName());
        queryWrapper.like(StringUtils.hasText(user.getStatus()),User::getStatus,user.getStatus());
        queryWrapper.like(StringUtils.hasText(user.getPhonenumber()),User::getPhonenumber,user.getPhonenumber());


        //分页

        Page<User> page = new Page<>();
        page.setSize(pageSize);
        page.setCurrent(pageNum);
        page(page,queryWrapper);

        List<User> userList = page.getRecords();
        List<UserVo> userVos = BeanCopyUtils.copyBeanList(userList, UserVo.class);

        PageVo vo = new PageVo(userVos, page.getPages());

        return ResponseResult.okResult(vo);
    }

    /*
        新增用户
    * 检验 手机 邮箱 用户名 是否存在数据库
    *
    *
    * */

    @Override
    public boolean checkUserNameUnique(String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,userName);
        int i = count(queryWrapper);
        return i==0;
    }

    @Override
    public boolean checkPhoneUnique(User user) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhonenumber,user.getPhonenumber());
        int i = count(queryWrapper);
        return i==0;
    }

    @Override
    public boolean checkEmailUnique(User user) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail,user.getEmail());
        int i = count(queryWrapper);
        return i==0;
    }

    @Override
    public ResponseResult addUser(User user) {

        //加密密码
        String newPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(newPassword);

        //保存用户
        save(user);

        //说明前端选择了角色
        if(user.getRoleIds()!=null &&user.getRoleIds().length>0){
            insertUserRole(user);
        }

        //添加角色

        return ResponseResult.okResult();
    }



    @Autowired
    private UserRoleService userRoleService;

    private void insertUserRole(User user) {

        //将用户id和角色id 关联
        List<UserRole> userRoles = Arrays.stream(user.getRoleIds())
                .map(roleId -> new UserRole(user.getId(), roleId))
                .collect(Collectors.toList());

        userRoleService.saveBatch(userRoles);
    }



    //修改用户
    @Override
    @Transactional //添加事务
    public void updateUser(User user) {

        //删除角色和用户的关联
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId,user.getId());
        userRoleService.remove(queryWrapper);

        //新增用户与角色关联
        insertUserRole(user);

        //更新用户信息
        updateById(user);

    }




}
