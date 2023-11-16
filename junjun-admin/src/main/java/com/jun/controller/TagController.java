package com.jun.controller;

import com.jun.domain.entity.Tag;
import com.jun.domain.entity.dto.EditTagDto;
import com.jun.domain.entity.dto.TagListDto;
import com.jun.domain.entity.vo.addTagVo;
import com.jun.domain.result.ResponseResult;
import com.jun.service.TagService;
import com.jun.utils.BeanCopyUtils;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 27164
 * @version 1.0
 * @description: TODO
 * @date 2023/10/13 15:03
 */
@RestController
@RequestMapping("/content/tag")
public class TagController {

    @Autowired
    private TagService tagService;

//    @RequestMapping("/list")
//    public ResponseResult getlist(){
//
//        return ResponseResult.okResult(tagService.list());
//    }
        @RequestMapping("/list")
    public ResponseResult getTagList(Integer pageSize, Integer pageNum, TagListDto tagListDto){

        return tagService.getTagByPage(pageSize,pageNum,tagListDto);
    }

   @PostMapping
    public ResponseResult addTag(@RequestBody addTagVo addTagvo ){

        return tagService.addTag(addTagvo);
    }

    //---------------------------------删除标签--------------------------------------
    @DeleteMapping("/{id}")
    public ResponseResult DeleteTagById(@PathVariable("id") Long id){


          return   tagService.deleteByid(id);

    }
    //---------------------------修改标签 ------------------------------------------------
    // 第一步  根据用户id获取用户信息
    @GetMapping("/{id}")
    public ResponseResult getInfoByid(@PathVariable(value = "id") Long id) {

        Tag tag = tagService.getById(id);

        return  ResponseResult.okResult(tag);
    }
    //第二步  修改标签信息
    //TODO 回家测试
    @PutMapping()
    public ResponseResult updateTag(@RequestBody EditTagDto editTagDto){

        Tag tag = BeanCopyUtils.copyBean(editTagDto, Tag.class);
        tagService.updateById(tag);

        return ResponseResult.okResult();
    }
    //查询   写博文的标签列表
    @GetMapping("/listAllTag")
    public ResponseResult getTagList(){


         return tagService.getTagList();
    }


}
