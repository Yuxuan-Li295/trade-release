package com.shangan.tradeorder.mq;

/*
创建消息监听器`OrderPayCheckReceiver`来接收`order.delay.queue`中的消息，并进行相应的处理
@RabbitListener`注解来监听特定的队列
 */

import com.alibaba.fastjson.JSON;
import com.shangan.tradegoods.service.GoodsService;
import com.shangan.tradeorder.db.dao.OrderDao;
import com.shangan.tradeorder.db.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class OrderPayCheckReceiver {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private GoodsService goodsService;

    @RabbitListener(queues = "order.pay.status.check.queue")
    public void handleOrderStatus(String rawMessage) {

        log.info("Received Time:" + LocalDateTime.now() + "Content:" + rawMessage);
//        log.debug("Received message: {}", rawMessage);

//        Order receivedOrder = deserializeMessage(rawMessage);
//
//        if (receivedOrder == null) {
//            log.warn("Failed to parse order from message: {}", rawMessage);
//            return;
//        }
//
//        checkAndUpdateOrderStatus(receivedOrder);
    }
//
//    private Order deserializeMessage(String message) {
//        try {
//            return JSON.parseObject(message, Order.class);
//        } catch (Exception e) {
//            log.error("Error deserializing message", e);
//            return null;
//        }
//    }
//
//    private void checkAndUpdateOrderStatus(Order order) {
//        Order storedOrder = orderDao.getOrderById(order.getId());
//        if (storedOrder.getStatus() == 1) {
//            log.info("Order {} is overdue. Closing it now.",storedOrder.getId());
//            closedOrder(storedOrder);
//            releaseLockedStock(storedOrder);
//        }
//
//    }
//
//    private void closedOrder(Order order) {
//        order.setStatus(99);
//        orderDao.updateOrder(order);
//    }
//
//    private void releaseLockedStock(Order order) {
//        goodsService.revertStock(order.getGoodsId());
//    }


}
