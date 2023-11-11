package com.shangan.tradelightningdeal.service;

import com.shangan.tradelightningdeal.db.model.SeckillActivity;
import com.shangan.tradeorder.db.model.Order;

import java.util.List;

public interface SeckillActivityService {
    boolean insertSeckillActivity(SeckillActivity seckillActivity);

    SeckillActivity querySeckillActivityById(long id);

    List<SeckillActivity> queryActivityByStatus(int status);

    boolean processSeckillReqBase(long seckillActivityId);

    Order processSeckillSolution(long userId, long seckillActivityId);

    boolean lockStock(long id);
    boolean deductStock(long id);
    boolean revertStock(long id);
}
