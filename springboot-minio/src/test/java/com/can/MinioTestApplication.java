package com.can;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import io.minio.errors.MinioException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @className: MinioTestApplication
 * @description: 测试启动类
 * @author: zhengcan
 * @date: 2023/3/23
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
public class MinioTestApplication {

    @Test
    public void uploadTest() throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        try {
            // 创建一个minio客户端
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint("http://127.0.0.1:9000")
                            .credentials("BV0QiINMOIBTZTNT", "QxoYnivnX3vnfmg3waoWzVx5bl09WDL6")
                            .build();

            // 如果不存在，创建一个桶
            boolean found =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket("test").build());
            if (!found) {
                // 创建一个桶
                minioClient.makeBucket(MakeBucketArgs.builder().bucket("test").build());
            } else {
                System.out.println("桶 'test' 早已存在.");
            }

            //上传文件到桶内
            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket("test")
                            .object("FrXngGFWIBoe76P.jpeg")
                            .filename("/Users/zhengcan/Downloads/FrXngGFWIBoe76P.jpeg")
                            .build());
            System.out.println(
                    "/Users/zhengcan/Downloads/FrXngGFWIBoe76P.jpeg");
        } catch (MinioException e) {
            System.out.println("出现错误: " + e);
            System.out.println("HTTP跟踪: " + e.httpTrace());
        }
    }
}
