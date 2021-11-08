package com.github.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.stereotype.Service;

/**
 * @author : cymin
 * @date : 2021.11.08 下午7:37
 */
@Service
public class OrderService {

    /**
     * sentinel除了把接口定义为资源，业务方法也可以定义为资源
     * 使用@SentinelResource注解把getUsername接口定义为sentinel的资源
     * 注意：使用@SentinelResource注解后，该接口将不会再使用全局异常处理器MyBlockExceptionHandler了，必须通过blockHandler处理异常
     * @return
     */
    @SentinelResource(value = "getUsername", blockHandler = "blockHandler4getUsername")
    public String getUsername() {
        return "小珠珠";
    }

    /**
     * 不要忘记加BlockException e参数!!!!
     * @param e
     * @return
     */
    public String blockHandler4getUsername(BlockException e) {
        return "链路流控";
    }
}
