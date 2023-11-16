package com.jun.domain.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 27164
 * @version 1.0
 * @description: TODO  查询写博文的标签
 * @date 2023/10/19 22:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagVo {

    private Long id;
    private String name;

}
