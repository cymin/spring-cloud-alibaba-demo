package com.github.service;

import com.github.mapper.StockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : cymin
 * @date : 2021.11.24 下午6:07
 */
@Service
public class StockService {
    @Autowired
    StockMapper stockMapper;

    @Transactional(propagation = Propagation.REQUIRED=.)
    public void deduct(Integer productId) {
        stockMapper.deduct(productId);
        stockMapper.deduct(productId);
        stockMapper.deduct(productId);
        System.out.println("更新商品id：" + productId);
    }
}
