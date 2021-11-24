package com.github.controller;

import com.github.model.Order;
import com.github.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : cymin
 * @date : 2021.11.24 下午6:09
 */
@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    OrderService orderService;

    /**
     * 下单
     * @return
     */
    @GetMapping("/add")
    public String create() {
        Order order = new Order();
        order.setProductId(9);
        order.setTotalAmount(100);
        order.setStatus(0);

        orderService.create(order);
        return "下单成功";
    }
}
