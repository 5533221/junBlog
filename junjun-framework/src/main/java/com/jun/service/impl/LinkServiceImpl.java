package com.jun.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jun.constants.SystemCanstants;
import com.jun.domain.entity.Link;
import com.jun.domain.entity.vo.LinkVo;
import com.jun.domain.entity.vo.PageVo;
import com.jun.domain.result.ResponseResult;
import com.jun.mapper.LinkMapper;
import com.jun.service.LinkService;
import com.jun.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * 友链(SgLink)表服务实现类
 *
 * @author makejava
 * @since 2023-10-04 16:45:20
 */
@Service("LinkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {



    @Override
    public ResponseResult getAllLink() {
        //查询所有状态为0的友链
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemCanstants.LINK_STATUS_NORMAL);

        List<Link> linkList = list(queryWrapper);

        //封装 LinkVo
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(linkList, LinkVo.class);

        return ResponseResult.okResult(linkVos);
    }

    @Override
    public PageVo selectLinkPage(Link link, Integer pageNum, Integer pageSize) {


        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(StringUtils.hasText(link.getName()),Link::getName, link.getName());
        queryWrapper.eq(Objects.nonNull(link.getStatus()),Link::getStatus, link.getStatus());

        Page<Link> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,queryWrapper);

        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());


        return pageVo;
    }
}
