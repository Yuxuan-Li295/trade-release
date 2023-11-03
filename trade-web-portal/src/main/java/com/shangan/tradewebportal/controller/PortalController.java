package com.shangan.tradewebportal.controller;

import com.alibaba.fastjson.JSON;
import com.shangan.tradegoods.db.dao.GoodsDao;
import com.shangan.tradegoods.db.model.Goods;
import com.shangan.tradegoods.service.GoodsService;
import com.shangan.tradegoods.service.SearchService;
import com.shangan.tradewebportal.util.CommonUtils;
import com.shangan.tradeorder.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import com.shangan.tradeorder.db.model.Order;
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
    private GoodsDao goodsDao;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private SearchService searchService;

    @Autowired
    private OrderService orderService;


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
        ModelAndView modelAndView = new ModelAndView();
        try {
            log.info("buy userId={}, goodsId={}", userId, goodsId);
            //Fetch the goods from the database.
            Goods goods = goodsDao.queryGoodsById(goodsId);
            if (goods == null) {
               modelAndView.addObject("resultInfo","商品不存在，下单失败");
               modelAndView.setViewName("error");
               return modelAndView;
            }
            Order order = orderService.createOrder(userId,goodsId);
            //Create a new order using OrderService
            if (order != null) {
                modelAndView.addObject("order",order);
                modelAndView.addObject("resultInfo","下单成功");
                modelAndView.setViewName("buy_result");
            } else {
                modelAndView.addObject("resultInfo","创建订单失败");
                modelAndView.setViewName("error");
            }
        } catch (Exception e) {
            log.error("Error occurred while hanlding purchase", e);
            modelAndView.addObject("resultInfo", "下单异常：原因" + e.getMessage());
            modelAndView.setViewName("error");
        }
        return modelAndView;
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

    @RequestMapping("/order/query/{orderId}")
    public String orderQuery(Map<String, Object> resultMap, @PathVariable long orderId) {
        Order order = orderService.queryOrder(orderId);
        if (order != null) {
            log.info("Order ID: {} | Order Details: {}", orderId, JSON.toJSON(order));
        }
        resultMap.put("order",order);
        String displayPrice = CommonUtils.changeF2Y(order.getPayPrice());
        resultMap.put("orderShowPrice",displayPrice);
        return "order_detail";
    }

    @RequestMapping("/order/payOrder/{orderId}")
    public String payOrder(Map<String, Object> resultMap, @PathVariable long orderId) {
        try {
            orderService.payOrder(orderId);
            return "redirect:/order/query/" + orderId;
        } catch (IllegalArgumentException e) {
            resultMap.put("errorInfo", e.getMessage());
        } catch (IllegalStateException e) {
            resultMap.put("errorInfo", e.getMessage());
        } catch (Exception e) {
            resultMap.put("errorInfo", "支付过程中发生未知错误");
        }
        return "error";
    }

}
