package com.github.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author : cymin
 * @date : 2021.11.07 上午11:58
 */
/*
name：要远程调用的服务名称
path: product-service服务中对应在ProductController中的@RequestMapping，如果没有@RequestMapping则省略path
 */
@FeignClient(name = "product-service", path = "product")
public interface ProductFeignService {

    /**
     * 保持跟product-nacos中 module的ProductController中方法一致，但稍微不同的是：
     * controller中的方法对参数校验没有那么严格，@PathVariable如果不写value则默认跟方法中参数保持一致，String get(@PathVariable String id)
     * feign中的接口对参数校验十分严格，必须指定@PathVariable中的value跟参数保持一致，String get(@PathVariable("id") String id)
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    String get(@PathVariable("id") Integer id);
}
