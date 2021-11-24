package com.github.mapper;

import com.github.model.Stock;
import com.github.model.StockExample;

import java.util.List;

public interface StockMapper {
    long countByExample(StockExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Stock record);

    int insertSelective(Stock record);

    List<Stock> selectByExample(StockExample example);

    Stock selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Stock record);

    int updateByPrimaryKey(Stock record);

    void deduct(Integer productId);
}