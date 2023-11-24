package com.shangan.tradewebmanager.client;

import com.shangan.tradewebmanager.client.model.Goods;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/*
name is the annotation setting for service provider, and also for the corresponding name that registered
in the consul registration center, contextID for the current system name
 */
@FeignClient(name = "trade-goods", contextId = "trade-order")
public interface GoodsFeignClient {

    @RequestMapping("/goods/insertGoods")
    boolean insertGoods(@RequestBody Goods goods);

    @RequestMapping("/goods/queryGoodsById")
    Goods queryGoodsById(@RequestParam("id") long id);

    @RequestMapping("/goods/searchGoodsList")
    List<Goods> searchGoodsList(@RequestParam("keyword") String keyword, @RequestParam("from") int from, @RequestParam("size") int size);

    @RequestMapping("/goods/lockStock")
    boolean lockStock(@RequestParam("id") long id);

    @RequestMapping("/goods/deductStock")
    boolean deductStock(@RequestParam("id") long id);

    @RequestMapping("/goods/revertStock")
    boolean revertStock(@RequestParam("id") long id);
}
