package com.jun.domain.entity.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 27164
 * @version 1.0
 * @description: TODO 修改文章的 接收前端传来的参数
 * @date 2023/10/21 14:27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateArticleDto {

    @TableId
    private Long id;
    //标题
    private String title;
    //文章内容
    private String content;
    //文章摘要
    private String summary;
    //所属分类id
    private Long categoryId;

    //缩略图
    private String thumbnail;
    //是否置顶（0否，1是）
    private String isTop;
    //状态（0已发布，1草稿）
    private String status;
    //访问量
    private Long viewCount;
    //是否允许评论 1是，0否
    private String isComment;

    private List<Long> tags;


}
