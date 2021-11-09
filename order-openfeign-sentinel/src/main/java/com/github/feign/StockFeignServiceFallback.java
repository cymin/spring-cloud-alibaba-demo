package com.github.feign;

import org.springframework.stereotype.Component;

/**
 * @author : cymin
 * @date : 2021.11.09 上午11:09
 */
@Component
public class StockFeignServiceFallback implements StockFeignService {
    @Override
    public String deduct() {
        return "111服务降级啦";
    }

    @Override
    public String deduct2() {
        return "服务降级啦";
    }
}
