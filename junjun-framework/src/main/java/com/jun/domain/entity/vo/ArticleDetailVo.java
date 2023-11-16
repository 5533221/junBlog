package com.jun.domain.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 27164
 * @version 1.0
 * @description: TODO
 * @date 2023/10/4 16:10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDetailVo {

    private Long id;
    //标题
    private String title;
    //文章摘要
    private String summary;

    //分类id  新增
    private  Long categoryId;
    //内容   新增  详情内容
    private String content;

    //所属分类名
    private String categoryName;
    //缩略图
    private String thumbnail;
    //访问量
    private Long viewCount;
    //创建时间
    private Date createTime;


}
