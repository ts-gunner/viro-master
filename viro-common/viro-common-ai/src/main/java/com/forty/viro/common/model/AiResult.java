package com.forty.viro.common.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AiResult {

    private String chatId;

    private String modelName;

    private String answer;

    private Integer promptTokens;

    private Integer answerTokens;

    private Integer totalTokens;

}
