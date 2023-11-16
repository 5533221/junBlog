package com.jun.domain.entity.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 27164
 * @version 1.0
 * @description: TODO
 * @date 2023/10/20 16:30
 * 后台  查询文章列表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryListVo {


    private Long id;

    //分类名
    private String name;
    //父分类id，如果没有父分类为-1
    private Long pid;
    //描述
    private String description;
    //状态0:正常,1禁用
    private String status;

    private Integer delFlag;


}
