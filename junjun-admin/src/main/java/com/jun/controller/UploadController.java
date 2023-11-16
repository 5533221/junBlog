package com.jun.controller;

import com.jun.domain.result.ResponseResult;
import com.jun.enums.AppHttpCodeEnum;
import com.jun.exception.SystemException;
import com.jun.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;

/**
 * @author 27164
 * @version 1.0
 * @description: TODO
 * @date 2023/10/19 22:09
 */
@RestController
public class UploadController {


    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    public ResponseResult uploadImg(@RequestParam("img")MultipartFile multipartFile){

            try{
             return uploadService.uploadImg(multipartFile);
            }catch (Exception e){
                e.printStackTrace();
                //文件上传失败
                throw  new SystemException(AppHttpCodeEnum.FILE_FAIL);
            }

//        return ResponseResult.okResult();
    }

}
