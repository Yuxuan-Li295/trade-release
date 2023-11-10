package com.shangan.tradewebportal;

import com.shangan.tradelightningdeal.utils.RedisWorker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
    @Autowired
    public RedisWorker redisWorker;

    @Test
    public void testSetKeyValue() {
        String key = "Yuxuan";
        String value = "The best SDE";
        //Call the method to set
        redisWorker.setKeyValue(key, value);
    }

    @Test
    public void testGetValueByKey() {
        String key = "Yuxuan";
        String expectedValue = "The best SDE";
        String actualValue = redisWorker.getValueByKey(key);
        System.out.println(actualValue);
        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void setStockTest() {
        redisWorker.setValue("stock:4", 10L);
    }

    @Test
    public void stockCheckTest() {
        redisWorker.stockDeductCheck("stock:4");
        System.out.println(redisWorker.getValueByKey("stock:4"));
    }

}
