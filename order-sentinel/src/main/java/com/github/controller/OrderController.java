package com.github.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by cymin on 2021/11/4.
 * 默认一旦spring应用重启之后，在sentinel控制台中配置的信息都会丢失
 */
@RestController
@RequestMapping("order")
public class OrderController {

    /**
     * 流控
     * 注意value的值
     * @return
     */
    @GetMapping("/add")
    @SentinelResource(value = "add", blockHandler = "addBlockHandler")
    public String add() {
        return "success";
    }

    public String addBlockHandler(BlockException e) {
        return "被流控";
    }
}
