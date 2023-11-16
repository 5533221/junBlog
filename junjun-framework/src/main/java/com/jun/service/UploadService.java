package com.jun.service;

import com.jun.domain.result.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

    //上传文件
    ResponseResult uploadImg(MultipartFile img);

}
