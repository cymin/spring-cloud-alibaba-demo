package com.github.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 两种配置方式
 * 全局配置：使用@Configuration，作用于所有的服务提供者，会打印所有调用提供者rest接口的日志
 * 局部配置：2种方法：1.不使用@Configuration，作用于某一个服务提供者; 2.使用application.yml配置文件
 * @author : cymin
 * @date : 2021.11.07 下午12:37
 */
//@Configuration
public class FeignConfig {
    /**
     * 修改feign的日志级别
     * @return
     */
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
