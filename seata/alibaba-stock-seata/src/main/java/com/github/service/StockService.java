package com.github.service;

import com.github.mapper.StockMapper;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : cymin
 * @date : 2021.11.24 下午6:07
 */
@Service
public class StockService {
    @Autowired
    StockMapper stockMapper;

//    @GlobalTransactional
    public void deduct(Integer productId) {
        stockMapper.deduct(productId);
        System.out.println("更新商品id：" + productId);
    }
}
