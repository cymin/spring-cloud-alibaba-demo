package com.github.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author : cymin
 * @date : 2021.11.07 上午11:58
 */
/*
name：要远程调用的服务名称
path: stock-service服务中对应在StockController中的@RequestMapping，如果没有@RequestMapping则省略path
 */
@FeignClient(name = "stock-service", path = "stock")
public interface StockFeignService {
    /**
     * 2.声明需要调用的rest接口方法，跟StockController中的方法保持一致，声明完不需要写实现类
     * FeignClient类似于mybatis中的@Mapper使用的都是动态代理方式
     * @return
     */
    @GetMapping("/deduct")
    String deduct();

/**
 @RestController
 @RequestMapping("stock")
 public class StockController {

     @Value("${server.port}")
     String port;

     @GetMapping("/deduct")
     public String deduct() {
        System.out.println("库存递减成功");
        return "库存递减成功 port=" + port;
     }
 }
 */
}
