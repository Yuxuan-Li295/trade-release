package com.shangan.tradewebportal.controller;

import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.shangan.tradecommon.model.TradeResultDTO;
import com.shangan.tradecommon.utils.RedisWorker;
import com.shangan.tradewebportal.client.GoodsFeignClient;
import com.shangan.tradewebportal.client.OrderFeignClient;
import com.shangan.tradewebportal.client.SeckillActivityFeignClient;
import com.shangan.tradewebportal.client.model.Goods;
import com.shangan.tradewebportal.client.model.Order;
import com.shangan.tradewebportal.client.model.SeckillActivity;
import com.shangan.tradewebportal.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.Resource;
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
    private GoodsFeignClient goodsFeignClient;

    @Autowired
    private OrderFeignClient orderFeignClient;

    @Autowired
    private SeckillActivityFeignClient seckillActivityFeignClient;

    @Autowired
    private RedisWorker redisWorker;

    @Autowired
    private ResourceLoader resourceLoader;

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
        Goods item = goodsFeignClient.queryGoodsById(goodsId);
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
            Goods goods = goodsFeignClient.queryGoodsById(goodsId);
            if (goods == null) {
               modelAndView.addObject("errorInfo","商品不存在，下单失败");
               modelAndView.setViewName("error");
               return modelAndView;
            }
            Order order = orderFeignClient.createOrder(userId,goodsId);
            //Create a new order using OrderService
            if (order != null) {
                modelAndView.addObject("order",order);
                modelAndView.addObject("errorInfo","下单成功");
                modelAndView.setViewName("buy_result");
            } else {
                modelAndView.addObject("errorInfo","创建订单失败 用户已在黑名单中");
                modelAndView.setViewName("error");
            }
        } catch (Exception e) {
            log.error("Error occurred while hanlding purchase", e);
            modelAndView.addObject("errorInfo", "下单异常：原因" + e.getMessage());
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
        List<Goods> resultList = goodsFeignClient.searchGoodsList(searchWords,0,10);
        resultMap.put("goodsList", resultList);
        return "search";
    }

    @RequestMapping("/order/query/{orderId}")
    public String orderQuery(Map<String, Object> resultMap, @PathVariable long orderId) {
        Order order = orderFeignClient.queryOrder(orderId);
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
            orderFeignClient.payOrder(orderId);
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

    /*
Flash Sale Activity Detail Page
 */
    @RequestMapping("/seckill/{seckillId}")
    public String seckillInfo(Map<String, Object> resultMap, @PathVariable long seckillId) {
        SeckillActivity seckillActivity;
        Goods goods;
        long startTotalStatic = System.nanoTime();
        //Locate the static page
        Resource resource = resourceLoader.getResource("classpath:/static/seckill_item_" + seckillId + ".html");
        if (resource.exists()) {
            //Redirect since static page exists
            long endTotalStatic = System.nanoTime();
            log.info("Find static Page! Total execution time: {} nanoseconds", (endTotalStatic - startTotalStatic));
            return "redirect:/seckill_item_" + seckillId + ".html";
        } else {
            long startTotal = System.nanoTime();

            //First try to get the flash sale activity info from Redis
            String activityKey = "seckill:activity" + seckillId;
            String activityJson = redisWorker.getValueByKey(activityKey);
            long startCache = System.nanoTime();
            if (activityJson != null) {
                seckillActivity = JSON.parseObject(activityJson, SeckillActivity.class);
                log.info("Hit the cache for the SeckillActivity:{}", activityJson);
            } else {
                long startDb = System.nanoTime();
                seckillActivity = seckillActivityFeignClient.querySeckillActivityById(seckillId);
                long endDb = System.nanoTime();
                log.info("Database query time for SeckillActivity: {} nanoseconds",(endDb - startDb));
                //Check if the activity exists or not?
                if (seckillActivity == null) {
                    log.error("No seckill activity found with ID:" + seckillId);
                    throw new RuntimeException("Do not found the corresponding seckillInfo");
                }
                //Put the info into Redis
                redisWorker.setValue(activityKey,JSON.toJSONString(seckillActivity));
            }
            long endCache = System.nanoTime();
            log.info("Time Spent in cache for SeckillActivity: {} nanoseconds", (endCache - startCache));
            log.info("SeckillID = {}, seckillActivity={}", seckillId, JSON.toJSON(seckillActivity));
            String newPrice = CommonUtils.changeF2Y(seckillActivity.getSeckillPrice());
            String oldPrice = CommonUtils.changeF2Y(seckillActivity.getOldPrice());
            resultMap.put("seckillActivity", seckillActivity);
            resultMap.put("seckillPrice", newPrice);
            resultMap.put("oldPrice", oldPrice);

            //First try to get the goods info from redis
            String goodsKey = "seckillActivity_goods:" + seckillActivity.getGoodsId();
            String goodsJson = redisWorker.getValueByKey(goodsKey);
            startCache = System.nanoTime();
            if (goodsJson != null) {
                goods = JSON.parseObject(goodsJson, Goods.class);
                log.info("Hit the cahce for goods:{}", goodsJson);
            } else {
                long startDb = System.nanoTime();
                goods = goodsFeignClient.queryGoodsById(seckillActivity.getGoodsId());
                long endDb = System.nanoTime();
                log.info("Database query time for goods: {} nanoseconds", (endDb - startDb));
                if (goods == null) {
                    log.error("There is no corresponding flash sale goods for seckillId:{}, goodsId:{}", seckillId, seckillActivity.getGoodsId());
                    throw new RuntimeException("Error searching falsh sale goods");
                }
                redisWorker.setValue(goodsKey, JSON.toJSONString(goods));
            }
            endCache = System.nanoTime();
            log.info("Time Spent in cache for goods: {} nanoseconds", (endCache - startCache));
            resultMap.put("seckillActivity",seckillActivity);
            resultMap.put("seckillPrice", newPrice);
            resultMap.put("oldPrice", oldPrice);
            resultMap.put("goods",goods);
            long endTotal = System.nanoTime();
            log.info("Total execution time: {} nanoseconds", (endTotal - startTotal));
            return "seckill_item";
        }
    }

    @RequestMapping("/seckill/list")
    public String flashSaleList(Map<String, Object> resultMap) {
        //Fetch all ongoing seckillActivities
        List<SeckillActivity> seckillActivities = seckillActivityFeignClient.queryActivityByStatus(1);

        //Check if the list is empty
        if (seckillActivities.isEmpty()) {
            log.info("No ongoing seckill activites found!");
            return null;
        }

        resultMap.put("seckillActivities", seckillActivities);
        return "seckill_activity_list";
    }

    //    @ResponseBody
//    @RequestMapping("/seckill/buy/{userId}/{seckillId}")
//    public String seckillInfoBase(@PathVariable long seckillId) {
//        //boolean res = seckillActivityService.processSeckillReqBase(seckillId);
//        boolean res = seckillActivityService.processSeckillSolution(seckillId);
//        if (res) {
//            return "商品成功抢购";
//        } else {
//            return "商品已售完 未能成功抢购";
//        }
//    }
    @HystrixCommand(fallbackMethod = "fallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.strategy",value = "SEMAPHORE"),
            @HystrixProperty(name = "execution.isolation.semaphore.maxConcurrentRequests",value = "1")
    })
    @RequestMapping("/seckill/buy/{userId}/{seckillId}")
    public ModelAndView seckill(@PathVariable long userId, @PathVariable long seckillId) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            TradeResultDTO<Order> orderTradeResultDTO = seckillActivityFeignClient.processSeckillSolution(userId,seckillId);
            log.info("seckillActivityFeignClinet get goods in secKill's result:{}", orderTradeResultDTO);
            if (orderTradeResultDTO.getCode() != 200) {
                throw new RuntimeException(orderTradeResultDTO.getErrorMessage());
            }
            modelAndView.addObject("resultInfo", "秒杀抢购成功");
            modelAndView.addObject("order", orderTradeResultDTO.getData());
            modelAndView.setViewName("buy_result");
        } catch (Exception e) {
            log.error("There is an error in seckill buy", e);
            modelAndView.addObject("errorInfo", e.getMessage());
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }

    //Fallback executed when rate limiting
    public ModelAndView fallback(long userId, long seckillId) {
        log.info("Rate Limiting fallback being triggered");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("errorInfo","Rate Limiting Triggered, please try again later");
        modelAndView.setViewName("error");
        return modelAndView;
    }
}
