package com.shangan.tradegoods.controller;

import com.alibaba.fastjson.JSON;
import com.shangan.tradegoods.db.model.Goods;
import com.shangan.tradegoods.service.GoodsService;
import com.shangan.tradegoods.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private SearchService searchService;

    /*
    Insert the new goods
     */
    @PostMapping("/goods/insertGoods")
    @ResponseBody
    public boolean insertGoods(@RequestBody Goods goods) {
        log.info("insertGoods goods:{}", JSON.toJSON(goods));
        return goodsService.insertGoods(goods);
    }

    /*
    Query the goods info
     */
    @GetMapping("/goods/queryGoodsById")
    @ResponseBody
    public Goods queryGoodsById(long id) {
        log.info("queryGoodsById id: {}", id);
        return goodsService.queryGoodsById(id);
    }

    /*
    Search according to the keywords
     */
    @GetMapping("/goods/searchGoodsList")
    @ResponseBody
    public List<Goods> searchGoodsList(String keyword, int from, int size) {
        log.info("searchGoodslist keyword: {} from: {}, size: {}", keyword, from, size);
        return searchService.searchGoodsList(keyword,from,size);
    }

    /*
    Goods lock stock
     */
    @GetMapping("/goods/lockStock")
    @ResponseBody
    public boolean lockStock(long id) {
        log.info("Lock stock for id:{}", id);
        return goodsService.lockStock(id);
    }

    /*
    Deduct stock
     */
    @GetMapping("/goods/deductStock")
    @ResponseBody
    public boolean deductStock(long id) {
        log.info("DeductStock for id: {}", id);
        return goodsService.deductStock(id);
    }

    @GetMapping("/goods/revertStock")
    @ResponseBody
    public boolean revertStock(long id) {
        log.info("RevertStock for goods id:{}", id);
        return goodsService.revertStock(id);
    }
}
