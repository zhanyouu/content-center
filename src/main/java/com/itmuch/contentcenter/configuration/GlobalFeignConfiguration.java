package com.itmuch.contentcenter.configuration;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * feign的配置类
 * 这个类别加@Configuration注解，否则必须挪到启动类能扫描到的包外
 */
//@Configuration
public class GlobalFeignConfiguration {
    //配置feign输出的日志级别
    @Bean
    public Logger.Level level() {
        return Logger.Level.FULL;
    }
}
