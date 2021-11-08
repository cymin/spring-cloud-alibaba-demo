package com.github.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.github.pojo.User;
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
public class HelloController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);

    public static final String RESOURCE_NAME = "hello";
    public static final String USER_RESOURCE_NAME = "user";
    public static final String DEGRADE_RESOURCE_NAME = "degrade";

    /**
     * 使用@SentinelResource注解改善接口中定义资源和被流控降级之后的处理方法，使用该注解需要：
     * 1.增加sentinel-annotation-aspectj依赖
     * 2.在springboot启动类中配置bean：SentinelResourceAspect
     *
     * value = USER_RESOURCE_NAME:定义资源
     * blockHandler = "blockHandler4getUser"：设置被流控降级后的方法，默认该方法必须在同一个类,如果不想跟源方法放在同一个类中，可以在@SentinelResource注解中设置blockHandlerClass属性，
     * 但指定Class中的方法必须声明为static类型，否则无法解析
     * fallback: 指定接口出现异常时的处理方法, 如果同时指定二者，fallback优先级高于blockHandler，默认该方法必须在同一个类,如果不想跟源方法放在同一个类中，可以在@SentinelResource注解中设置bfallbackClass属性，
     *  但指定Class中的方法必须声明为static类型，否则无法解析
     * @param id
     * @return
     */
    @GetMapping("/user")
    @SentinelResource(value = USER_RESOURCE_NAME, fallback = "fallbackHandle4GetUser", blockHandler = "blockHandler4GetUser")
    public User getUser(String id) {
        System.out.println(1/0);
        return new User("小珠珠");
    }

    /**
     * getUser接口出现异常时的处理方法
     * 1.该方法一定要是public
     * 2.必须要包含源方法的方法参数、参数顺序，返回类型也要和源方法保持一致
     * 3.方法参数最后可以添加异常处理类参数Throwable
     * @return
     */
    public User fallbackHandle4GetUser(String id, Throwable e) {
        e.printStackTrace();
        return new User("异常处理");
    }

    /**
     * 被流控降级后的处理方法，需要注意：
     * 1.该方法一定要是public
     * 2.必须要包含源方法的方法参数、参数顺序，返回类型也要和源方法保持一致
     * 3.方法参数最后可以添加异常处理类参数BlockException，可以区分是什么规则的处理方法
     * @param id
     * @param e
     * @return
     */
    public User blockHandler4GetUser(String id, BlockException e) {
        e.printStackTrace();
        return new User("被流控---");
    }

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
        // 设置受保护的资源阈值
        rule.setCount(1);
        rules.add(rule);

        // 使用@SentinelResource注解改善接口中定义资源和被流控降级之后的处理方法
        FlowRule rule2 = new FlowRule();
        // 设置被保护的资源
        rule2.setResource(USER_RESOURCE_NAME);
        // 设置流控规则 QPS
        rule2.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // 设置受保护的资源阈值
        rule2.setCount(1);
        rules.add(rule2);

        // 加载配置好的规则
        FlowRuleManager.loadRules(rules);
    }

    /**
     * 测试熔断降级
     * @return
     */
    @GetMapping("/degrade")
    @SentinelResource(value = DEGRADE_RESOURCE_NAME, entryType = EntryType.IN, blockHandler = "degradeHandle")
    public User degrade() {
        throw new RuntimeException("出现异常");
    }

    /**
     * 降级方法，注意最后这个参数BlockException必须要写
     * @param e
     * @return
     */
    public User degradeHandle(BlockException e) {
        return new User("熔断降级");
    }

    /**
     * 降级规则
     */
    @PostConstruct
    public void initDegradeRule() {
        List<DegradeRule> rules = new ArrayList<>();
        DegradeRule rule = new DegradeRule();
        rule.setResource(DEGRADE_RESOURCE_NAME);
        // 熔断策略，异常数
        rule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT);

        // 下面三个条件同时满足就触发熔断，即：在1min之内发起了2次请求，失败了2次
        // 1.触发熔断的异常数
        rule.setCount(2);
        // 2.触发熔断的最小请求数
        rule.setMinRequestAmount(2);
        // 3.统计时长，页面上没有该设置，单位ms，如果设置时间太短不好测试，默认是1s，但在这里先用1min测试
        rule.setStatIntervalMs(60*1000);

        // 设置熔断的持续时长，单位s，即在熔断时间10s内后面的请求会直接进入降级处理，
        // 超过10s以后进入半开状态，恢复正常接口的调用，但是如果在第一次调用之后直接出现了异常，则会再次熔断直接进入降级处理，而不会去管上面3个条件，再过10s之后，如果第一次又出现异常，再次进入半开状态，依次类推
        rule.setTimeWindow(10);

        rules.add(rule);
        DegradeRuleManager.loadRules(rules);
    }
}
