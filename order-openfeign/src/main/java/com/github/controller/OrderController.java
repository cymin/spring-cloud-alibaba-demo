package com.github.controller;

import com.github.feign.ProductFeignService;
import com.github.feign.StockFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by cymin on 2021/11/4.
 */
@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    StockFeignService stockFeignService;

    @Autowired
    ProductFeignService productFeignService;

    /**
     * 4.使用openfeign
     * @return
     */
    @GetMapping("/add")
    public String add() {
        System.out.println("订单创建成功");
        String s = stockFeignService.deduct();
        String p = productFeignService.get(1);
        return "stock:" + s + ", product:" + p;
    }
}