package com.forty.viro.provider.controller;


import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONUtil;
import com.forty.viro.common.ViroCoreConfig;
import com.forty.viro.common.enums.AiRoleEnum;
import com.forty.viro.common.enums.DeepSeekModelEnum;
import com.forty.viro.common.factory.AiModelFactory;
import com.forty.viro.common.model.AiMessage;
import com.forty.viro.common.model.BaseAiModel;
import com.forty.viro.common.model.SseEvent;
import com.forty.viro.common.properties.DeepSeekProperties;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

@RequestMapping("/chat")
@RestController
public class ChatController {
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Resource
    ViroCoreConfig viroCoreConfig;

    @GetMapping(value = "/chat_with_stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter chatWithStream(@RequestParam("question") String question) {
        SseEmitter emitter = new SseEmitter();
        String deepseekApiKey = viroCoreConfig.getAi().getDeepseek().getApiKey();
        BaseAiModel model = AiModelFactory.createModel(new DeepSeekProperties(deepseekApiKey, DeepSeekModelEnum.DEEPSEEK_CHAT));
        ArrayList<AiMessage> aiMessages = new ArrayList<>();
        aiMessages.add(new AiMessage(AiRoleEnum.USER, question));
        Stream<String> chatStream = model.chatWithStream(aiMessages);
        executor.execute(() -> {
            // send connect successful message
            try {
                emitter.send(Convert.strToUnicode(JSONUtil.toJsonStr(SseEvent.builder()
                        .event("connection")
                        .data("ok")
                        .build())));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            chatStream.forEach(str -> {
                try {
                    SseEvent aiContext = SseEvent.builder()
                            .event("ai_context")
                            .data(str)
                            .build();
                    emitter.send(Convert.strToUnicode(JSONUtil.toJsonStr(aiContext)));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        });
        return emitter;
    }
}
