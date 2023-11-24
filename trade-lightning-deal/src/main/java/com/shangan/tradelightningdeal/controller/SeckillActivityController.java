package com.shangan.tradelightningdeal.controller;

import com.alibaba.fastjson.JSON;
import com.shangan.tradecommon.model.TradeResultDTO;
import com.shangan.tradelightningdeal.client.model.Order;
import com.shangan.tradelightningdeal.db.model.SeckillActivity;
import com.shangan.tradelightningdeal.service.SeckillActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@Controller
public class SeckillActivityController {

    @Autowired
    private SeckillActivityService seckillActivityService;

    @GetMapping("/seckill/insertSeckillActivity")
    @ResponseBody
    public boolean insertSeckillActivity(SeckillActivity seckillActivity) {
        log.info("InsertSeckillActivity seckillActivity:{}", JSON.toJSON(seckillActivity));
        return seckillActivityService.insertSeckillActivity(seckillActivity);
    }

    @GetMapping("/seckill/querySeckillActivityById")
    @ResponseBody
    public SeckillActivity querySeckillActivityById(long id) {
        log.info("QuerySeckillActivityById:{}", id);
        return seckillActivityService.querySeckillActivityById(id);
    }

    @GetMapping("/seckill/queryActivityByStatus")
    @ResponseBody
    public List<SeckillActivity> queryActivityByStatus(int status) {
        log.info("queryActivityByStatus status:{}", status);
        return seckillActivityService.queryActivityByStatus(status);
    }

    @GetMapping("/seckill/seckillActivityId")
    @ResponseBody
    public boolean processSeckillReqBase(long seckillActivityId) {
        log.info("processSeckillReqBase seckillActivityId:{}", seckillActivityId);
        return seckillActivityService.processSeckillReqBase(seckillActivityId);
    }

    @GetMapping("/seckill/processSeckill")
    @ResponseBody
    public TradeResultDTO<Order> processSeckill(long userId, long seckillActivityId) {
        TradeResultDTO<Order> res = new TradeResultDTO<>();
        try {
            log.info("processSeckill userId:{} seckillActivityId:{}", userId, seckillActivityId);
            Order order = seckillActivityService.processSeckillSolution(userId, seckillActivityId);
            res.setData(order);
            res.setCode(200);
        } catch (Exception ex) {
            res.setCode(500);
            res.setErrorMessage(ex.getMessage());
        }
        return res;
    }

    @GetMapping("/seckill/lockStock")
    @ResponseBody
    public boolean lockStock(long id) {
        log.info("lockStock id:{}", id);
        return seckillActivityService.lockStock(id);
    }

    @GetMapping("/seckill/deductStock")
    @ResponseBody
    public boolean deductStock(long id) {
        log.info("deductStock id:{}", id);
        return seckillActivityService.deductStock(id);
    }

    @GetMapping("/seckill/revertStock")
    @ResponseBody
    public boolean revertStock(long id) {
        log.info("revertStock id:{}", id);
        return seckillActivityService.revertStock(id);
    }

    @GetMapping("/seckill/pushSeckillActivityInfoToCache")
    @ResponseBody
    void pushSeckillActivityInfoToCache(long id) {
        log.info("pushSeckillActivityInfoToCache id:{}", id);
        seckillActivityService.pushSeckillActivityInfoToCache(id);
    }
}
