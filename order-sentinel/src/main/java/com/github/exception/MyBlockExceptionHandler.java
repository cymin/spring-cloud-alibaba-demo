package com.github.exception;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.domain.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : cymin
 * @date : 2021.11.08 下午6:10
 */
@Component
public class MyBlockExceptionHandler implements BlockExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyBlockExceptionHandler.class);

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws Exception {
        LOGGER.info("规则信息：" + e.getRule().toString());
        Result r = new Result(500, "其他异常");
        if (e instanceof FlowException) {
            r = Result.get(100, "被限流了");
        } else if (e instanceof DegradeException) {
            LOGGER.info("服务降级了");
            r = Result.get(101, "服务降级了");
        } else if (e instanceof AuthorityException) {
            LOGGER.info("授权规则不通过");
            r = Result.get(102, "授权规则不通过");
        } else if (e instanceof SystemBlockException) {
            LOGGER.info("触发系统保护规则了");
            r = Result.get(103, "触发系统保护规则了");
        } else if (e instanceof ParamFlowException) {
            LOGGER.info("热点参数限流了");
            r = Result.get(104, "热点参数限流了");
        }

        //返回json数据
        httpServletResponse.setStatus(500);
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writer().writeValue(httpServletResponse.getWriter(), r);
    }
}
