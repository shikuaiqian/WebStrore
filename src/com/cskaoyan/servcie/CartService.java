package com.cskaoyan.servcie;

import com.cskaoyan.bean.ShoppingCart;
import com.cskaoyan.bean.ShoppingItem;

import java.sql.SQLException;

public interface CartService {

    ShoppingCart findCart(int uid) throws SQLException;

    boolean deleteItem(String itemId, String uid) throws SQLException;

    boolean saveShoppingItem(ShoppingItem item, String uid) throws SQLException;
}
