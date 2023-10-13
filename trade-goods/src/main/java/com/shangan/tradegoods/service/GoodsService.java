package com.shangan.tradegoods.service;


import com.shangan.tradegoods.db.model.Goods;

/**
 * 商品相关服务接口
 */
public interface GoodsService {
    /**
     * 插入一个商品
     *
     * @param goods
     * @return
     */
    boolean insertGoods(Goods goods);

    Goods queryGoodsById(long id);

}
