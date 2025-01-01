package com.forty.viro.common.properties;


import com.forty.viro.common.enums.DeepSeekModelEnum;
import lombok.Getter;

/**
 * DeepSeek， https://platform.deepseek.com/
 */
@Getter
public class DeepSeekProperties extends AiBaseProperties {

    private final String modelUrl = "https://api.deepseek.com/models";
    private final String chatCompletionUrl = "https://api.deepseek.com/chat/completions";

    public DeepSeekProperties(String apiKey, DeepSeekModelEnum deepSeekModel) {
        super(apiKey, deepSeekModel.getModelName());
    }

}
