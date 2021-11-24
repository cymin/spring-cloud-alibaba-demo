package com.github;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author : cymin
 * @date : 2021.11.24 下午5:59
 */
@SpringBootApplication
@MapperScan("com.github.mapper")
public class StockSeataApplication {
    public static void main(String[] args) {
        SpringApplication.run(StockSeataApplication.class, args);
    }

}
