package com.shangan.tradewebmanager.controller;

import com.shangan.tradegoods.db.model.Goods;
import com.shangan.tradegoods.service.GoodsService;
import com.shangan.tradelightningdeal.db.model.SeckillActivity;
import com.shangan.tradelightningdeal.service.SeckillActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Slf4j
@Controller
public class ManagerController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private SeckillActivityService seckillActivityService;


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
     *
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

    @RequestMapping("/addSkillActivity")
    public String addSkillActivity() {
        return "add_skill_activity";
    }

    @RequestMapping("/addSkillActivityAction")
    public String addSkillActivityAction(@RequestParam("activityName") String activityName,
                                         @RequestParam("goodsId") long goodsId,
                                         @RequestParam("startTime") String startTime,
                                         @RequestParam("endTime") String endTime,
                                         @RequestParam("availableStock") int availableStock,
                                         @RequestParam("seckillPrice") int seckillPrice,
                                         @RequestParam("oldPrice") int oldPrice,
                                         Map<String, Object> resultMap) {
        //Create the SeckillActivity object and sets its properties
        SeckillActivity seckillActivity = new SeckillActivity();
        seckillActivity.setActivityName(activityName);
        seckillActivity.setGoodsId(goodsId);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            startTime = startTime.substring(0,10) + " " + startTime.substring(11);
            endTime = endTime.substring(0,10) + " " + endTime.substring(11);
            seckillActivity.setStartTime(format.parse(startTime));
            seckillActivity.setEndTime(format.parse(endTime));
        } catch (ParseException e) {
            log.error("Invalid date format.",e);
            return "500";
        }
        seckillActivity.setActivityStatus(1);
        seckillActivity.setLockStock(0);
        seckillActivity.setSeckillPrice(seckillPrice);
        seckillActivity.setAvailableStock(availableStock);
        seckillActivity.setOldPrice(oldPrice);
        seckillActivity.setCreateTime(new Date());
        boolean isAdded = seckillActivityService.insertSeckillActivity(seckillActivity);
        if (!isAdded) {
            log.error("There is an error when adding the seckill activity action");
            return "500";
        }
        resultMap.put("seckillActivity",seckillActivity);
        return "add_skill_activity";
    }

}
