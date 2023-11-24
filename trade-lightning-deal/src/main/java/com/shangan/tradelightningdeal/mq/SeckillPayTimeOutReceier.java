package com.shangan.tradelightningdeal.mq;

import com.alibaba.fastjson.JSON;
import com.shangan.tradecommon.service.LimitBuyService;
import com.shangan.tradelightningdeal.client.OrderFeignClient;
import com.shangan.tradelightningdeal.client.model.Order;
import com.shangan.tradelightningdeal.service.SeckillActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SeckillPayTimeOutReceier {

    @Autowired
    private LimitBuyService limitBuyService;

    @Autowired
    private SeckillActivityService seckillActivityService;

    @Autowired
    private OrderFeignClient orderFeignClient;

    @RabbitListener(queues = "seckill.order.pay.status.check.queue")
    public void process(String message) {
        Order order = JSON.parseObject(message, Order.class);
        if (order.getActivityType() != 1) {
            return;
        }
        Order orderDB = orderFeignClient.queryOrder(order.getId());
        //1->waiting for pay 2. Pay success
        if (orderDB.getStatus() == 1) {
            log.info("The order id:{} timeout! Order CLosed!", orderDB.getId());
            //Remove from the limited buy list
            limitBuyService.removeLimitMember(order.getActivityId(), order.getUserId());
            //Revert Stock
            seckillActivityService.revertStock(order.getActivityId());
            orderDB.setStatus(99);
            orderFeignClient.updateOrder(orderDB);
        }
    }
}