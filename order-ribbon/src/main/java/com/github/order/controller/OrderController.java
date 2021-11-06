package com.github.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by cymin on 2021/11/4.
 */
@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/add")
    public String add() {
        System.out.println("订单创建成功");
        String msg = restTemplate.getForObject("http://stock-service/stock/deduct", String.class);
        return "success " + msg;
    }
}
