package com.github.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * Created by cymin on 2021/11/4.
 * 默认一旦spring应用重启之后，在sentinel控制台中配置的信息都会丢失
 */
@RestController
@RequestMapping("order")
public class OrderController {

    /**
     * 流控
     * 使用@SentinelResource注解给资源名add加流控规则
     * 使用全局异常处理器给资源名/order/add加流控规则
     * 注意value的值
     * @return
     */
    @GetMapping("/add")
//    @SentinelResource(value = "add", blockHandler = "addBlockHandler")
    public String add() {
        return "success";
    }

    public String addBlockHandler(BlockException e) {
        return "被流控";
    }

    /**
     * 线程流控
     * 同一时间只能有一个线程访问，等这个线程处理完了，才可以处理另一个线程
     * @return
     */
    @GetMapping("/thread")
    @SentinelResource(value = "thread", blockHandler = "addBlockHandler")
    public String threadFlow() throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        return "success";
    }


}
