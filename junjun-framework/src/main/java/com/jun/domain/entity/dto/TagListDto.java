package com.jun.domain.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author 27164
 * @version 1.0
 * @description: TODO
 * @date 2023/10/19 17:25
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagListDto {

    private String name;

    private String remark;

}
