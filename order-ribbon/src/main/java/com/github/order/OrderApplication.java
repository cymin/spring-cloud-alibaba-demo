package com.github.order;

import com.github.ribbon.RandomRuleConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
//@EnableDiscoveryClient // 该注解在当前springboot版本中也可以不加，但加上也没错
// 给stock-service重新指定负载均衡策略，负载均衡策略只能在消费端设置，不能在提供端设置
@RibbonClients(value = {
        @RibbonClient(name = "stock-service", configuration = RandomRuleConfig.class)
})
public class OrderApplication {

    @Bean
    @LoadBalanced //开启ribbon的负载均衡，解析服务名称
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

}
