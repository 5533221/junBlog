package com.jun.controller;

import com.jun.domain.result.ResponseResult;
import com.jun.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 27164
 * @version 1.0
 * @description: TODO
 * @date 2023/10/3 19:53
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //获取文章列表
    @GetMapping("/getCategoryList")
    public ResponseResult getCategoryList(){


        return categoryService.getCategoryList();
    }




}
