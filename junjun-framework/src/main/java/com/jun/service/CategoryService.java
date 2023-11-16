package com.jun.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jun.domain.entity.Category;
import com.jun.domain.entity.dto.AddCategoryDto;
import com.jun.domain.result.ResponseResult;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2023-10-01 11:25:09
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();

    ResponseResult getArticleCategory();

    ResponseResult getCategoryByPage(Integer pageSize, Integer pageNum);

    ResponseResult AddCategory(AddCategoryDto addCategoryDto);
}
