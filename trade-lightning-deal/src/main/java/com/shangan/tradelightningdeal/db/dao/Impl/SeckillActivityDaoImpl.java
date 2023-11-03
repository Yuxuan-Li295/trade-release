package com.shangan.tradelightningdeal.db.dao.Impl;

import com.shangan.tradelightningdeal.db.dao.SeckillActivityDao;
import com.shangan.tradelightningdeal.db.mappers.SeckillActivityMapper;
import com.shangan.tradelightningdeal.db.model.SeckillActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeckillActivityDaoImpl implements SeckillActivityDao {

    @Autowired
    private SeckillActivityMapper seckillActivityMapper;

    @Override
    public boolean insertSeckillActivity(SeckillActivity seckillActivity) {
        int result = seckillActivityMapper.insert(seckillActivity);
        if (result > 0) {
            return true;
        }
        return false;
    }

    @Override
    public SeckillActivity querySeckillActivityById(long id) {
        return seckillActivityMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SeckillActivity> queryActivityByStatus(int status) {
        return seckillActivityMapper.queryActivityByStatus(status);
    }
}
