package com.jun.domain.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 27164
 * @version 1.0
 * @description: TODO
 * @date 2023/10/19 20:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemCategoryVo {

    private Long id;
    private String name;
    private String description;

}
