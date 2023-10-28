package com.shangan.tradeorder.service.Impl;

import com.shangan.tradegoods.db.dao.GoodsDao;
import com.shangan.tradegoods.db.model.Goods;
import com.shangan.tradeorder.db.dao.OrderDao;
import com.shangan.tradeorder.db.model.Order;
import com.shangan.tradeorder.service.OrderService;
import com.shangan.tradeorder.utils.SnowflakeIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private GoodsDao goodsDao;

    private final SnowflakeIdWorker snowflakeIdWorker;

    public OrderServiceImpl() {
        this.snowflakeIdWorker = new SnowflakeIdWorker(0,0);
    }


    @Override
    public Order createOrder(long userId, long goodsId) {
        Order order = new Order();
        //使用SnowflakeIdWorker生成唯一的ID
        long orderId = snowflakeIdWorker.nextId();
        order.setId(orderId);
        order.setUserId(userId);
        order.setGoodsId(goodsId);
        order.setActivityType(0);
        order.setActivityId(0L);
        order.setStatus(1);
        order.setCreateTime(new Date());
        Goods goods = goodsDao.queryGoodsById(goodsId);
        if (goods == null) {
            log.error("Goods not found");
            return null;
        }
        order.setPayPrice(goods.getPrice());
        try {
            boolean insertResult = orderDao.insertOrder(order);
            if (insertResult) {
                log.info("Order created successfully: {}", order);
                return order;
            } else {
                log.error("Failed to create order for userId: {} and goodsId: {}", userId, goodsId);
            }

        } catch (Exception e) {
            log.error("Error occured while creating order for userId: {} and goodsId: {}", userId, goodsId, e);
        }
        return null;
    }

    @Override
    public Order queryOrder(long orderId) {
        return orderDao.getOrderById(orderId);
    }

    @Override
    public void payOrder(long orderId) {
        Order order = orderDao.getOrderById(orderId);
        if (order == null) {
            throw new IllegalArgumentException("订单ID" + orderId + "不存在");
        }
        if (order.getStatus() != 1) {
            throw new IllegalStateException("订单状态不是可支付状态");
        }
        log.info("正在付款中...");
        order.setPayTime(new Date());
        order.setStatus(2);
        orderDao.updateOrder(order);
    }
}
