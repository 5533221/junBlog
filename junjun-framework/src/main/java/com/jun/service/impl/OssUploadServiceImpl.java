package com.jun.service.impl;

import com.google.gson.Gson;
import com.jun.domain.result.ResponseResult;
import com.jun.service.UploadService;
import com.jun.utils.PathUtils;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author 27164
 * @version 1.0
 * @description: TODO
 * @date 2023/10/9 14:23
 */
@Service
@ConfigurationProperties(prefix = "oss")
@Data
public class OssUploadServiceImpl implements UploadService {


    private String accessKey;
    private String secretKey;
    private String bucket;


    @Override
    public ResponseResult uploadImg(MultipartFile img) {


        System.out.println(img);

        //获取文件名
        String originalFilename = img.getOriginalFilename();

        String filePath = PathUtils.generateFilePath(originalFilename);

        String url = uploadFun(img,filePath);

        return ResponseResult.okResult(url);
    }

    private String uploadFun(MultipartFile file,String filePath) {


        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
//...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);

        //路径 通过文件名配置路径
        String key = filePath;

        try {

            InputStream is = file.getInputStream();

            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);

            try {
                Response response = uploadManager.put(is,key,upToken,null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);

                return "http://s3gapvz3f.hn-bkt.clouddn.com/"+key;
            } catch (QiniuException ex) {
                ex.printStackTrace();
                if (ex.response != null) {
                    System.err.println(ex.response);

                    try {
                        String body = ex.response.toString();
                        System.err.println(body);
                    } catch (Exception ignored) {
                    }
                }
            }
        } catch (Exception ex) {
            //ignore
        }

        return "http://s3gapvz3f.hn-bkt.clouddn.com/"+key;
    }
}
