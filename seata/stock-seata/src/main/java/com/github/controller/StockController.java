package com.github.controller;

import com.github.model.Stock;
import com.github.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : cymin
 * @date : 2021.11.24 下午6:07
 */
@RestController
@RequestMapping("stock")
public class StockController {
    @Autowired
    StockService stockService;

    @PostMapping("/deduct")
    public String deduct(@RequestParam("productId") Integer productId) {
        stockService.deduct(productId);
        return "库存递减成功";
    }
}
