package com.jun.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jun.domain.entity.Comment;
import com.jun.domain.result.ResponseResult;


/**
 * 评论表(SgComment)表服务接口
 *
 * @author makejava
 * @since 2023-10-07 14:36:00
 */
public interface CommentService extends IService<Comment> {

    ResponseResult getcommentList(String commentType, Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult sendComment(Comment comment);
}
