package com.shangan.tradewebmanager.client;


import com.shangan.tradecommon.model.TradeResultDTO;
import com.shangan.tradewebmanager.client.model.Order;
import com.shangan.tradewebmanager.client.model.SeckillActivity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "trade-lightning-deal",contextId = "trade-web-portal")
public interface SeckillActivityFeignClient {
    @RequestMapping("/seckill/insertSeckillActivity")
    boolean insertSeckillActivity(@RequestParam("seckillActivity") SeckillActivity seckillActivity);

    @RequestMapping("/seckill/querySeckillActivityById")
    SeckillActivity querySeckillActivityById(@RequestParam("id") long id);


    @RequestMapping("/seckill/queryActivityByStatus")
    List<SeckillActivity> queryActivityByStatus(@RequestParam("status") int status);

    @RequestMapping("/seckill/seckillActivityId")
    boolean processSeckillReqBase(@RequestParam("seckillActivity") long seckillActivityId);

    @RequestMapping("/seckill/processSeckill")
    TradeResultDTO<Order> processSeckillSolution(@RequestParam("userId") long userId, @RequestParam("seckillActivityId") long seckillActivityId);

    @RequestMapping("/seckill/lockStock")
    boolean lockStock(@RequestParam("id") long id);

    @RequestMapping("/seckill/deductStock")
    boolean deductStock(@RequestParam("id") long id);

    @RequestMapping("/seckill/revertStock")
    boolean revertStock(@RequestParam("id") long id);

    @RequestMapping("/seckill/pushSeckillActivityInfoToCache")
    void pushSeckillActivityInfoToCache(@RequestParam("id") long id);

    @RequestMapping("/seckill/insertSeckillActivity")
    TradeResultDTO<String> addSeckillActivity(@RequestParam("seckillActivity") SeckillActivity seckillActivity);


}
