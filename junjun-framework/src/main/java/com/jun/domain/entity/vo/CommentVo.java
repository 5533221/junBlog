package com.jun.domain.entity.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author 27164
 * @version 1.0
 * @description: TODO
 * @date 2023/10/7 14:58
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentVo {

    @TableId
    private Long id;

    //文章id
    private Long articleId;
    //根评论id
    private Long rootId;
    //评论内容
    private String content;

    //所回复的目标评论的userid
    private Long toCommentUserId;

    //所回复的目标评论的用户名
    private String toCommentUserName;

    //回复目标评论id
    private Long toCommentId;

    private Long createBy;

    private Date createTime;

    //评论是谁发的
    private String username;

    //子评论
    private List<CommentVo> children;


}
