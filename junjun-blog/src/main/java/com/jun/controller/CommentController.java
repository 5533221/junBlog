package com.jun.controller;

import com.jun.constants.SystemCanstants;
import com.jun.domain.entity.Comment;
import com.jun.domain.result.ResponseResult;
import com.jun.enums.AppHttpCodeEnum;
import com.jun.exception.SystemException;
import com.jun.service.CommentService;
import com.jun.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 27164
 * @version 1.0
 * @description: TODO
 * @date 2023/10/7 14:40
 */
@RestController
@RequestMapping("/comment")
@Api(tags = "评论的相关接口",description = "")
public class CommentController {

        @Autowired
        private CommentService commentService;

        @GetMapping("/commentList")
        public ResponseResult commentList(Long articleId,Integer pageNum,Integer pageSize){

                //articleId:文章id
                //pageNum:页码
                //pageSize:每页条数
//                if(pageNum.equals(2)){
//                        if(SecurityUtils.getLoginUser().getUser()!=null){
//
//                                return commentService.getcommentList(SystemCanstants.ARTICLE_COMMENT, articleId,pageNum,pageSize);
//                        }else{
//
//                                throw new SystemException(AppHttpCodeEnum.NOUSER_OPER);
//                        }
//                }
                return commentService.getcommentList(SystemCanstants.ARTICLE_COMMENT, articleId,pageNum,pageSize);
        }


        //发送评论
        @PostMapping
        public ResponseResult sendComment(@RequestBody Comment comment){

                if(SecurityUtils.getLoginUser().getUser()!=null){

                        return commentService.sendComment(comment);
                }else{

                        throw new SystemException(AppHttpCodeEnum.NEED_LOGIN);
                }


        }

        //友链评论
        @GetMapping("/linkCommentList")
        @ApiOperation(value = "友链评论列表",notes = "获取所有的友链评论")
        @ApiImplicitParams({
                @ApiImplicitParam(name = "pageNum",value = "页码"),
                @ApiImplicitParam(name = "pageSize",value = "一页有多少")
        })
        public  ResponseResult linkCommentList(Integer pageNum, Integer pageSize){

                return commentService.getcommentList(SystemCanstants.LINK_COMMENT,null,pageNum,pageSize);

        }


}
