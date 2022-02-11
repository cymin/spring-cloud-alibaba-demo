package com.github.controller;

import com.github.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author : cymin
 * @date : 2021.11.24 下午6:07
 */
@RestController
@RequestMapping("stock")
public class StockController {
    @Autowired
    StockService stockService;

    @GetMapping("/deduct")
    public String deduct() {
        stockService.deduct(1);
        return "库存递减成功";
    }
}
