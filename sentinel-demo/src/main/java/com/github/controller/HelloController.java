package com.github.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by cymin on 2021/11/4.
 */
@RestController
@Slf4j
public class HelloController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);

    public static final String RESOURCE_NAME = "hello";
    public static final String USER_RESOURCE_NAME = "user";
    public static final String DEGRADE_RESOURCE_NAME = "degrade";

    /**
     * 使用sentinel进行流控
     * @return
     */
    @GetMapping("/hello")
    public String hello() {
        Entry entry = null;
        try {
            // 使用entry定义一个资源名称，通常跟 @GetMapping("/hello")中的保持一致，sentinel针对资源进行限制的
            entry = SphU.entry(RESOURCE_NAME);
            String str = "-------hello world ---------";
            LOGGER.info(str);
            return str;
        } catch (BlockException e) {
            //资源访问阻止，被限流或者被降级，进行相应的处理
            LOGGER.info("被流控了");
            return "被流控了";
        } catch (Exception e) {
            // 若需要配置降级规则，需要通过这种方式记录业务异常
            Tracer.traceEntry(e, entry);
            if (entry != null) {
                entry.exit();
            }
        }
        return null;
    }

    /**
     * 定义规则
     * 使用spring的@PostConstruct注解，在创建controller的时候调用初始化方法initFlowRules
     */
    @PostConstruct
    private static void initFlowRules() {
        // 流控规则
        List<FlowRule> rules = new ArrayList<>();
        // 流控
        FlowRule rule = new FlowRule();
        // 设置被保护的资源
        rule.setResource(RESOURCE_NAME);
        // 设置流控规则 QPS
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // 设置受保护的资源阈值 set limit QPS to 20.
        rule.setCount(1);

        rules.add(rule);

        FlowRuleManager.loadRules(rules);
    }
}
