package com.github;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author : cymin
 * @date : 2021.11.24 下午6:03
 */
@SpringBootApplication
@MapperScan("com.github.mapper")
@EnableTransactionManagement //开启本地事务
@EnableFeignClients //启用openfeign
public class AlibabaOrderSeataApplication {
    public static void main(String[] args) {
        SpringApplication.run(AlibabaOrderSeataApplication.class, args);
    }
}
