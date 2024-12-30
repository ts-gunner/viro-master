package com.forty.viro.provider;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.forty.viro.provider.mapper")
public class ViroProviderDscApplication {
    public static void main(String[] args) {
        SpringApplication.run(ViroProviderDscApplication.class, args);
    }
}
