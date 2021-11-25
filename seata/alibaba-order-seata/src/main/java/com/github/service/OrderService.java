package com.github.service;

import com.github.mapper.OrderMapper;
import com.github.model.Order;
import com.github.openfeign.StockFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : cymin
 * @date : 2021.11.24 下午6:09
 */
@Service
public class OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    StockFeignService stockFeignService;

    @Transactional
    public Order create(Order order) {
        // 插入订单？能否成功
        orderMapper.insert(order);

        // 扣减库存，能否成功
        // 这里由于是两个表来模拟两个不同的数据库，
        stockFeignService.deduct(order.getProductId());

        // 抛出异常，测试能否完成事务
        System.out.println(1/0);

        System.out.println("订单创建成功");
        return order;
    }
}
