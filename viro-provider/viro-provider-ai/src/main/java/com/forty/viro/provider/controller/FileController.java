package com.forty.viro.provider.controller;


import com.forty.viro.common.ViroCoreConfig;
import com.forty.viro.common.enums.ErrorCode;
import com.forty.viro.common.exception.BusinessException;
import com.forty.viro.common.model.BaseResponse;
import com.forty.viro.common.properties.TencentApiConfiguration;
import com.forty.viro.common.tencent.CosObjectStore;
import com.forty.viro.common.tencent.TencentASR;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileController {

    @Resource
    ViroCoreConfig coreConfig;
    /**
     * 上传MP3转文本
     * @param file
     * @return
     */
    @PostMapping("/upload_recorders")
    public BaseResponse<String> convertRecorderToText(@RequestParam("blob") MultipartFile file) {
        TencentApiConfiguration tencentConfig = coreConfig.getTencent();
        BaseResponse<String> response = new BaseResponse<>();
        try {
            File tempFile = File.createTempFile(file.getOriginalFilename(), ".tmp");
            file.transferTo(tempFile);
            // 上传到对象存储
            CosObjectStore cosObjectStore = new CosObjectStore(tencentConfig);
            String url = cosObjectStore.putObject("/tmp/" + file.getOriginalFilename(), tempFile);
            // 语音识别
            response.setData(new TencentASR(tencentConfig).getSpeechContent(url));
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, e.getMessage());
        }
        return response;
    }

    @GetMapping("/upload_recorders")
    public BaseResponse<Object> getRecorders() {
        return new BaseResponse<>();
    }
}
