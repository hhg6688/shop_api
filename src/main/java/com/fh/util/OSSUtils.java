package com.fh.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;

import java.io.File;

public class OSSUtils {

    public static String uploadFile(File file){
        String endpoint = "http://oss-cn-qingdao.aliyuncs.com";
        String accessKeyId = "LTAI4Fzk48Ah3cDFcTQqznp5";
        String accessKeySecret = "dJDLbirhKGSXimTuge3b6e8th0e9uX";
        String bucketName="1908a-huang";
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 创建PutObjectRequest对象。
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,file.getName(),file);

        // 上传文件。
        PutObjectResult putObjectResult = ossClient.putObject(putObjectRequest);

        // 关闭OSSClient。
        ossClient.shutdown();
        return "http://"+bucketName+"."+endpoint+"/"+file.getName();
    }
}
