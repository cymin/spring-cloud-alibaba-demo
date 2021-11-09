package com.github.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.github.feign.StockFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/add")
    public String add() {
        System.out.println("订单创建成功");
        String s = stockFeignService.deduct();
        return "stock:" + s;
    }

    @GetMapping("/add2")
    public String add2() {
        System.out.println("订单创建成功");
        String s = stockFeignService.deduct2();
        return "stock:" + s;
    }

    /**
     * 热点规则
     * 必须使用@SentinelResource注解对getById添加规则，对/get/id添加没用
     * 异常处理也必须使用blockHandle，不能使用全局异常处理
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    @SentinelResource(value = "getById", blockHandler = "blockHandler4getById")
    public String getById(@PathVariable("id") Integer id) {
        System.out.println("正常访问");
        return "正常访问";
    }

    public String blockHandler4getById(@PathVariable("id") Integer id, BlockException e) {
        return "热点异常处理";
    }

}