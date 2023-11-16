package com.jun.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.jun.domain.entity.Category;
import com.jun.domain.entity.dto.AddCategoryDto;
import com.jun.domain.entity.vo.ExcelCategoryVo;
import com.jun.domain.result.ResponseResult;
import com.jun.enums.AppHttpCodeEnum;
import com.jun.service.CategoryService;
import com.jun.utils.BeanCopyUtils;
import com.jun.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author 27164
 * @version 1.0
 * @description: TODO
 * @date 2023/10/19 20:19
 */
@RestController
@RequestMapping("/content/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //----------------------------写博文 ，查询文章分类的接口---------
    @GetMapping("/listAllCategory")
    public ResponseResult getArticleCategory(){


        return categoryService.getArticleCategory();
    }

//    -------------写博文  查询文章列表----------------------------
    @GetMapping("/list")
    public ResponseResult getCategoryListByPage(Integer pageSize, Integer pageNum){


        return categoryService.getCategoryByPage(pageSize,pageNum);
    }

//-----------------写博文  新增分类------------------------------
    @PostMapping
    public ResponseResult AddCategory(@RequestBody AddCategoryDto addCategoryDto){

        return categoryService.AddCategory(addCategoryDto);
    }

//----------------写博文  删除分类--------------------------------
    @DeleteMapping("/{id}")
    public ResponseResult DeleteCategory(@PathVariable(value = "id") Long id){

        categoryService.removeById(id);

        return ResponseResult.okResult();
    }

// -------------------修改分类------------------------------
    @GetMapping("/{id}")
    public ResponseResult getCategoryById(@PathVariable(value = "id") Long id){

        Category category = categoryService.getById(id);
        return ResponseResult.okResult(category);
    }

    @PutMapping
    public ResponseResult UpdateCategoryById(@RequestBody Category category){

        categoryService.updateById(category);

        return ResponseResult.okResult();
    }

//------------导出为excel表格
//权限控制ps是PermissionService类的bean名称
    //判断是否有该权限
    @PreAuthorize("@ps.hasPermission('content:category:export')")
    @GetMapping("/export")
    //注意返回值类型是void
    public void exportExcel_category(HttpServletResponse response){


        try {
            //设置下载文件的请求头，下载下来的excel文件叫  分类.xlsx
            WebUtils.setDownLoadHeader("分类.xlsx",response);
            //获取需要导入的数据
            List<Category> categories = categoryService.list();

            //将数据库获取的信息封装成ExcelCategoryVo
            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyBeanList(categories, ExcelCategoryVo.class);

            //把数据写入到Excel中，也就是把ExcelCategoryVo实体类的字段作为Excel表格的列头
            //sheet方法里面的字符串是Excel表格左下角工作簿的名字
            EasyExcel.write(response.getOutputStream(),ExcelCategoryVo.class)
                    .autoCloseStream(Boolean.FALSE)
                    .sheet("文章分类")
                    .doWrite(excelCategoryVos);


        } catch (Exception e) {
            e.printStackTrace();

            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            //将错误信息返回个前端页面
            WebUtils.renderString(response, JSON.toJSONString(result));

        }


    }



}
