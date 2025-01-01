package com.forty.viro.common.model;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.Method;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import com.forty.viro.common.enums.ErrorCode;
import com.forty.viro.common.exception.BusinessException;
import okhttp3.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public interface BaseAiModel {

    AiResult chat(List<AiMessage> messages);

    Stream<String> chatWithStream(List<AiMessage> messages);

    default AiResult chat(String apiKey, String modelName, String chatUrl, List<AiMessage> messages) {
        String requestBody = new JSONObject()
                .putOpt("model", modelName)
                .putOpt("messages", messages)
                .toString();

        String body = HttpRequest.of(chatUrl)
                .header(Header.AUTHORIZATION, "Bearer " + apiKey)
                .method(Method.POST)
                .header(Header.CONTENT_TYPE, ContentType.JSON.getValue())
                .body(requestBody)
                .execute()
                .body();
        JSONObject entries = JSONUtil.parseObj(body);
        if (entries.containsKey("error")) {
            JSONObject error = (JSONObject) entries.get("error");
            throw new BusinessException(ErrorCode.PARAM_ERROR, "AI answer error: " + String.valueOf(error.get("message")));
        }
        JSONObject usage = (JSONObject)entries.get("usage");
        String content = entries.get("choices", JSONArray.class)
                .get(0, JSONObject.class)
                .get("message", JSONObject.class)
                .get("content", String.class);
        return AiResult.builder()
                .chatId(entries.get("id",String.class))
                .modelName(entries.get("model", String.class))
                .answer(content)
                .promptTokens((Integer)usage.get("prompt_tokens"))
                .answerTokens((Integer) usage.get("completion_tokens"))
                .totalTokens((Integer) usage.get("total_tokens"))
                .build();
    }

    default Stream<String> chatWithStream(String apiKey, String modelName, String chatUrl, List<AiMessage> messages) throws IOException {
        String requestBody = new JSONObject()
                .putOpt("model", modelName)
                .putOpt("messages", messages)
                .putOpt("stream", true)
                .toString();
        Request okhttpRequest = new Request.Builder()
                .url(chatUrl)
                .post(RequestBody.create(requestBody, MediaType.get(ContentType.JSON.getValue())))
                .addHeader("Authorization", "Bearer " + apiKey)
                .build();
        Call call = new OkHttpClient().newCall(okhttpRequest);
        Response okhttpResponse = call.execute();
        BufferedReader reader = new BufferedReader(okhttpResponse.body().charStream());
        return Stream.generate(() -> {
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (StrUtil.isBlank(line)) continue;
                    if (JSONUtil.isTypeJSON(line)) {
                        Optional.of(JSONUtil.parseObj(line))
                                .map(x -> x.getJSONObject("error"))
                                .map(x -> x.getStr("message"))
                                .ifPresent(x -> System.out.println("error: " + x));
                        return null;
                    }
                    line = StrUtil.replace(line, "data: ", StrUtil.EMPTY);

                    if (StrUtil.equals("[DONE]", line) || !JSONUtil.isTypeJSON(line)) {
                        return null;
                    }

                    return JSONUtil.parseObj(line)
                            .get("choices", JSONArray.class)
                            .get(0, JSONObject.class)
                            .get("delta", JSONObject.class)
                            .get("content", String.class);
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }).takeWhile(Objects::nonNull);  // 当等于null的时候，停止生成

    }
}
