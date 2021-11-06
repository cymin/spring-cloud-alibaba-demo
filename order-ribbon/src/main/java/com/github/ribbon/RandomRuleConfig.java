package com.github.ribbon;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 当前配置类，必须放在不被springboot的@ComponentScan扫描到的地方，默认basePackages=OrderApplication所在的包com.github.order
 * 所以com.github.ribbon默认是不被扫描的
 * @author : cymin
 * @date : 2021.11.06 下午9:20
 */
@Configuration
public class RandomRuleConfig {
    /**
     * 方法名一定要叫iRule，其他的不起作用
     * @return
     */
    @Bean
    IRule iRule() {
        return new RandomRule();
    }
}
