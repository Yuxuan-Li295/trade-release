package com.shangan.tradelightningdeal.service.Impl;

import com.alibaba.fastjson.JSON;
import com.shangan.tradegoods.db.model.Goods;
import com.shangan.tradegoods.service.GoodsService;
import com.shangan.tradelightningdeal.db.dao.SeckillActivityDao;
import com.shangan.tradelightningdeal.db.model.SeckillActivity;
import com.shangan.tradelightningdeal.service.SeckillActivityService;
import com.shangan.tradelightningdeal.utils.RedisWorker;
import com.shangan.tradeorder.service.LimitBuyService;
import com.shangan.tradeorder.db.model.Order;
import com.shangan.tradeorder.utils.SnowflakeIdWorker;
import com.shangan.tradeorder.mq.OrderMessageSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class SeckillActivityServiceImpl implements SeckillActivityService {
    @Autowired
    private SeckillActivityDao seckillActivityDao;

    @Autowired
    private RedisWorker redisWorker;

    @Autowired
    private LimitBuyService limitBuyService;

    private final SnowflakeIdWorker snowFlake = new SnowflakeIdWorker(6, 8);

    @Autowired
    private OrderMessageSender orderMessageSender;

    @Autowired
    private GoodsService goodsService;

    @Override
    public boolean insertSeckillActivity(SeckillActivity seckillActivity) {
        return seckillActivityDao.insertSeckillActivity(seckillActivity);
    }

    @Override
    public SeckillActivity querySeckillActivityById(long id) {
        return seckillActivityDao.querySeckillActivityById(id);
    }

    @Override
    public List<SeckillActivity> queryActivityByStatus(int status) {
        return seckillActivityDao.queryActivityByStatus(status);
    }

    @Override
    public boolean processSeckillReqBase(long seckillActivityId) {
        //Query Seckill Activity
        SeckillActivity seckillActivity = seckillActivityDao.querySeckillActivityById(seckillActivityId);
        if (seckillActivity == null) {
            log.error("Cannot find the flashsaale activity: id = {}", seckillActivityId);
            throw new RuntimeException("Cannot find the corresponding flash sale activity");
        }
        //Try update stock directly, if availableStock > 0, minus 1
        int availableOne = seckillActivity.getAvailableStock();
        if (availableOne > 0) {
            //Update successfully, which means snagging successfully
            log.info("商品抢购成功");
            seckillActivityDao.updateAvailableStockByPrimaryKey(seckillActivityId);
            return true;
        } else {
            //Update unsuccessful, because the unavailable stock
            log.info("商品抢购失败，商品已经售完");
            return false;
        }
    }

    public Order processSeckillSolution(long userId, long seckillActivityId) {
        //加入限购校验逻辑
        if (limitBuyService.isInLimitMember(seckillActivityId, userId)) {
            log.error("活动:{} userId = {} 你已经购买了该商品，请勿重复购买", seckillActivityId, userId);
            throw new RuntimeException("检测到用户重复购买商品");
        }
        //Redis 库存校验
        String key = "stock:" + seckillActivityId;
        boolean result = redisWorker.stockDeductCheck(key);
        if (!result) {
            log.error("抱歉，对于活动ID:{},userId={},抢购的人太多，库存不足",seckillActivityId,userId);
            throw new RuntimeException("库存不足!");
        }
        //Query the seckill activity info
        SeckillActivity seckillActivity = seckillActivityDao.querySeckillActivityById(seckillActivityId);
        if (seckillActivity == null) {
            log.error("Cannot find the corresponding seckill activity for seckillActivity Id:{}", seckillActivityId);
            throw new RuntimeException("Cannot find the corresponding seckill Activity");
        }
        //lock the stock
        boolean lockResults = seckillActivityDao.lockStock(seckillActivityId);
        if (!lockResults) {
            log.info("抱歉 对于活动={}, userId={},商品太火了,已售完",seckillActivityId,userId);
            throw new RuntimeException("商品抢购失败");
        }
        log.info("商品成功抢购 恭喜你！！活动ID={}, userId={}",seckillActivityId,userId);
        Order order = new Order();
        long orderId = snowFlake.nextId();
        order.setActivityId(seckillActivityId);
        order.setId(orderId);
        order.setActivityType(1);
        order.setGoodsId(seckillActivity.getGoodsId());
        order.setPayPrice(seckillActivity.getSeckillPrice());
        order.setUserId(userId);
        order.setStatus(1);
        order.setCreateTime(new Date());

        orderMessageSender.sendOrderCreateMessage(JSON.toJSONString(order));
        return order;
    }

    @Override
    public boolean lockStock(long id) {
        log.info("Lock Stock for 秒杀活动 ID:{}", id);
        return seckillActivityDao.lockStock(id);
    }

    @Override
    public boolean deductStock(long id) {
        log.info("Deduct Stock for 秒杀活动 ID:{}", id);
        return seckillActivityDao.deductStock(id);
    }

    @Override
    public boolean revertStock(long id) {
        log.info("Revert stock for 秒杀活动 ID:{}", id);
        return seckillActivityDao.revertStock(id);
    }

    @Override
    public void pushSeckillActivityInfoToCache(long id) {
        //Query SeckillActivity Info
        SeckillActivity seckillActivity = seckillActivityDao.querySeckillActivityById(id);
        if (seckillActivity == null) {
            log.error("Seckill activity not found for id:{}", id);
            throw new RuntimeException("Seckill Activity not found");
        }
        //Put the seckillactivity stock info into Redis
        String stockKey = "stock:" + id;
        redisWorker.setValue(stockKey, Long.valueOf(seckillActivity.getAvailableStock()));
        //Put the complete seckillActivity info into redis
        String activityKey = "seckill:activity" + seckillActivity.getId();
        redisWorker.setValue(activityKey, JSON.toJSONString(seckillActivity));
        //Goods info for the seckill Activity
        Goods goods = goodsService.queryGoodsById(seckillActivity.getGoodsId());
        redisWorker.setValue("seckillActivity_goods:" + seckillActivity.getGoodsId(), JSON.toJSONString(goods));
    }


}