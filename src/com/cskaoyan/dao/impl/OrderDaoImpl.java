package com.cskaoyan.dao.impl;

 
import com.cskaoyan.bean.Order;
import com.cskaoyan.bean.OrderItem;
import com.cskaoyan.dao.OrderDao;
import com.cskaoyan.utils.MyC3P0DataSouce;
import com.cskaoyan.utils.TransactionUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    @Override
    public boolean deleteOrder(int oid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(MyC3P0DataSouce.getDataSource());
        queryRunner.update("delete from orderItem where oid = ?", oid);
        int update = queryRunner.update("delete from `order` where oid = ?", oid);
        return 1 == update;
    }

    @Override
    public List<OrderItem> findOrderItemsByOid(int oid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(MyC3P0DataSouce.getDataSource());
        return queryRunner.query("select * from orderItem where oid = ?", new BeanListHandler<>(OrderItem.class), oid);
    }

    @Override
    public List<Order> findPartSearchProduct(int limit, int offset) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(MyC3P0DataSouce.getDataSource());
        return queryRunner.query("select * from `order` limit ? offset ?", new BeanListHandler<>(Order.class), limit, offset);

    }

    @Override
    public int findPartOrders() throws SQLException {
        QueryRunner queryRunner = new QueryRunner(MyC3P0DataSouce.getDataSource());
        Long query = (Long) queryRunner.query("select count(*) from `order`", new ScalarHandler());
        return query.intValue();
    }

    @Override
    public boolean setOrderState(int oid, int state) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(MyC3P0DataSouce.getDataSource());
        int update = queryRunner.update("update `order` set state = ? where oid = ?", state, oid);
        return 1 == update;
    }

    @Override
    public List<Order> findAllOrders(int uid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(MyC3P0DataSouce.getDataSource());
        return queryRunner.query("select * from `order` where uid = ?", new BeanListHandler<>(Order.class), uid);
    }

    @Override
    public void saveOrderItem(OrderItem item) throws SQLException {
        Connection connection = TransactionUtil.get();
        QueryRunner queryRunner = new QueryRunner();
        queryRunner.update(connection, "INSERT INTO orderItem (itemId, oid, pid, buynum) " +
                "VALUES (null, ?, ?, ?)", item.getOid(), item.getPid(), item.getBuynum());

    }

    @Override
    public boolean saveOrder(Order order) throws SQLException {
        Connection connection = TransactionUtil.get();
        QueryRunner queryRunner = new QueryRunner();
        int update = queryRunner.update(connection, "INSERT INTO `order` (oid, money, recipients, tel, address, state," +
                        "  uid) " +
                        "VALUES (?, ?, ?, ?, ?, 1, ?)",
                order.getOid(),
                order.getMoney(),
                order.getRecipients(),
                order.getTel(),
                order.getAddress(),
                order.getUid());
        return 1 == update;
    }
}
