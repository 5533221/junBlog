package com.jun.controller;

import com.jun.domain.entity.Link;
import com.jun.domain.entity.vo.PageVo;
import com.jun.domain.result.ResponseResult;
import com.jun.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 27164
 * @version 1.0
 * @description: TODO
 * @date 2023/10/22 15:55
 */
@RestController
@RequestMapping("/content/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @GetMapping("/list")
    public ResponseResult getLinkListByPage( Link link,Integer pageSize,Integer pageNum){

        PageVo pageVo = linkService.selectLinkPage(link, pageNum, pageSize);
        return ResponseResult.okResult(pageVo);
    }

    @PostMapping
    public ResponseResult add(@RequestBody Link link){
        linkService.save(link);
        return ResponseResult.okResult();
    }


    @GetMapping(value = "/{id}")
    //①根据id查询友链
    public ResponseResult getInfo(@PathVariable(value = "id")Long id){
        Link link = linkService.getById(id);
        return ResponseResult.okResult(link);
    }
    @PutMapping("/changeLinkStatus")
    //②修改友链状态
    public ResponseResult changeLinkStatus(@RequestBody Link link){
        linkService.updateById(link);
        return ResponseResult.okResult();
    }

    @PutMapping
    //③修改友链
    public ResponseResult edit(@RequestBody Link link){
        linkService.updateById(link);
        return ResponseResult.okResult();
    }

    @DeleteMapping("/{id}")
    public ResponseResult delete(@PathVariable Long id){
        linkService.removeById(id);
        return ResponseResult.okResult();
    }


}
