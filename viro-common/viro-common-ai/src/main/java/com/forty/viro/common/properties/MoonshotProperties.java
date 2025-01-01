package com.forty.viro.common.properties;


import com.forty.viro.common.enums.MoonshotModelEnum;
import lombok.Getter;

/**
 * Moonshot AI  https://platform.moonshot.cn/docs/api
 */
@Getter
public class MoonshotProperties extends AiBaseProperties {

    private final String modelUrl = "https://api.moonshot.cn/v1/models";
    private final String chatCompletionUrl = "https://api.moonshot.cn/v1/chat/completions";
    private final String estimateTokenCountUrl = "https://api.moonshot.cn/v1/tokenizers/estimate-token-count";


    public MoonshotProperties(String apiKey, MoonshotModelEnum moonshotModelEnum) {
        super(apiKey, moonshotModelEnum.getModelName());

    }
}
