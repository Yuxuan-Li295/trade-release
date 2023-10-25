package com.shangan.tradewebportal.controller;

import com.alibaba.fastjson.JSON;
import com.shangan.tradegoods.db.model.Goods;
import com.shangan.tradegoods.service.GoodsService;
import com.shangan.tradegoods.service.SearchService;
import com.shangan.tradewebportal.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;


@Slf4j
@Controller
public class PortalController {


    @Autowired
    private GoodsService goodsService;

    @Autowired
    private SearchService searchService;

    @RequestMapping("/")
    public String redirectTohome() {
        return "goods_detail";
    }

    @RequestMapping("/goods_detail")
    public String showHomePage() {
        return "goods_detail";
    }

    @RequestMapping("/goods/{goodsId}")
    public ModelAndView displayProductDetail(@PathVariable long goodsId) {
        ModelAndView mav = new ModelAndView();
        log.info("Received goodsId: {}", goodsId);
        Goods item = goodsService.queryGoodsById(goodsId);
        log.info("Fetching details for Product ID = {}, Details = {}", goodsId, JSON.toJSONString(item));

        if (item != null) {
            String showPrice = CommonUtils.changeF2Y(item.getPrice());
            mav.addObject("goods", item);
            mav.addObject("showPrice", showPrice);
            mav.setViewName("goods_detail");
        } else {
            mav.setViewName("error");
            mav.addObject("errorMessage", "Unable to locate product with ID: " + goodsId);
        }

        return mav;
    }



    @RequestMapping("/buy/{userId}/{goodsId}")
    public ModelAndView handlePurchasRequest(@PathVariable long userId, @PathVariable long goodsId) {

        log.info("buy userId={}, goodsId={}", userId, goodsId);
        //Logic for handling purchase can be added here
        return null;
    }

    @RequestMapping("/search")
    public String pageSearch() {
        return "search";
    }

    @RequestMapping("/searchAction")
    public String executeSearch(@RequestParam("searchWords") String searchWords, Map<String, Object> resultMap) {
        log.info("Execute search for word: {}", searchWords);
        List<Goods> resultList = searchService.searchGoodsList(searchWords,0,10);
        resultMap.put("goodsList", resultList);
        return "search";
    }
}
