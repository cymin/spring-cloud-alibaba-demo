package com.github;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

/**
 * @author : cymin
 * @date : 2021.11.24 下午6:03
 */
@SpringBootApplication
@MapperScan({"com.github.mapper"})
@EnableTransactionManagement
public class OrderSeataApplication {
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
    public static void main(String[] args) {
        SpringApplication.run(OrderSeataApplication.class, args);
    }
}
