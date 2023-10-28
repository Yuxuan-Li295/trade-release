package com.shangan.tradeorder.db.mappers;

import com.shangan.tradegoods.db.model.Goods;
import com.shangan.tradeorder.db.model.Order;

public interface OrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
}