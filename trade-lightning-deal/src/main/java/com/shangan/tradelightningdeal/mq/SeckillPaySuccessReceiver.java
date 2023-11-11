package com.shangan.tradelightningdeal.mq;

import com.alibaba.fastjson.JSON;
import com.shangan.tradelightningdeal.service.SeckillActivityService;
import com.shangan.tradeorder.db.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SeckillPaySuccessReceiver {
    @Autowired
    private SeckillActivityService seckillActivityService;

    @RabbitListener(queues = "seckill.order.pay.success.queue")
    public void process(String message) {
        log.info("秒杀支付成功！消息处理中，接受到的内容为:{}",message);
        Order order = JSON.parseObject(message, Order.class);
        seckillActivityService.deductStock(order.getActivityId());
    }
}
