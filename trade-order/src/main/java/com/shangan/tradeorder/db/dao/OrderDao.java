package com.shangan.tradeorder.db.dao;
import com.shangan.tradeorder.db.model.Order;


public interface OrderDao {

    //Create: Save a new order
    boolean insertOrder(Order order);

    //Read: Retrieve an order by its ID
    Order getOrderById(Long id);

    //Update: Modify an existing Order's details
    boolean updateOrder(Order order);

    //Delete: Remove an order by its ID
    boolean deleteOrderById(Long id);
}
