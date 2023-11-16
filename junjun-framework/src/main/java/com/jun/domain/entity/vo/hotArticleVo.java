package com.jun.domain.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 27164
 * @version 1.0
 * @description: TODO
 * @date 2023/10/1 10:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class hotArticleVo {

    private Long id;
    //标题
    private String title;
    //访问量
    private Long viewCount;

    //所属分类id
    private Long categoryId;

}
