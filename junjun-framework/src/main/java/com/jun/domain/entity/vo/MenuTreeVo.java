package com.jun.domain.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author 27164
 * @version 1.0
 * @description: TODO
 * @date 2023/10/21 20:39
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
//新增角色-获取菜单下拉树列表
public class MenuTreeVo {

    private static final long serialVersionUID = 1L;
    //节点ID
    private Long id;
    //节点名称
    private String label;

    private Long parentId;

    //子节点
    private List<MenuTreeVo> children;

}
