package com.github.order.rule;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 实现一个自定义的负载均衡策略，该类放在任意一个package中都可以
 * 默认是懒加载，在第一次调用的时候拦截，所以第一次比较慢
 * @author : cymin
 * @date : 2021.11.07 上午8:47
 */
@Configuration
public class CustomRule extends AbstractLoadBalancerRule {

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    /**
     * 随机选择一个服务实例名称并返回
     * @param key
     * @return
     */
    @Override
    public Server choose(Object key) {
        // 获取负载均衡器
        ILoadBalancer loadBalancer = this.getLoadBalancer();
        // 获取可用的服务列表
        List<Server> reachableServers = loadBalancer.getReachableServers();
        // 随机选择一个服务实例，注意要用线程安全的方式
        int i = ThreadLocalRandom.current().nextInt(reachableServers.size());
        // 注意这里，如果服务不可用，应该循环判断，直到获取到一个可用的服务实例，如果都不可用，返回null
        Server server = reachableServers.get(i);
        if(server.isAlive()) {
            return server;
        }
        return null;
    }
}
