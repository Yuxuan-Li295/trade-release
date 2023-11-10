package com.shangan.tradelightningdeal.service.Impl;

import com.shangan.tradelightningdeal.db.dao.SeckillActivityDao;
import com.shangan.tradelightningdeal.db.model.SeckillActivity;
import com.shangan.tradelightningdeal.service.SeckillActivityService;
import com.shangan.tradelightningdeal.utils.RedisWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SeckillActivityServiceImpl implements SeckillActivityService {
    @Autowired
    private SeckillActivityDao seckillActivityDao;

    @Autowired
    private RedisWorker redisWorker;


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

    public boolean processSeckillSolution(long seckillActivityId) {
        //Build the redis key for the stock
        String stockKey = "stock:" + seckillActivityId;
        //Try to deduct stock using Redisworker's atomic LUa script method
        boolean stockDeducted = redisWorker.stockDeductCheck(stockKey);
        if (!stockDeducted) {
            // Stock deduction failed, either due to insufficient stock or other reasons
            log.info("Unfortunately, the purchase failed. Better luck next time!");
            return false;
        }
        //Query the seckill activity info
        SeckillActivity seckillActivity = seckillActivityDao.querySeckillActivityById(seckillActivityId);
        if (seckillActivity == null) {
            log.error("Cannot find the corresponding seckill activity for seckillActivity Id:{}", seckillActivityId);
            throw new RuntimeException("Cannot find the corresponding seckill Activity");
        }
        int stockNumber = seckillActivity.getAvailableStock();
        if (stockNumber > 0) {
            log.info("Congratulations! Purchase successful!");
            seckillActivityDao.updateAvailableStockByPrimaryKey(seckillActivityId);
            return true;
        } else {
            log.info("Unfortunately, the purchase failed. Better luck next time!");
            return false;
        }
    }
}
