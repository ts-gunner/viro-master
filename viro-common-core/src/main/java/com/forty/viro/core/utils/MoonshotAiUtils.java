package com.forty.viro.core.utils;


import cn.hutool.http.ContentType;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.Method;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.forty.viro.core.enums.AiRoleEnum;
import com.forty.viro.core.model.AiMessage;
import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Moonshot AI  https://platform.moonshot.cn/docs/api
 */

@Data
public class MoonshotAiUtils {

    private String API_KEY;
    private static final String MODELS_URL = "https://api.moonshot.cn/v1/models";
    private static final String FILES_URL = "https://api.moonshot.cn/v1/files";
    private static final String ESTIMATE_TOKEN_COUNT_URL = "https://api.moonshot.cn/v1/tokenizers/estimate-token-count";
    private static final String CHAT_COMPLETION_URL = "https://api.moonshot.cn/v1/chat/completions";

    public MoonshotAiUtils(@NonNull final String apiKey) {
        this.API_KEY = apiKey;
    }
    private HttpRequest getCommonRequest(@NonNull String url) {
        return HttpRequest.of(url).header(Header.AUTHORIZATION, "Bearer " + API_KEY);
    }
    public String getModelList() {
        return getCommonRequest(MODELS_URL)
                .execute()
                .body();
    }

    public int estimateTokenCount(@NonNull String model, @NonNull List<AiMessage> messages) {
        String requestBody = new JSONObject()
                .putOpt("model", model)
                .putOpt("messages", messages)
                .toString();
        String body = getCommonRequest(ESTIMATE_TOKEN_COUNT_URL)
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

    public void chat(@NonNull String model, @NonNull List<AiMessage> messages){
        String requestBody = new JSONObject()
                .putOpt("model", model)
                .putOpt("messages", messages)
                .toString();
        String body = getCommonRequest(CHAT_COMPLETION_URL)
                .method(Method.POST)
                .header(Header.CONTENT_TYPE, ContentType.JSON.getValue())
                .body(requestBody)
                .execute()
                .body();
        System.out.println(body);


    }

    public void chatWithStrem(@NonNull String model, @NonNull List<AiMessage> messages){

    }
    public static void main(String[] args) {
        String apiKey = "sk-6CB0AEQummzFiv19f6L7wkhQ3VJR5fPltO6ydNw7lbKvuXoR";
        String modelName = "moonshot-v1-8k";
        List<AiMessage> messages = Arrays.asList(
                new AiMessage(AiRoleEnum.SYSTEM.getRoleName(), "Hello, What I can help you?"),
                new AiMessage(AiRoleEnum.USER.getRoleName(), "My name is Jun Jian Yang. Who are you?"));
        MoonshotAiUtils moonshotAi = new MoonshotAiUtils(apiKey);
        // 获取模型列表
//        String modelList = moonshotAi.getModelList();
//        System.out.println(modelList);
//        int length = moonshotAi.estimateTokenCount(modelName, messages);
//        System.out.println("文本长度：" + length);
        moonshotAi.chat(modelName, messages);
    }

}
