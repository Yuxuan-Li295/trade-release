package com.shangan.tradegoods.db.dao;

import com.shangan.tradegoods.db.model.Goods;

/**
 * Interface for product database operations.
 */
public interface GoodsDao {
    /**
     * Adds a new product.
     */
    boolean insertGoods(Goods goods);

    /**
     * Removes a product by its ID.
     */
    boolean deleteGoods(long id);

    /**
     * Finds a product by its ID.
     */
    Goods queryGoodsById(long id);

    /**
     * Modifies the product details
     */
    boolean updateGoods(Goods goods);

    boolean lockStock(long id);

    boolean deductStock(long id);

    boolean revertStock(long id);
}















