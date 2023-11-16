package com.jun.job.cron;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.jun.domain.entity.Article;
import com.jun.service.ArticleService;
import com.jun.utils.RedisCache;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 27164
 * @version 1.0
 * @description: TODO
 * @date 2023/10/12 20:57
 */
@Component
public class UpdateViewCountJob {

//    @Autowired
//    private ArticleService articleService;
//
//    @Autowired
//    private RedisCache redisCache;
//
//    @Scheduled(cron = "0/10 * * * * ?")
//    public void updateViewCount(){


//        //获取redis中浏览量
//        Map<String, Integer> viewCountMap = redisCache.getCacheMap("article:viewCount");
//
//        //转为 List<Article>
//        List<Article> articles =
//                viewCountMap.
//                        entrySet().
//                        stream()
//                        .map(entry -> new Article(Long.parseLong(entry.getKey()), entry.getValue().longValue()))
//                        .collect(Collectors.toList());
//
//        //将浏览量更新到mysql中  利用mybatis批量修改操作
//        articleService.updateBatchById(articles);
//
//        System.out.println("文章浏览量更新完成！！当前时间为:"+LocalDateTime.now());

//    }




    @Autowired
    //操作redis。RedisCache是我们在huanf-framework工程写的工具类
    private RedisCache redisCache;

    @Autowired
    //操作数据库。ArticleService是我们在huanf-framework工程写的接口
    private ArticleService articleService;

    //每隔3分钟，把redis的浏览量数据更新到mysql数据库
    @Scheduled(cron = "0/10 * * * * ?")
//    @Transactional
    public void updateViewCount(){
        //获取redis中的浏览量，注意得到的viewCountMap是HashMap双列集合
        Map<String, Integer> viewCountMap = redisCache.getCacheMap("article:viewCount");
        //让双列集合调用entrySet方法即可转为单列集合，然后才能调用stream方法
        List<Article> articles = viewCountMap.entrySet()
                .stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                //把最终数据转为List集合
                .collect(Collectors.toList());


        //把获取到的浏览量更新到mysql数据库中。updateBatchById是mybatisplus提供的批量操作数据的接口
//        articleService.updateBatchById(articles);

        for (Article article : articles) {
            LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Article :: getId, article.getId());
            updateWrapper.set(Article :: getViewCount, article.getViewCount());
            articleService.update(updateWrapper);
        }


        //方便在控制台看打印信息
        System.out.println("redis的文章浏览量数据已更新到数据库，现在的时间是: "+ LocalTime.now());
    }



}
