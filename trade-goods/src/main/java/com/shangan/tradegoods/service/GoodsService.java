package com.shangan.tradegoods.service;


import com.shangan.tradegoods.db.model.Goods;

/**
 * Service interface for product-related operations
 */
public interface GoodsService {
    /**
     * Add a new product into database
     */
    boolean insertGoods(Goods goods);

    /**
     * Retrieves product details using its ID
     */
    Goods queryGoodsById(long id);


}
