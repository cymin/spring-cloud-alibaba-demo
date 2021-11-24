package com.github.service;

import com.github.mapper.OrderMapper;
import com.github.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @author : cymin
 * @date : 2021.11.24 下午6:09
 */
@Service
public class OrderService {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    OrderMapper orderMapper;

    @Transactional
    public Order create(Order order) {
        // 插入订单？能否成功
        // 在加了@Transactional注解的情况下，订单无法创建成功，但是扣减库存成功了
        orderMapper.insert(order);

        // 扣减库存，能否成功
        // 这里由于是两个表来模拟两个不同的数据库，使用本地事务无法保证stock表的事务回滚
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("productId", order.getProductId());
        String msg = restTemplate.postForObject("http://localhost:8071/stock/deduct", paramMap, String.class);

        // 抛出异常，测试能否完成事务
        System.out.println(1/0);

        System.out.println("订单创建成功");
        return order;
    }
}
