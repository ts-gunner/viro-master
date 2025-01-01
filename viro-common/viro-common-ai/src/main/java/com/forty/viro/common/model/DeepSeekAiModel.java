package com.forty.viro.common.model;



import com.forty.viro.common.enums.ErrorCode;
import com.forty.viro.common.exception.BusinessException;
import com.forty.viro.common.properties.DeepSeekProperties;

import java.util.List;
import java.util.stream.Stream;

public class DeepSeekAiModel implements BaseAiModel {

    private final DeepSeekProperties properties;

    public DeepSeekAiModel(DeepSeekProperties properties) {
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

}
