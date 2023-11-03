package com.shangan.tradeorder.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderMessageSender {

    /*
    是否使用构造函数？
     */
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendOrderCreateMessage(String message) {
        log.info("发送订单创建信息: {}", message);
        amqpTemplate.convertAndSend("order-event-exchange","order.create", message);
    }

}
