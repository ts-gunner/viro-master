package com.forty.viro.common.factory;



import com.forty.viro.common.enums.AiRoleEnum;
import com.forty.viro.common.enums.DeepSeekModelEnum;
import com.forty.viro.common.enums.ErrorCode;
import com.forty.viro.common.enums.MoonshotModelEnum;
import com.forty.viro.common.exception.BusinessException;
import com.forty.viro.common.model.*;
import com.forty.viro.common.properties.AiBaseProperties;
import com.forty.viro.common.properties.DeepSeekProperties;
import com.forty.viro.common.properties.MoonshotProperties;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class AiModelFactory {

    public static BaseAiModel createModel(AiBaseProperties properties) {
        if (properties instanceof MoonshotProperties moonshotProperties) {
            return new MoonshotAiModel(moonshotProperties);
        }
        else if (properties instanceof DeepSeekProperties deepSeekProperties) {
            return new DeepSeekAiModel(deepSeekProperties);
        }
        throw new BusinessException(ErrorCode.PARAM_ERROR, "Unkown AiProperties");
    }

    public static void main(String[] args) {
        String moonshotApiKey = "sk-6CB0AEQummzFiv19f6L7wkhQ3VJR5fPltO6ydNw7lbKvuXoR";
        String deepseekApiKey = "sk-e1a6b84e76234300b1084586dc741214";

        BaseAiModel model1 = AiModelFactory.createModel(new DeepSeekProperties(deepseekApiKey, DeepSeekModelEnum.DEEPSEEK_CHAT));
        BaseAiModel model2 = AiModelFactory.createModel(new MoonshotProperties(moonshotApiKey, MoonshotModelEnum.MOONSHOT_V1_8K));
        List<AiMessage> messages = Arrays.asList(
                new AiMessage(AiRoleEnum.SYSTEM, "Hello, What I can help you?"),
                new AiMessage(AiRoleEnum.USER, "My name is Jun Jian Yang. Who are you?"));
//        AiResult chat1 = model1.chat(messages);
//        System.out.println(chat1);
//        AiResult chat2 = model2.chat(messages);
//        System.out.println(chat2);
//        int i = ((MoonshotAiModel) model2).estimateTokenCount(messages);  // 需要将model2强转MoonshotAiModel
//        System.out.println(i);
        Stream<String> stringStream = model1.chatWithStream(messages);
        stringStream.forEach(System.out::println);
    }
}
