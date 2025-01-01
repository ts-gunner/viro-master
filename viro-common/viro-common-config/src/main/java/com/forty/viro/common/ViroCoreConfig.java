package com.forty.viro.common;


import com.forty.viro.common.constant.GlobalConstant;
import com.forty.viro.common.properties.AiConfiguration;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = GlobalConstant.CONFIG_PREFIX)
public class ViroCoreConfig {
    private AiConfiguration ai;

}
