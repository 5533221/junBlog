package com.jun.controller;

import com.jun.domain.result.ResponseResult;
import com.jun.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 27164
 * @version 1.0
 * @description: TODO
 * @date 2023/10/4 16:42
 */
@RestController
@RequestMapping("/link")
public class LinkController {

    @Autowired
    private LinkService linkservice;

    @GetMapping("/getAllLink")
    public ResponseResult getAllLink(){

           return linkservice.getAllLink();
    }


}
