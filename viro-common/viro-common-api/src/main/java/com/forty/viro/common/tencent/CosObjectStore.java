package com.forty.viro.common.tencent;

import com.forty.viro.common.properties.TencentApiConfiguration;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.StorageClass;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.model.PutObjectRequest;

import java.io.File;

public class CosObjectStore {
    private final TencentApiConfiguration config;

    public CosObjectStore(TencentApiConfiguration config) {
        this.config = config;
    }

    COSClient createCOSClient() {
        COSCredentials cred = new BasicCOSCredentials(this.config.getSecretId(), this.config.getSecretKey());

        // ClientConfig 中包含了后续请求 COS 的客户端设置：
        ClientConfig clientConfig = new ClientConfig();

        clientConfig.setRegion(new Region(this.config.getObjectStore().getRegionName()));

        // 生成 cos 客户端。
        return new COSClient(cred, clientConfig);
    }

    // 将本地文件上传到 COS
    public String putObject(String key, File file)
            throws CosClientException {
        COSClient cosClient = createCOSClient();

        PutObjectRequest putObjectRequest = new PutObjectRequest(this.config.getObjectStore().getBuckName(), key, file);

        putObjectRequest.setStorageClass(StorageClass.Standard);
        try {
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            System.out.println(putObjectResult.getRequestId());
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        } finally {
            // 确认本进程不再使用 cosClient 实例之后，关闭即可
            cosClient.shutdown();
        }
        return this.config.getObjectStore().getBaseStorePath() + key;
    }
}
