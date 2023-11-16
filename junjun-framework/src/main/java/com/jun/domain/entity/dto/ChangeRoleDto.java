package com.jun.domain.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 27164
 * @version 1.0
 * @description: TODO
 * @date 2023/10/21 16:26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeRoleDto {

    private Long roleId;

    private  String status;
}
