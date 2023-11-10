package com.shangan.tradelightningdeal.service;

import com.shangan.tradelightningdeal.db.model.SeckillActivity;

import java.util.List;

public interface SeckillActivityService {
    boolean insertSeckillActivity(SeckillActivity seckillActivity);

    SeckillActivity querySeckillActivityById(long id);

    List<SeckillActivity> queryActivityByStatus(int status);

    boolean processSeckillReqBase(long seckillActivityId);

    boolean processSeckillSolution(long seckillActivityId);
}
