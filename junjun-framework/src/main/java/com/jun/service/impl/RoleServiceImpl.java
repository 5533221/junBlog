package com.jun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jun.constants.SystemCanstants;
import com.jun.domain.entity.RoleMenu;
import com.jun.domain.entity.Role;
import com.jun.domain.entity.vo.PageVo;
import com.jun.domain.result.ResponseResult;
import com.jun.mapper.RoleMapper;
import com.jun.service.RoleMenuService;
import com.jun.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2023-10-15 19:50:12
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {

        //判断如果是管理员  返回集合中需要有admin
        if(id==1L){

            List<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }
        
        //否则查询用户所具有的角色信息
        List<String> otherRole = getBaseMapper().selectRoleKeyByUserId(id);
        return otherRole;
    }

    //------------------------获取角色列表
    @Override
    public ResponseResult getRoleList(Integer pageSize, Integer pageNum, Role role) {

        //搜索
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(role.getRoleName()),Role::getRoleName,role.getRoleName());
        queryWrapper.like(StringUtils.hasText(role.getStatus()),Role::getStatus,role.getStatus());

        Page<Role> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,queryWrapper);

        List<Role> roleList = page.getRecords();

        PageVo pageVo = new PageVo(roleList,page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    // -----------新增角色-------------------

    @Override
    //TODO 查询角色  看视频
    public void insertRole(Role role) {

        save(role);

        if(role.getMenuIds()!=null &&role.getMenuIds().length>0){

            insertRoleMenu(role);
        }
    }



    @Autowired
    private RoleMenuService roleMenuService;

    private void insertRoleMenu(Role role) {

        List<RoleMenu> roleMenus = Arrays.stream(role.getMenuIds())
                .map(memuId -> new RoleMenu(role.getId(), memuId))
                .collect(Collectors.toList());

        roleMenuService.saveBatch(roleMenus);
    }

    //修改角色-保存修改好的角色
    @Override
    public void updateRole(Role role) {

        updateById(role);
        roleMenuService.deleteRoleMenuByRoleId(role.getId());
        insertRoleMenu(role);

    }

    //新增用户---获取所有状态为正常的角色列表
    @Override
    public  List<Role> selectAllRole() {

//        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(Role::getStatus,"0");
//        List<Role> roleList = list(queryWrapper);

        return list(Wrappers.<Role>lambdaQuery().eq(Role::getStatus, SystemCanstants.NORMAL));
    }

    //修改用户 根据id查询用户信息
    @Override
    public List<Long> selectRoleIdByUserId(Long userId) {

        return getBaseMapper().selectRoleIdByUserId(userId);
    }
}
