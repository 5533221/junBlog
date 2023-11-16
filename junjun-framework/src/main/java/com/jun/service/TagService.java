package com.jun.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jun.domain.entity.Tag;
import com.jun.domain.entity.dto.TagListDto;
import com.jun.domain.entity.vo.addTagVo;
import com.jun.domain.result.ResponseResult;


/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2023-10-13 15:00:24
 */
public interface TagService extends IService<Tag> {

    ResponseResult getTagByPage(Integer pageSize, Integer pageNum, TagListDto tagListDto);

    ResponseResult addTag( addTagVo addTagvo );


    ResponseResult deleteByid(Long id);

    ResponseResult getTagList();
}
