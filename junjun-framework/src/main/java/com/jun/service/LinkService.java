package com.jun.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jun.domain.entity.Link;
import com.jun.domain.entity.vo.PageVo;
import com.jun.domain.result.ResponseResult;

import java.util.List;


/**
 * 友链(SgLink)表服务接口
 *
 * @author makejava
 * @since 2023-10-04 16:45:20
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();

    //分页查询友链
    PageVo selectLinkPage(Link link,Integer pageNum,Integer pageSize);
}
