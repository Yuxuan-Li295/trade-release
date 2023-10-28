package com.shangan.tradeorder;

import com.shangan.tradeorder.db.dao.OrderDao;
import com.shangan.tradeorder.db.model.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderTest {

    @Autowired
    private OrderDao orderDao;

    @Test
    public void insertGoodsTest() {
        Order order = new Order();
        order.setUserId(191491001L);
        order.setGoodsId(1L);
        order.setPayTime(new Date());
        order.setPayPrice(66666);
        boolean insertResult = orderDao.insertOrder(order);
        assertTrue(insertResult);
        assertTrue(order.getId() != null);
        Order retrieveOrder = orderDao.getOrderById(order.getId());
        assertEquals(order.getUserId(),retrieveOrder.getUserId());
        assertEquals(order.getGoodsId(),retrieveOrder.getGoodsId());
        assertEquals(order.getPayPrice(), retrieveOrder.getPayPrice());
        assertNotNull(retrieveOrder.getPayTime());
    }



}
