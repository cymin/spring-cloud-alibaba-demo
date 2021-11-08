package com.github.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.github.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    OrderService orderService;

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

    /**
     * 测试关联流控模式：
     * 每秒调用关联资源/order/add超过2次，资源名/order/get将会被限流
     * @return
     */
    @GetMapping("/get")
    public String get() {
        return "success";
    }

    /**
     * 链路流控模式：
     * test1接口和test2接口同时调用了业务方法getUsername,对业务方法getUsername进行流控，
     * 每秒钟调用资源名test2超过2次，入口资源/order/test2将会被限流
     * @return
     */
    @GetMapping("/test1")
    public String test1() {
        return "test1:" + orderService.getUsername();
    }

    /**
     * 链路流控模式：
     * test1接口和test2接口同时调用了业务方法getUsername,对业务方法getUsername进行流控，
     * 每秒钟调用资源名test2超过2次，入口资源/order/test2将会被限流
     * @return
     */
    @GetMapping("/test2")
    public String test2() {
        return "test2:" + orderService.getUsername();
    }


}
