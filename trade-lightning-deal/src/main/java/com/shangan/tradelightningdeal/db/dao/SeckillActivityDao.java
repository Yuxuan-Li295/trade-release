package com.shangan.tradelightningdeal.db.dao;

import com.shangan.tradelightningdeal.db.model.SeckillActivity;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface SeckillActivityDao {
    boolean insertSeckillActivity(SeckillActivity seckillActivity);

    SeckillActivity querySeckillActivityById(long id);

    List<SeckillActivity> queryActivityByStatus(int status);
}
