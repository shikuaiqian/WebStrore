package com.cskaoyan.dao;

import com.cskaoyan.bean.Order;
import com.cskaoyan.bean.OrderItem;


import java.sql.SQLException;
import java.util.List;

public interface OrderDao {
    boolean saveOrder(Order order) throws SQLException;

    void saveOrderItem(OrderItem item) throws SQLException;

    List<Order> findAllOrders(int uid) throws SQLException;

    boolean setOrderState(int oid, int state) throws SQLException;

    int findPartOrders() throws SQLException;

    List<Order> findPartSearchProduct(int limit, int offset) throws SQLException;

    List<OrderItem> findOrderItemsByOid(int oid) throws SQLException;

    boolean deleteOrder(int oid) throws SQLException;
}
