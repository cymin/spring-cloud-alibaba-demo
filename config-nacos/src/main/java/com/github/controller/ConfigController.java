package com.github.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

/**
 * 使用@RequestScope注解可以动态刷新@Value的值，实时获取注册中心的配置属性
 * @author : cymin
 * @date : 2021.11.07 下午10:07
 */
@RestController
@RequestMapping("/config")
@RequestScope
public class ConfigController {

    @Value("${user.baba}")
    public String baba;

    @GetMapping("/get")
    public String getValue() {
        return "user.baba=" + baba;
    }
}
