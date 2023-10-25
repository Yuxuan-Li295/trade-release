package com.shangan.tradegoods.service;

import com.shangan.tradegoods.db.model.Goods;

import java.util.List;

public interface SearchService {
    /*
     * Add goods to ES
     */
    void addGoodsToES(Goods goods);

    List<Goods> searchGoodsList(String keyword, int from, int size);
}
