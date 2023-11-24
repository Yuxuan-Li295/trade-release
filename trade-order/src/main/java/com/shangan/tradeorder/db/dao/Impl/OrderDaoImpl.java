package com.shangan.tradeorder.db.dao.Impl;

import com.shangan.tradeorder.db.dao.OrderDao;
import com.shangan.tradeorder.db.mappers.OrderMapper;
import com.shangan.tradeorder.db.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public boolean insertOrder(Order order) {
        if (order.getGoodsId() == null) {
            throw new IllegalArgumentException("goods_id cannot be null");
        } else {
            //insert the order into the database
            return orderMapper.insert(order) > 0;
        }
    }

    @Override
    public Order getOrderById(Long id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean updateOrder(Order order) {
        return orderMapper.updateByPrimaryKey(order) > 0;
    }

    @Override
    public boolean deleteOrderById(Long id) {
        return orderMapper.deleteByPrimaryKey(id) > 0;
    }

}
