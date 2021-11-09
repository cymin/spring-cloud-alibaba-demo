package com.github.feign;

import com.github.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author : cymin
 * @date : 2021.11.07 上午11:58
 */
@FeignClient(name = "stock-service", path = "stock", configuration = FeignConfig.class, fallback = StockFeignServiceFallback.class)
public interface StockFeignService {

    @GetMapping("/deduct")
    String deduct();

    @GetMapping("/deduct2")
    String deduct2();
}
