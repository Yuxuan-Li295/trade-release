package com.shangan.tradeorder.service;

import com.shangan.tradeorder.db.model.Order;

public interface OrderService {
    Order createOrder(long userId, long goodsId);

    Order queryOrder(long orderId);

    void payOrder(long orderId);
}
