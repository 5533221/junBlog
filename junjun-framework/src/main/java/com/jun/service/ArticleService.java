package com.jun.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jun.domain.entity.Article;
import com.jun.domain.entity.dto.AddArticleDto;
import com.jun.domain.entity.dto.UpdateArticleDto;
import com.jun.domain.result.ResponseResult;

public interface ArticleService extends IService<Article> {


    ResponseResult HotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult addArticle(AddArticleDto addArticleDto);


    ResponseResult getArticleListByPage(Article article,Integer pageNum, Integer pageSize);



    ResponseResult edit(UpdateArticleDto updateArticleDto);
}


