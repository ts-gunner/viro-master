package com.forty.viro.provider;

import com.forty.viro.common.ViroCoreConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ViroCoreConfig.class})
public class ViroProviderAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ViroProviderAiApplication.class, args);
    }

}
