package com.shangan.tradeorder;

import com.shangan.tradecommon.utils.SnowflakeIdWorker;
import com.shangan.tradeorder.db.dao.OrderDao;
import com.shangan.tradeorder.db.model.Order;
import com.shangan.tradeorder.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderTest {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderService orderService;

    private SnowflakeIdWorker snowFlake = new SnowflakeIdWorker(6, 8);

    @Test
    public void insertGoodsTest() {
//        Order order = new Order();
//        order.setUserId(19491001L);
//        order.setGoodsId(2L);
//        order.setStatus(1);
//        order.setPayTime(new Date());
//        order.setPayPrice(12345);
//        boolean insertResult = orderDao.insertOrder(order);
//        assertTrue(insertResult);
//        assertTrue(order.getId() != null);
//        Order retrieveOrder = orderDao.getOrderById(order.getId());
//        assertEquals(order.getUserId(),retrieveOrder.getUserId());
//        assertEquals(order.getGoodsId(),retrieveOrder.getGoodsId());
//        assertEquals(order.getPayPrice(), retrieveOrder.getPayPrice());
//        assertNotNull(retrieveOrder.getPayTime());
        for (int i = 0; i < 100; i++) {
            System.out.println("Generate Snowflake Distributed Id Test:");
            Order order = new Order();
            order.setId(snowFlake.nextId() + 1);
            order.setUserId(23L + i);
            order.setGoodsId(1044L);
            order.setPayTime(new Date());
            order.setPayPrice(1999);
            order.setStatus(1);
            order.setActivityType(1);
            order.setCreateTime(new Date());
            boolean insertResult = orderDao.insertOrder(order);
            System.out.println(insertResult);
        }
    }

    @Test
    public void insertGoodsTest1() {
        Order order = new Order();
        order.setId(23L);
        order.setUserId(2L);
        order.setGoodsId(1044L);
        order.setPayTime(new Date());
        order.setPayPrice(999);
        order.setStatus(1);
        order.setActivityType(1);
        order.setCreateTime(new Date());
        boolean insertResult = orderDao.insertOrder(order);
        System.out.println(insertResult);
    }

    @Test
    public void insertGoodsRabbit() {
        orderService.createOrder(1L, 1045L);
    }


}