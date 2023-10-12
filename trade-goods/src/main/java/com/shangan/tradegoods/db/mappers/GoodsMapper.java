package com.shangan.tradegoods.db.mappers;

import com.shangan.tradegoods.db.model.Goods;
import org.springframework.stereotype.Service;


public interface GoodsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKey(Goods record);

    int lockStock(Long id);

    int deductStock(Long id);

    int revertStock(Long id);
}
