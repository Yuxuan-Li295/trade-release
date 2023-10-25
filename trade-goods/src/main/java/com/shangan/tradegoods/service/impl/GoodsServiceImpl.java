package com.shangan.tradegoods.service.impl;

import com.shangan.tradegoods.db.dao.GoodsDao;
import com.shangan.tradegoods.db.model.Goods;
import com.shangan.tradegoods.service.GoodsService;
import com.shangan.tradegoods.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private SearchService searchService;

    @Override
    public boolean insertGoods(Goods goods) {
        boolean res = goodsDao.insertGoods(goods);
        searchService.addGoodsToES(goods);
        return res;
    }

    @Override
    public Goods queryGoodsById(long id) {
        return goodsDao.queryGoodsById(id);
    }
}

