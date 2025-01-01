package com.forty.viro.common.model;

import cn.hutool.http.ContentType;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.Method;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.forty.viro.common.enums.ErrorCode;
import com.forty.viro.common.exception.BusinessException;
import com.forty.viro.common.properties.MoonshotProperties;


import java.util.List;
import java.util.stream.Stream;

public class MoonshotAiModel implements BaseAiModel {

    private final MoonshotProperties properties;

    public MoonshotAiModel(MoonshotProperties properties) {
        this.properties = properties;
    }
    public AiResult chat(List<AiMessage> messages) {
        return this.chat(properties.getApiKey(), properties.getModelName(), properties.getChatCompletionUrl(), messages);
    }

    public Stream<String> chatWithStream(List<AiMessage> messages) {
        try{
            return this.chatWithStream(properties.getApiKey(), properties.getModelName(), properties.getChatCompletionUrl(), messages);
        }catch (Exception e){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, e.getMessage());
        }
    }

    public int estimateTokenCount(List<AiMessage> messages) {
        String requestBody = new JSONObject()
                .putOpt("model", properties.getModelName())
                .putOpt("messages", messages)
                .toString();
        String body = HttpRequest.of(properties.getEstimateTokenCountUrl())
                .header(Header.AUTHORIZATION, "Bearer " + properties.getApiKey())
                .method(Method.POST)
                .header(Header.CONTENT_TYPE, ContentType.JSON.getValue())
                .body(requestBody)
                .execute()
                .body();
        JSONObject entries = JSONUtil.parseObj(body);
        if (!entries.containsKey("data")) {
            return -1;
        }else {
            JSONObject data = (JSONObject) entries.get("data");
            return (Integer) data.get("total_tokens");
        }
    }
}
