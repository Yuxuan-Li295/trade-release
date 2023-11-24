package com.shangan.tradeorder.mq;

import com.alibaba.fastjson.JSON;
import com.shangan.tradecommon.mq.OrderMessageSender;
import com.shangan.tradecommon.service.LimitBuyService;
import com.shangan.tradeorder.db.dao.OrderDao;
import lombok.extern.slf4j.Slf4j;
import com.shangan.tradeorder.db.model.Order;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CreateOrderReceiver {
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderMessageSender orderMessageSender;

    @Autowired
    private LimitBuyService limitBuyService;

    //Process the create order message
    @RabbitListener(queues = "create.order.queue")
    public void process(String message) {
        log.info("创建订单消息处理，接收到消息内容:{}", message);
        Order order = JSON.parseObject(message, Order.class);
        boolean insertResult = orderDao.insertOrder(order);
        if (!insertResult) {
            log.error("order insert error order={}", JSON.toJSONString(order));
            throw new RuntimeException("订单生成失败");
        }
        orderMessageSender.sendPayStatusCheckDelayMessage(JSON.toJSONString(order));

        //创建订单成功，秒杀活动添加对于限购名单
        if (order.getActivityType() == 1) {
            limitBuyService.addLimitMember(order.getActivityId(), order.getUserId());
        }
    }
}