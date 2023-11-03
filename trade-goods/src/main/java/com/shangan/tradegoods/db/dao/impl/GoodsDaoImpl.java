package com.shangan.tradegoods.db.dao.impl;

import com.shangan.tradegoods.db.dao.GoodsDao;
import com.shangan.tradegoods.db.mappers.GoodsMapper;
import com.shangan.tradegoods.db.model.Goods;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class GoodsDaoImpl implements GoodsDao {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public boolean insertGoods(Goods goods) {
        int result = goodsMapper.insert(goods);
        //大于0 表示插入成功
        if (result > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteGoods(long id) {
        int result = goodsMapper.deleteByPrimaryKey(id);
        return result > 0;
    }

    @Override
    public Goods queryGoodsById(long id) {
        Goods goods = goodsMapper.selectByPrimaryKey(id);
        return goods;
    }

    @Override
    public boolean updateGoods(Goods goods) {
        int result = goodsMapper.updateByPrimaryKey(goods);
        return result > 0;
    }

    @Override
    public boolean lockStock(long id) {
        int result = goodsMapper.lockStock(id);
        if (result < 0) {
            log.error("There is an error when lock the stock");
            return false;
        }
        return true;
    }

    /*
    Result < 1 -> number of affected rows < 1, no rows were affected (result < 1)
    Means the operation failed, possibly because there was no stock left.
     */
    @Override
    public boolean deductStock(long id) {
        int result = goodsMapper.deductStock(id);
        if (result < 1) {
            log.error("Deduct stock failed");
            return false;
        }
        return true;
    }

    @Override
    public boolean revertStock(long id) {
        int result = goodsMapper.revertStock(id);
        if (result < 1) {
            log.error("Revert stock failed");
            return false;
        }
        return true;
    }
}