package com.forty.viro.common.enums;

import lombok.Getter;

@Getter
public enum MoonshotModelEnum {

    MOONSHOT_V1_8K("moonshot-v1-8k");

    private final String modelName;

    private MoonshotModelEnum(String modelName) {
        this.modelName = modelName;
    }

}
