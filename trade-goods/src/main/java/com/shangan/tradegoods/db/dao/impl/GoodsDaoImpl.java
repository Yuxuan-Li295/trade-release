package com.shangan.tradegoods.db.dao.impl;

import com.shangan.tradegoods.db.dao.GoodsDao;
import com.shangan.tradegoods.db.mappers.GoodsMapper;
import com.shangan.tradegoods.db.model.Goods;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Goods Database operation
 */
@Slf4j
@Service
public class GoodsDaoImpl implements GoodsDao {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public boolean insertGoods(Goods goods) {
        int rowsAffectd = goodsMapper.insert(goods);
        log.info("Inserted {} product(s).", rowsAffectd);
        return rowsAffectd > 0;
    }

    @Override
    public boolean deleteGoods(long id) {
        int rowsAffected = goodsMapper.deleteByPrimaryKey(id);
        log.info("Deleted{} product(s) with ID: {}.", rowsAffected, id);
        return rowsAffected > 0;
    }

    @Override
    public Goods queryGoodsById(long id) {
        log.info("Fetching product with ID: {}.", id);
        return goodsMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean updateGoods(Goods goods) {
        int rowsAffected = goodsMapper.updateByPrimaryKey(goods);
        log.info("Updated {} product(s) with ID: {}.", rowsAffected, goods.getId());
        return rowsAffected > 0;
    }
}