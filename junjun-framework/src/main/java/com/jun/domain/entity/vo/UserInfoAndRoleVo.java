package com.jun.domain.entity.vo;

import com.jun.domain.entity.Role;
import com.jun.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 27164
 * @version 1.0
 * @description: TODO
 * @date 2023/10/22 14:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoAndRoleVo {

    private User user;
    private List<Role> roles;
    private List<Long> roleIds;

}
