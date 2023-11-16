package com.jun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jun.domain.entity.Article;
import com.jun.domain.entity.Category;
import com.jun.domain.entity.dto.AddCategoryDto;
import com.jun.domain.entity.vo.CategoryListVo;
import com.jun.domain.entity.vo.PageVo;
import com.jun.domain.entity.vo.SystemCategoryVo;
import com.jun.domain.entity.vo.categoryVo;
import com.jun.domain.result.ResponseResult;
import com.jun.mapper.CategoryMapper;
import com.jun.service.ArticleService;
import com.jun.service.CategoryService;
import com.jun.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2023-10-01 11:25:09
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    //实现文章列表的显示
    @Autowired
    private ArticleService articleService;



    @Override
    public ResponseResult getCategoryList() {


        //要求查的是getStatus字段的值为0，注意SystemCanstants是我们写的一个常量类，用来解决字面值的书写问题
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus,0);
        List<Article> articleList = articleService.list(queryWrapper);

        //利用流 id去重
        Set<Long> longSet = articleList.stream()
                .map(new Function<Article, Long>() {
                    @Override
                    public Long apply(Article article) {
                        return article.getCategoryId();
                    }
                })
                .collect(Collectors.toSet());
        //查询分类表  根据id
        List<Category> categories = listByIds(longSet);

         categories.stream()
                .filter(category -> "0".equals(category.getStatus()))
                .collect(Collectors.toList());
        //封装vo
        List<categoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, categoryVo.class);

        return ResponseResult.okResult(categoryVos);
    }
//-------------- 写博文   查询文章列表分类  状态正常的   --------------------------
    @Override
    public ResponseResult getArticleCategory() {


        //查询正常状态的category
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getStatus,0);

        //查询
        List<Category> categoryList = list(queryWrapper);
        List<SystemCategoryVo> systemCategoryVos = BeanCopyUtils.copyBeanList(categoryList, SystemCategoryVo.class);

        return ResponseResult.okResult(systemCategoryVos);
    }

    //-------------写博文 分页查询分类列表------------------
    @Override
    public ResponseResult getCategoryByPage(Integer pageSize, Integer pageNum) {

        LambdaQueryWrapper<Category> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getStatus,0);

        Page<Category> page = new Page<>();
        page.setSize(pageSize);
        page.setCurrent(pageNum);
        page(page,queryWrapper);

        List<Category> records = page.getRecords();

        List<CategoryListVo> categoryListVos = BeanCopyUtils.copyBeanList(records, CategoryListVo.class);

        PageVo pageVo = new PageVo(categoryListVos,page.getTotal());

        return ResponseResult.okResult(pageVo);
    }


    //------------------写博文  新增 目录

    @Override
    public ResponseResult AddCategory(AddCategoryDto addCategoryDto) {

        //转为Category
        Category category = BeanCopyUtils.copyBean(addCategoryDto, Category.class);

        save(category);

        return ResponseResult.okResult();
    }


}
