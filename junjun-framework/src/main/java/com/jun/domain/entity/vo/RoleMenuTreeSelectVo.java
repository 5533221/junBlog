package com.jun.domain.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author 35238
 * @date 2023/8/10 0010 16:29
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
//修改用户  回显菜单树
public class RoleMenuTreeSelectVo {

    //角色所关联的菜单权限列表
    private List<Long> checkedKeys;

    //菜单
    private List<MenuTreeVo> menus;
}