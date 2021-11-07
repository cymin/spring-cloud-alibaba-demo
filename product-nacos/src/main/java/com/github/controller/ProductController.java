package com.github.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by cymin on 2021/11/4.
 */
@RestController
@RequestMapping("product")
public class ProductController {

    @Value("${server.port}")
    String port;

    @GetMapping("/get/{id}")
    public String get(@PathVariable Integer id) {
        System.out.println("查询到商品id：" + id);
        return "查询到商品id：" + id + " port=" + port;
    }
}
