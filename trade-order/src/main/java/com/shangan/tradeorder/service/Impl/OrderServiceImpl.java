package com.shangan.tradeorder.service.Impl;

import com.alibaba.fastjson.JSON;
import com.shangan.tradegoods.db.dao.GoodsDao;
import com.shangan.tradegoods.db.model.Goods;
import com.shangan.tradegoods.service.GoodsService;
import com.shangan.tradeorder.db.dao.OrderDao;
import com.shangan.tradeorder.db.model.Order;
import com.shangan.tradeorder.mq.OrderMessageSender;
import com.shangan.tradeorder.service.OrderService;
import com.shangan.tradeorder.service.RiskBlackListService;
import com.shangan.tradeorder.utils.SnowflakeIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderMessageSender orderMessageSender;

    @Autowired
    private RiskBlackListService riskBlackListService;

    private final SnowflakeIdWorker snowflakeIdWorker;

    public OrderServiceImpl() {
        this.snowflakeIdWorker = new SnowflakeIdWorker(0,0);
    }


    /*
    Optimization:
    1. Avoid race conditions by locking the row in the database for the product.
    2. If the stock is enough, reduce the stock and proceed with the order creation.
    3. If the stock is not enough, throw an exception
    4. After the order is created, send a message to RabbitMQ to check the payment status of the order
     */
    @Transactional (rollbackFor = Exception.class)
    @Override
    public Order createOrder(long userId, long goodsId) {
        //First check whether the user is in the black list or not
        if (riskBlackListService.isUserInBlackList(userId)) {
            log.error("User with ID {} is in the blacklist, cannot create order", userId);
            return null;
        }
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
            throw new RuntimeException("Goods Not Found");
        }
        if (goods.getAvailableStock() <= 0) {
            log.error("Goods stock is not enough for the goods: {} with the userId to be: {}",goodsId, userId);
        }
        if (goods.getAvailableStock() <= 0) {
            log.error("Not enough stock for goodsId: {}", goodsId);
            throw new RuntimeException("Not Enough Stock");
        }
        if (!goodsService.lockStock(goodsId)) {
            log.error("Sorry, this order:{} cannot be locked", JSON.toJSONString(order));
            throw new RuntimeException("Cannot lock this order");
        }
        order.setPayPrice(goods.getPrice());
        orderMessageSender.sendOrderCreateMessage(JSON.toJSONString(order));
        return order;
    }

    @Override
    public Order queryOrder(long orderId) {
        return orderDao.getOrderById(orderId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void payOrder(long orderId) {
        Order order = orderDao.getOrderById(orderId);
        log.info("订单{} 支付中",orderId);
        if (order == null) {
            log.error("订单ID:{} 不存在",orderId);
            throw new RuntimeException("Cannot Pay non-exist order");
        }
        if (order.getStatus() != 1) {
            log.error("订单ID:{} 非可支付状态",orderId);
            throw new RuntimeException("订单状态无法支付");
        }
        log.info("正在付款中...");
        order.setPayTime(new Date());
        order.setStatus(2);
        boolean updateResult = orderDao.updateOrder(order);
        if (!updateResult) {
            log.error("订单ID:{} 无法正常更新",orderId);
            throw new RuntimeException("无法更新订单");
        }
        if (order.getActivityType() == 0) {
            //->Normal goods
            boolean deductResult = goodsService.deductStock(order.getGoodsId());
            if (!deductResult) {
                log.error("不能正常对ID:{} 进行库存扣减",orderId);
                throw new RuntimeException("无法扣减库存");
            }
        } else if (order.getActivityType() == 1) {
            orderMessageSender.sendSeckillPaySuccessMessage(JSON.toJSONString(order));
        }
    }
}