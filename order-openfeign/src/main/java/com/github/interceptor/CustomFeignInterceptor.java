package com.github.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : cymin
 * @date : 2021.11.07 下午4:26
 */
public class CustomFeignInterceptor implements RequestInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomFeignInterceptor.class);

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("aaa", "aaa");
        requestTemplate.query("bbb", "1111");
        requestTemplate.uri("/get/999");
        LOGGER.info("feign 拦截器");
    }
}
