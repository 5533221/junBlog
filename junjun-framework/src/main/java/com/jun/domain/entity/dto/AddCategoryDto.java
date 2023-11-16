package com.jun.domain.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 27164
 * @version 1.0
 * @description: TODO
 * @date 2023/10/20 16:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddCategoryDto {


    //分类名
    private String name;
    //描述
    private String description;
    //状态0:正常,1禁用
    private String status;

}
