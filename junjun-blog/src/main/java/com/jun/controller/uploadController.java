package com.jun.controller;

import com.jun.domain.result.ResponseResult;
import com.jun.enums.AppHttpCodeEnum;
import com.jun.exception.SystemException;
import com.jun.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 27164
 * @version 1.0
 * @description: TODO
 * @date 2023/10/9 14:19
 */
@RestController
public class uploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    //巨坑  img  参数名字必须和前端一致
    public ResponseResult  uploadImg(MultipartFile img){

        return uploadService.uploadImg(img);
    }


}
