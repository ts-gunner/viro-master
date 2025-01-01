package com.forty.viro.common.properties;

import lombok.Getter;

@Getter
public class AiBaseProperties {
    private final String apiKey;
    private final String modelName;

    AiBaseProperties(String apiKey, String modelName) {
        this.apiKey = apiKey;
        this.modelName = modelName;
    }
}
