package com.github.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by cymin on 2021/11/4.
 */
@RestController
@RequestMapping("stock")
public class StockController {

    @GetMapping("/deduct")
    public String deduct() {
        System.out.println("库存递减成功");
        return "success";
    }
}
