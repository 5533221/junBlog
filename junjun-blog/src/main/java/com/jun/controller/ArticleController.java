package com.jun.controller;

import com.jun.domain.entity.Article;
import com.jun.domain.result.ResponseResult;
import com.jun.service.ArticleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 27164
 * @version 1.0
 * @description: TODO
 * @date 2023/9/30 21:57
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;



    //热门文章
    @GetMapping("/hotArticleList")
    public ResponseResult getHotArticle(){

       ResponseResult result= articleService.HotArticleList();

        return result;
    }

    //分页  文章列表
    @GetMapping("/articleList")
    public ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId){


        return articleService.articleList(pageNum,pageSize,categoryId);
    }

    //文章详情

    @GetMapping("/{id}")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id){



            return articleService.getArticleDetail(id);
    }



    //文章浏览量自增
    @PutMapping("/updateViewCount/{id}")
    public ResponseResult updateViewCount(@PathVariable("id") Long id){


        return articleService.updateViewCount(id);

    }







}
