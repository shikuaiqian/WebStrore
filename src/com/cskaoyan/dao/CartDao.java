package com.cskaoyan.dao;

import com.cskaoyan.bean.ShoppingCart;
import com.cskaoyan.bean.ShoppingItem;

import java.sql.SQLException;
import java.util.List;

public interface CartDao {
    boolean saveShoppingItem(ShoppingItem item) throws SQLException;

    List<ShoppingItem> findCartItemsByUid(int uid) throws SQLException;

    ShoppingCart createCart(int uid) throws SQLException;

    ShoppingCart findCartByUid(int uid) throws SQLException;

    boolean updateShoppingItemCount(ShoppingItem item) throws SQLException;

    boolean deleteItem(String itemId, String uid) throws SQLException;

    void clearCart(int uid) throws SQLException;

    List<ShoppingItem> findCartItemsBySid(int sid) throws SQLException;

    ShoppingItem findCartItemByUidWithPid(int i, int uid) throws SQLException;
}
