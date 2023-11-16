package com.jun.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jun.domain.entity.Article;
import com.jun.domain.entity.ArticleTag;
import com.jun.domain.entity.dto.AddArticleDto;
import com.jun.domain.entity.dto.UpdateArticleDto;
import com.jun.domain.entity.vo.ArticleByIdVo;
import com.jun.domain.result.ResponseResult;
import com.jun.service.ArticleService;
import com.jun.service.ArticleTagService;
import com.jun.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 27164
 * @version 1.0
 * @description: TODO
 * @date 2023/10/19 23:20
 */
@RestController
@RequestMapping
public class ArticleController {


    //新增文章
    @Autowired
    private ArticleService articleService;

    @PostMapping("/content/article")
    @PreAuthorize("@ps.hasPermission('content:article:writer')")//权限控制
    public ResponseResult addArticle(@RequestBody AddArticleDto addArticleDto){


        return articleService.addArticle(addArticleDto);
    }


    //查询后台 已经发布的文章
    /*
    *
    * @Param pageNum: 页码
             pageSize: 每页条数
             title：文章标题
            summary：文章摘要
    * */
    @GetMapping("/content/article/list")
    public ResponseResult getArticleListByPage(Article article,Integer pageNum, Integer pageSize){



        return articleService.getArticleListByPage(article,pageNum,pageSize);
    }

    //删除文章
    @DeleteMapping("content/article/{id}")
    public ResponseResult removeArticleByid(@PathVariable(value = "id")Long id){

        articleService.removeById(id);

        return ResponseResult.okResult();
    }


    //文章标签类
    @Autowired
    private ArticleTagService articleTagService;


    //修改文章
    @GetMapping("content/article/{id}")
    public ResponseResult getArticleByid(@PathVariable(value = "id")Long id){

        Article article = articleService.getById(id);

        //获取关联
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId,article.getId());

        //根据文章id 获取对应的标签
        //TODO 学完回来理解
        List<ArticleTag> articleTags = articleTagService.list(queryWrapper);
        List<Long> tagIds = articleTags.stream()
                .map(articleId -> articleId.getTagId()).collect(Collectors.toList());

        ArticleByIdVo articleByIdVo = BeanCopyUtils.copyBean(article, ArticleByIdVo.class);

        //给查询出来的文章设置标签
        articleByIdVo.setTags(tagIds);

        return ResponseResult.okResult(articleByIdVo);
    }

    @PutMapping("content/article")
    public ResponseResult UpdateArticle(@RequestBody UpdateArticleDto updateArticleDto){

        return  articleService.edit(updateArticleDto);
    }






}
