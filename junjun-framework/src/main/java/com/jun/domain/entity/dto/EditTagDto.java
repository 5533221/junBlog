package com.jun.domain.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 27164
 * @version 1.0
 * @description: TODO  修改标签信息
 * @date 2023/10/19 19:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditTagDto {


    private Long id;

    private String name;

    private String remark;
}
