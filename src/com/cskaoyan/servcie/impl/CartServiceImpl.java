package com.cskaoyan.servcie.impl;

import com.cskaoyan.bean.ShoppingCart;
import com.cskaoyan.bean.ShoppingItem;
import com.cskaoyan.dao.CartDao;
import com.cskaoyan.dao.ProductDao;
import com.cskaoyan.dao.impl.CartDaoImpl;
import com.cskaoyan.dao.impl.ProductDaoImpl;
import com.cskaoyan.servcie.CartService;


import java.sql.SQLException;
import java.util.List;

public class CartServiceImpl implements CartService {

    private CartDao dao = new CartDaoImpl();
    private ProductDao productDao = new ProductDaoImpl();

    @Override
    public boolean deleteItem(String itemId, String uid) throws SQLException {
        return dao.deleteItem(itemId, uid);
    }

    @Override
    public ShoppingCart findCart(int uid) throws SQLException {
        // 拿到车
        ShoppingCart cart = dao.findCartByUid(uid);

        if (null != cart) {
            // 拿到购物项
            List<ShoppingItem> cartItems = dao.findCartItemsByUid(uid);

            // 拿到购物项具体的商品
            for (ShoppingItem cartItem : cartItems) {
                cartItem.setProduct(productDao.findProductByPid(cartItem.getPid() + ""));
            }

            cart.setShoppingItems(cartItems);
        }

        return cart;
    }

    @Override
    public boolean saveShoppingItem(ShoppingItem item, String uid) throws SQLException {
        // 先拿 当前用户的购物车
        int intUid = Integer.parseInt(uid);
        ShoppingCart cart = dao.findCartByUid(intUid);
        if (null == cart) {
            // 如果该用户没有创建购物车表，则创建
            cart = dao.createCart(intUid);
        }

        // 如果没有填写购买数量，默认为 1
        if (0 == item.getSnum()) {
            item.setSnum(1);
        } else if (item.getSnum() < 0) {
            throw new IllegalStateException("请输入正确商品数量...");
        }

        // 拿到当前购物车已添加购物项
        int sid = cart.getSid();
        item.setSid(sid);
        List<ShoppingItem> shoppingItems = dao.findCartItemsBySid(sid);
        // 如果有当前商品则更新商品数量
        if (shoppingItems.contains(item)) {
            return dao.updateShoppingItemCount(item);
        } else {
            return dao.saveShoppingItem(item);
        }
    }
}
