package com.github.mapper;

import com.github.model.Order;
import com.github.model.OrderExample;

import java.util.List;

public interface OrderMapper {
    long countByExample(OrderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    List<Order> selectByExample(OrderExample example);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
}