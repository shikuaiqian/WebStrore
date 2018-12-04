package com.cskaoyan.dao.impl;

import com.cskaoyan.bean.ShoppingCart;
import com.cskaoyan.bean.ShoppingItem;
import com.cskaoyan.dao.CartDao;
import com.cskaoyan.utils.MyC3P0DataSouce;
import com.cskaoyan.utils.TransactionUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CartDaoImpl implements CartDao {
    @Override
    public ShoppingItem findCartItemByUidWithPid(int pid, int uid) throws SQLException {
        Connection connection = TransactionUtil.get();
        QueryRunner queryRunner = new QueryRunner();
        return queryRunner.query(connection, "select * from shoppingItem where pid = ? and sid = (select sid from shoppingCart where uid = ?)",
                new BeanHandler<>(ShoppingItem.class), pid, uid);
    }

    @Override
    public void clearCart(int uid) throws SQLException {
        Connection connection = TransactionUtil.get();
        QueryRunner queryRunner = new QueryRunner();
        queryRunner.update(connection,"delete from shoppingItem where sid = (select sid from shoppingCart where uid = ?)", uid);
    }

    @Override
    public boolean deleteItem(String itemId, String uid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(MyC3P0DataSouce.getDataSource());
        int update = queryRunner.update("delete from shoppingItem where itemId = ? and sid = (select sid from shoppingCart where uid = ?)", itemId, uid);
        return 1 == update;
    }

    @Override
    public boolean updateShoppingItemCount(ShoppingItem item) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(MyC3P0DataSouce.getDataSource());
        int update = queryRunner.update("update shoppingItem set snum = snum + ? where sid = ? and pid = ?",
                item.getSnum(), item.getSid(), item.getPid());
        return 1 == update;
    }

    @Override
    public ShoppingCart findCartByUid(int uid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(MyC3P0DataSouce.getDataSource());
        return queryRunner.query("select * from shoppingcart where uid = ?", new BeanHandler<>(ShoppingCart.class), uid);
    }

    @Override
    public ShoppingCart createCart(int uid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(MyC3P0DataSouce.getDataSource());
        queryRunner.update("INSERT INTO shoppingcart (sid, uid) VALUES (null, ?)", uid);
        return queryRunner.query("select * from shoppingCart where uid = ?", new BeanHandler<>(ShoppingCart.class), uid);
    }

    @Override
    public List<ShoppingItem> findCartItemsBySid(int sid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(MyC3P0DataSouce.getDataSource());
        return queryRunner.query("select * from shoppingItem where sid = ?",
                new BeanListHandler<>(ShoppingItem.class), sid);
    }

    @Override
    public List<ShoppingItem> findCartItemsByUid(int uid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(MyC3P0DataSouce.getDataSource());
        return queryRunner.query("select * from shoppingItem where sid = (select sid from shoppingCart where uid = ?)",
                new BeanListHandler<>(ShoppingItem.class), uid);
    }

    @Override
    public boolean saveShoppingItem(ShoppingItem item) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(MyC3P0DataSouce.getDataSource());
        int update = queryRunner.update("INSERT INTO shoppingItem (itemId, sid, pid, snum) " +
                        "VALUES (null, ?, ?, ?)",
                item.getSid(), item.getPid(), item.getSnum());
        return 1 == update;
    }
}
