package com.jun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jun.constants.SystemCanstants;
import com.jun.domain.entity.Article;
import com.jun.domain.entity.ArticleTag;
import com.jun.domain.entity.Category;
import com.jun.domain.entity.dto.AddArticleDto;
import com.jun.domain.entity.dto.UpdateArticleDto;
import com.jun.domain.entity.vo.ArticleDetailVo;
import com.jun.domain.entity.vo.ArticleListVo;
import com.jun.domain.entity.vo.PageVo;
import com.jun.domain.entity.vo.hotArticleVo;
import com.jun.domain.result.ResponseResult;
import com.jun.mapper.ArticleMapper;
import com.jun.service.ArticleService;
import com.jun.service.ArticleTagService;
import com.jun.service.CategoryService;
import com.jun.utils.BeanCopyUtils;
import com.jun.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * @author 27164
 * @version 1.0
 * @description: TODO
 * @date 2023/9/30 21:46
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {


    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private RedisCache redisCache;

    //热门文章列表
    @Override
    public ResponseResult HotArticleList() {


        //-------------------每调用这个方法就从redis查询文章的浏览量，展示在热门文章列表------------------------

//        //获取redis中的浏览量，注意得到的viewCountMap是HashMap双列集合
//        Map<String, Integer> viewCountMap = redisCache.getCacheMap("article:viewCount");
//        //让双列集合调用entrySet方法即可转为单列集合，然后才能调用stream方法
//        List<Article> xxarticles = viewCountMap.entrySet()
//                .stream()
//                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
//                //把最终数据转为List集合
//                .collect(Collectors.toList());
//        //把获取到的浏览量更新到mysql数据库中。updateBatchById是mybatisplus提供的批量操作数据的接口
//        articleService.updateBatchById(xxarticles);


        //---------------------------------------------------------

       LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<Article>();

        //状态是未删除得
        queryWrapper.eq(Article::getStatus, SystemCanstants.ARTICLE_STATUS_NORMAL);

        //浏览量  降序排列
        queryWrapper.orderByDesc(Article::getViewCount);

        //十条数据
        Page<Article> page = new Page<>(SystemCanstants.ARTICLE_STATUS_CURRENT,SystemCanstants.ARTICLE_STATUS_SIZE);
        page(page,queryWrapper);

        List<Article> articlelist = page.getRecords();

        System.out.println("*************************************"+articlelist+"*****************************************************");

        //拷贝
        List<hotArticleVo> hotArticleVos = BeanCopyUtils.copyBeanList(articlelist, hotArticleVo.class);

        return ResponseResult.okResult(hotArticleVos);






    }

    //分页查询文章
    @Override
    public ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId) {


        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        //判空。如果前端传了categoryId这个参数，那么查询时要和传入的相同。第二个参数是数据表的文章id，第三个字段是前端传来的文章id
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0,Article::getCategoryId,categoryId);

        //只查询状态是正式发布的文章。Article实体类的status字段跟0作比较，一样就表示是正式发布的
        lambdaQueryWrapper.eq(Article::getStatus,SystemCanstants.ARTICLE_STATUS_NORMAL);

        //对isTop字段进行降序排序，实现置顶的文章(isTop值为1)在最前面
        lambdaQueryWrapper.orderByDesc(Article::getIsTop);

        //分页查询
        Page<Article> page = new Page<>(pageNum,pageSize);
        page(page,lambdaQueryWrapper);

        /**
         * 解决categoryName字段没有返回值的问题。在分页之后，封装成ArticleListVo之前，进行处理。第一种方式，用for循环遍历的方式
         */
        ////用categoryId来查询categoryName(category表的name字段)，也就是查询'分类名称'
        //List<Article> articles = page.getRecords();
        //for (Article article : articles) {
        //    //'article.getCategoryId()'表示从article表获取category_id字段，然后作为查询category表的name字段
        //    Category category = categoryService.getById(article.getCategoryId());
        //    //把查询出来的category表的name字段值，也就是article，设置给Article实体类的categoryName成员变量
        //    article.setCategoryName(category.getName());
        //
        //}

        /**
         * 解决categoryName字段没有返回值的问题。在分页之后，封装成ArticleListVo之前，进行处理。第二种方式，用stream流的方式
         */
        //用categoryId来查询categoryName(category表的name字段)，也就是查询'分类名称'
        List<Article> articles = page.getRecords();
        articles.stream()
                .map(new Function<Article, Article>() {
                    @Override
                    public Article apply(Article article) {
                        //'article.getCategoryId()'表示从article表获取category_id字段，然后作为查询category表的name字段
                        Category category = categoryService.getById(article.getCategoryId());
                        String name = category.getName();
                        //把查询出来的category表的name字段值，也就是article，设置给Article实体类的categoryName成员变量
                        article.setCategoryName(name);
                        //把查询出来的category表的name字段值，也就是article，设置给Article实体类的categoryName成员变量
                        return article;
                    }
                })
                .collect(Collectors.toList());


        //把最后的查询结果封装成ArticleListVo(我们写的实体类)。BeanCopyUtils是我们写的工具类
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);


        //把上面那行的查询结果和文章总数封装在PageVo(我们写的实体类)
        PageVo pageVo = new PageVo(articleListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    //实现详情页面内容
    @Override
    public ResponseResult getArticleDetail(Long id) {

        //根据文章id  查询文章
        Article article = getById(id);

            //从redis中获取浏览数量
        Integer viewCount = redisCache.getCacheMapValue("article:viewCount", id.toString());
        article.setViewCount(viewCount.longValue());

        //将接收的数据封装成对象
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);

        //根据分类id，查询 category中 的分类名

        Category category = categoryService.getById(articleDetailVo.getCategoryId());

        if(category!=null){

            //根据查询的分类名称  给实体类进行封装数据  将目录名称赋值给文章
            articleDetailVo.setCategoryName(category.getName());
        }


        //返回
        return ResponseResult.okResult(articleDetailVo);
    }

    //修改浏览量
    @Override
    public ResponseResult updateViewCount(Long id) {

        redisCache.incrementCacheMapValue("article:viewCount",id.toString(), 1);

        return ResponseResult.okResult();
    }

    @Autowired
    private ArticleTagService articleTagService;

    //新增文章
    @Override
    @Transactional //添加事务注解  同步
    public ResponseResult addArticle(AddArticleDto addArticleDto) {

        //添加文章
        Article article = BeanCopyUtils.copyBean(addArticleDto, Article.class);
//        save(article);
        articleService.save(article);
        //添加关联  TODO 回来学习
        List<ArticleTag> tagList = addArticleDto.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());

        //添加关联
        articleTagService.saveBatch(tagList);


        return ResponseResult.okResult();
    }

    //获取已经发布的文章 列表
    @Override
    public ResponseResult getArticleListByPage(Article article,Integer pageNum, Integer pageSize) {

        //根据文章标题和摘要搜索
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(article.getTitle()),Article::getTitle,article.getTitle());
        queryWrapper.like(StringUtils.hasText(article.getSummary()),Article::getSummary,article.getSummary());

        //分页
        Page<Article> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,queryWrapper);
        //
        List<Article> articleList = page.getRecords();


        PageVo pageVo = new PageVo();
        pageVo.setTotal(page.getTotal());
        pageVo.setRows(articleList);

        return ResponseResult.okResult(pageVo);
    }

    //修改文章
    //TODO  未搞懂  回来学习
    @Override
    public ResponseResult edit(UpdateArticleDto updateArticleDto) {

        Article article = BeanCopyUtils.copyBean(updateArticleDto, Article.class);
        //更新博客信息
        updateById(article);

        //删除原有的 标签和博客的关联
        LambdaQueryWrapper<ArticleTag> articleTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
        articleTagLambdaQueryWrapper.eq(ArticleTag::getArticleId,article.getId());
        articleTagService.remove(articleTagLambdaQueryWrapper);


        //添加新的博客和标签的关联信息
        //TODO 学完后理解
        List<ArticleTag> articleTags = updateArticleDto.getTags().stream()
                .map(tagId -> new ArticleTag(updateArticleDto.getId(), tagId))
                .collect(Collectors.toList());
        articleTagService.saveBatch(articleTags);

        return ResponseResult.okResult();
    }
}
