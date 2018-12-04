package com.cskaoyan.servcie;


import com.cskaoyan.bean.Order;
import com.cskaoyan.bean.OrderItem;
import com.cskaoyan.utils.PageHelper;

import java.sql.SQLException;
import java.util.List;

public interface OrderService {
    String placeOrder(Order order, String[] pids, int uid) throws SQLException;

    List<Order> findAllOrders(int uid) throws SQLException;

    boolean cancelOrder(String oid, String state) throws SQLException;

    PageHelper<Order> findPartOrders(String num) throws SQLException;

    List<OrderItem> findOrderItemsByOid(String oid) throws SQLException;

    boolean deleteOrder(String oid) throws SQLException;
}
