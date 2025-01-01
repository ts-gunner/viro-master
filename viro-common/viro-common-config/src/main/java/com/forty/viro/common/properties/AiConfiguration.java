package com.forty.viro.common.properties;


import com.forty.viro.common.constant.GlobalConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = GlobalConstant.CONFIG_PREFIX + ".ai")
public class AiConfiguration {

    private DeepSeekConfiguration deepseek;

    private MoonshotConfiguration moonshot;

    @Data
    public static class DeepSeekConfiguration {
        private String apiKey;

    }

    @Data
    public static class MoonshotConfiguration {
        private String apiKey;

    }
}
