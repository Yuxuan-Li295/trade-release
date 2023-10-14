package com.shangan.tradewebmanager.controller;

import com.shangan.tradegoods.db.model.Goods;
import com.shangan.tradegoods.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.Map;

@Slf4j
@Controller
public class ManagerController {

    @Autowired
    private GoodsService goodsService;

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    /**
     * Handles the addition of a new goods
     */
    @RequestMapping("/add_goods")
    public String addGoods() {
        return "add_goods";
    }

    /**
     * Handles the addition of a new good
     * @param title
     * @param number
     * @param brand
     * @param image
     * @param description
     * @param price
     * @param keywords
     * @param category
     * @param stock
     * @param resultMap
     * @return
     */
    @RequestMapping("/addGoodsAction")
    public String addGoodsAction(@RequestParam("title") String title,
                                 @RequestParam("number") String number,
                                 @RequestParam("brand") String brand,
                                 @RequestParam("image") String image,
                                 @RequestParam("description") String description,
                                 @RequestParam("price") int price,
                                 @RequestParam("keywords") String keywords,
                                 @RequestParam("category") String category,
                                 @RequestParam("stock") int stock, Map<String, Object> resultMap) {
        Goods goods = new Goods();
        goods.setTitle(title);
        goods.setNumber(number);
        goods.setBrand(brand);
        goods.setImage(image);
        goods.setDescription(description);
        goods.setPrice(price);
        goods.setKeywords(keywords);
        goods.setCategory(category);
        goods.setAvailableStock(stock);
        goods.setStatus(1); //Initially set as available
        goods.setSaleNum(0);
        goods.setCreateTime(new Date());
        boolean isAdded = goodsService.insertGoods(goods);
        log.info("add goods result={}", isAdded);
        resultMap.put("goodsInfo", goods);
        return "add_goods";
    }

}
