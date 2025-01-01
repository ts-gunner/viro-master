package com.forty.viro.common.enums;

import lombok.Getter;

@Getter
public enum DeepSeekModelEnum {

    DEEPSEEK_CHAT("deepseek-chat");

    private final String modelName;

    private DeepSeekModelEnum(String modelName) {
        this.modelName = modelName;
    }

}
