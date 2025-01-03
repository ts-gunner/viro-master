package com.forty.viro.common.tencent;

import com.forty.viro.common.enums.ErrorCode;
import com.forty.viro.common.exception.BusinessException;
import com.forty.viro.common.properties.TencentApiConfiguration;
import com.tencentcloudapi.asr.v20190614.AsrClient;
import com.tencentcloudapi.asr.v20190614.models.SentenceRecognitionRequest;
import com.tencentcloudapi.asr.v20190614.models.SentenceRecognitionResponse;
import com.tencentcloudapi.common.AbstractModel;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;

/**
 * 腾讯云音频转文字
 */
public class TencentASR {

    TencentApiConfiguration configuration;

    public TencentASR(TencentApiConfiguration configuration) {
        this.configuration = configuration;
    }

    public String getSpeechContent(String mp3Url){
        try{
            Credential cred = new Credential(this.configuration.getSecretId(), this.configuration.getSecretKey());
            // 实例化一个http选项，可选的，没有特殊需求可以跳过
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("asr.tencentcloudapi.com");
            // 实例化一个client选项，可选的，没有特殊需求可以跳过
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            AsrClient client = new AsrClient(cred, "", clientProfile);
            // 实例化一个请求对象,每个接口都会对应一个request对象
            SentenceRecognitionRequest req = new SentenceRecognitionRequest();
            req.setEngSerViceType("16k_zh_dialect");
            req.setSourceType(0L);
            req.setVoiceFormat("mp3");
            req.setUrl(mp3Url);
            // 返回的resp是一个SentenceRecognitionResponse的实例，与请求对象对应
            SentenceRecognitionResponse resp = client.SentenceRecognition(req);
            // 输出json格式的字符串回包
            return resp.getResult();
        } catch (TencentCloudSDKException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, e.toString());
        }
    }
}
