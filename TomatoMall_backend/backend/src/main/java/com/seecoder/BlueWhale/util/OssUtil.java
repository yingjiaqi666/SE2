package com.seecoder.BlueWhale.util;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import java.io.InputStream;
import java.util.Date;
import java.io.*;


@Getter
@Setter
@NoArgsConstructor
@ConfigurationProperties("aliyun.oss")
@Component
public class OssUtil {
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;


    public String upload(String objectName, InputStream inputStream) {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, inputStream);
        try {
            ossClient.putObject(putObjectRequest);
        }finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return ossClient.generatePresignedUrl(bucketName, objectName, new Date()).toString().split("\\?Expires")[0];
    }

    public String generatePresignedUrl(String objectName) {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            Date expiration = new Date(System.currentTimeMillis() + 3600 * 1000);
            return ossClient.generatePresignedUrl(bucketName, objectName, expiration).toString();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }


}
