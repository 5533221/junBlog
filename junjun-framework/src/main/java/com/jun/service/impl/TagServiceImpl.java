package com.jun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jun.domain.entity.Tag;
import com.jun.domain.entity.dto.TagListDto;
import com.jun.domain.entity.vo.PageVo;
import com.jun.domain.entity.vo.TagVo;
import com.jun.domain.entity.vo.addTagVo;
import com.jun.domain.result.ResponseResult;
import com.jun.mapper.TagMapper;
import com.jun.service.TagService;

import com.jun.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2023-10-13 15:00:24
 */
@Service()
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    private TagService service;

    //获取标签  根据标签名搜索
    @Override
    public ResponseResult<PageVo> getTagByPage(Integer pageSize, Integer pageNum, TagListDto tagListDto) {

        //
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(tagListDto.getName()),Tag::getName,tagListDto.getName());
        queryWrapper.like(StringUtils.hasText(tagListDto.getRemark()),Tag::getRemark,tagListDto.getRemark());

        Page<Tag> page = new Page();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,queryWrapper);

        //封装为 pageVo
        PageVo pageVo = new PageVo(page.getRecords(),page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    //添加标签
    @Override
    public ResponseResult addTag( addTagVo addTagvo ) {

        //将 vo参数 封装为 Tag
        Tag tag = BeanCopyUtils.copyBean(addTagvo, Tag.class);

        //调用数据库 保存tag
        service.save(tag);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteByid(Long id) {


        service.removeById(id);

        return  ResponseResult.okResult();
    }

    //查询  写博文的 标签列表
    @Override
    public ResponseResult getTagList() {

        //   只查询 id 和 name
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Tag::getId,Tag::getName);

        List<Tag> list = list(wrapper);
        //
        List<TagVo> tagVoList = BeanCopyUtils.copyBeanList(list, TagVo.class);

        return ResponseResult.okResult(tagVoList);
    }


}
