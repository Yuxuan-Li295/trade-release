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
        try {
            //Deserialize the rawMessage to Order Object
            Order order = JSON.parseObject(rawMessage,Order.class);
            if (order == null) {
                log.error("Unable to parse order from the raw message");
                return;
            }
            //Only process normal order, if not then early return
            if (order.getActivityType() != 0) {
                return;
            }
            Long orderId = order.getId();
            Order dbOrder = orderDao.getOrderById(orderId);
            if (dbOrder == null) {
                log.error("Order with ID: {} does not exist", orderId);
            }
            switch (dbOrder.getStatus()) {
                case 1:
                    log.info("Order with ID:{} timeout! Order closed.",orderId);
                    dbOrder.setStatus(99);
                    orderDao.updateOrder(dbOrder);
                    goodsService.revertStock(dbOrder.getGoodsId());
                    break;

                case 2:
                    log.info("Order with ID:{} has been paid", orderId);
                    break;

                default:
                    log.error("Order iwth ID:{} has an unknown status, something must be wrong!", orderId);
            }
        } catch (Exception e) {
            log.error("Error occurred while checking payment status for the order");
        }

    }
}