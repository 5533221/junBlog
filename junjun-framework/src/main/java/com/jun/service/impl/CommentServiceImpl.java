package com.jun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jun.constants.SystemCanstants;
import com.jun.domain.entity.Comment;
import com.jun.domain.entity.vo.CommentVo;
import com.jun.domain.entity.vo.PageVo;
import com.jun.domain.result.ResponseResult;
import com.jun.enums.AppHttpCodeEnum;
import com.jun.exception.SystemException;
import com.jun.mapper.CommentMapper;
import com.jun.service.CommentService;
import com.jun.service.UserService;
import com.jun.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 评论表(SgComment)表服务实现类
 *
 * @author makejava
 * @since 2023-10-07 14:36:00
 */
@Service("CommentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {


    @Autowired
    private UserService userService;

    @Override
    public ResponseResult getcommentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
    //  不考虑子评论  只有根评论

        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        //对Article文章id进行判断 得到指定id的文章
        //判断是否有 文章id 还是友链
        queryWrapper.eq(SystemCanstants.ARTICLE_COMMENT.equals(commentType),Comment::getArticleId,articleId);

        //文章为根 评论root_id 为-1
        queryWrapper.eq(Comment::getRootId,-1);

        //判断评论的类型(文章还是友链)
        queryWrapper.eq(Comment::getType,commentType);

        //分页
        Page<Comment> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);

        List<CommentVo> commentVoList = xxtoCommentList(page.getRecords());


        //查询子评论
        // 遍历(可以用for循环，也可以用stream流)。查询子评论(注意子评论只查到二级评论，不再往深查)

        for (CommentVo commentVo : commentVoList) {

            //获取根评论的 评论id    跟子评论的rootid相比较
          List<CommentVo> children= getChildren(commentVo.getId());
          commentVo.setChildren(children);
        }


        return ResponseResult.okResult(new PageVo(commentVoList,page.getTotal()));


    }

    //发送评论
    @Override
    public ResponseResult sendComment(Comment comment) {

        if(!StringUtils.hasText(comment.getContent())){
               //不能为空
            throw new SystemException(AppHttpCodeEnum.COMMENT_NOTNULL);
        }
        save(comment);
        return ResponseResult.okResult();
    }

    //根据根id 查询子评论
    private List<CommentVo> getChildren(Long id) {

        //
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId,id);

        //对子评论进行创建时间的先后排序
        queryWrapper.orderByAsc(Comment::getCreateTime);

        List<Comment> comments = list(queryWrapper);

        //调用下面方法  得到toCommentUserName和 Username
        List<CommentVo> commentVos = xxtoCommentList(comments);

        return commentVos;
    }


    private List<CommentVo> xxtoCommentList(List<Comment> comments){

        //对查询的数据进行vo封装
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(comments, CommentVo.class);

        for (CommentVo comment : commentVos) {

            //通过创建人字段id  查询 用户昵称
            String nickName = userService.getById(comment.getCreateBy()).getNickName();
//            然后把nickname字段(发这条子评论的用户昵称)的数据赋值给commentVo类的username字段
            comment.setUsername(nickName);


            //查询根评论的用户昵称。怎么判断是根评论的用户呢，判断getToCommentUserId为1，就表示这条评论是根评论
            if(comment.getToCommentUserId() != -1){

                //如果不是根评论-->将
                String toCommentUserName = userService.getById(comment.getToCommentUserId()).getNickName();
                //然后把nickname字段(发这条根评论的用户昵称)的数据赋值给commentVo类的toCommentUserName字段
                comment.setToCommentUserName(toCommentUserName);

            }
        }
        return commentVos;

    }
}
