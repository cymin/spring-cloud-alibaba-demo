package com.github.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author : cymin
 * @date : 2021.11.24 下午10:34
 */
@FeignClient(value = "alibaba-stock-seata", path = "stock")
public interface StockFeignService {
    @PostMapping("/deduct")
    String deduct(@RequestParam("productId") Integer productId);
}
