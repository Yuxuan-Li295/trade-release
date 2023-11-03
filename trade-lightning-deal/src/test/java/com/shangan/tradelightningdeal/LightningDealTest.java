package com.shangan.tradelightningdeal;

import com.shangan.tradelightningdeal.db.dao.SeckillActivityDao;
import com.shangan.tradelightningdeal.db.model.SeckillActivity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LightningDealTest {

    @Autowired
    private SeckillActivityDao seckillActivityDao;

    @Test
    public void insertGoodsTest() {
        //Create a simple SeckillActivity object for testing.
        SeckillActivity activity = new SeckillActivity();
        activity.setActivityName("黑色星期五");
        activity.setGoodsId(12345L);
        Date currentDate = new Date();
        activity.setStartTime(currentDate);
        //Set end timet o one week from now
        Calendar calendar = Calendar.getInstance(); //Get Current date
        calendar.setTime(currentDate);
        calendar.add(calendar.DATE,7); //Add 7 days
        Date oneWeekLater = calendar.getTime();
        activity.setEndTime(oneWeekLater);
        activity.setAvailableStock(100);
        activity.setActivityStatus(1);
        activity.setLockStock(0);
        activity.setSeckillPrice(50);
        activity.setOldPrice(100);
        activity.setCreateTime(new Date());
        boolean result = seckillActivityDao.insertSeckillActivity(activity);
        assertTrue(result);
    }
}
