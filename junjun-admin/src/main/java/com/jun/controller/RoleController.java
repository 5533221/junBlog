package com.jun.controller;

import com.jun.domain.entity.Role;
import com.jun.domain.entity.dto.ChangeRoleDto;
import com.jun.domain.result.ResponseResult;
import com.jun.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 27164
 * @version 1.0
 * @description: TODO
 * @date 2023/10/21 16:04
 */
@RestController
@RequestMapping
public class RoleController {

    @Autowired
    private RoleService roleService;

    //查询角色列表  可以根据角色名称状态查询 分页查询
    @GetMapping("system/role/list")
    public ResponseResult getRoleList(Integer pageSize, Integer pageNum, Role role){

        return roleService.getRoleList(pageSize,pageNum,role);
    }

    @PutMapping("system/role/changeStatus")
    public ResponseResult changRoleStatus(@RequestBody ChangeRoleDto changeRoleDto){

        Role role = new Role();
        role.setId(changeRoleDto.getRoleId());
        role.setStatus(changeRoleDto.getStatus());

        boolean b = roleService.updateById(role);

        return ResponseResult.okResult();
    }


    @PostMapping("system/role")
    public ResponseResult add( @RequestBody Role role) {
        roleService.insertRole(role);
        return ResponseResult.okResult();

    }

    //-------根据 id查询角色
    @GetMapping("system/role/{roleId}")
    public ResponseResult getRolebyid(@PathVariable(value = "roleId")Long roleId){

        Role role = roleService.getById(roleId);

        return ResponseResult.okResult(role);
    }

    //修改用户
   @PutMapping("system/role")
    public ResponseResult updateRole(@RequestBody Role role){

        roleService.updateRole(role);

        return ResponseResult.okResult();
    }

    //删除用户
    @DeleteMapping("system/role/{id}")
    public ResponseResult deleteRole(@PathVariable(value = "id")Long id){
        roleService.removeById(id);
        return ResponseResult.okResult();
    }

    //新增用户  ①查询所有状态为正常的角色列表接口
    @GetMapping("system/role/listAllRole")
    public ResponseResult getAllRoleList(){

        List<Role> roleList = roleService.selectAllRole();
        return ResponseResult.okResult(roleList);
    }




}
