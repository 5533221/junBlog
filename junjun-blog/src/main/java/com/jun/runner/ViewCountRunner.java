package com.jun.runner;

import com.jun.domain.entity.Article;
import com.jun.mapper.ArticleMapper;
import com.jun.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 27164
 * @version 1.0
 * @description: TODO
 * @date 2023/10/12 19:40
 */
@Component
public class ViewCountRunner implements CommandLineRunner {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisCache redisCache;


    //启动时  会执行此方法
    @Override
    public void run(String... args) throws Exception {

        //查询数据库中的博客信息，注意只需要查询id、viewCount字段的博客信息
        List<Article> articles = articleMapper.selectList(null);

        //查询数据库中的博客信息，注意只需要查询id、viewCount字段的博客信息
        Map<String, Integer> viewCountMap = articles.stream()
                .collect(Collectors.toMap(article -> article.getId().toString(), article -> article.getViewCount().intValue()));

        //将文章id为key，浏览量为value  ,map存入redis
        redisCache.setCacheMap("article:viewCount",viewCountMap);
    }
}
