package com.cskaoyan.servcie.impl;


import com.cskaoyan.bean.*;
import com.cskaoyan.dao.CartDao;
import com.cskaoyan.dao.OrderDao;
import com.cskaoyan.dao.ProductDao;
import com.cskaoyan.dao.UserDao;
import com.cskaoyan.dao.impl.CartDaoImpl;
import com.cskaoyan.dao.impl.OrderDaoImpl;
import com.cskaoyan.dao.impl.ProductDaoImpl;
import com.cskaoyan.dao.impl.UserDaoImpl;
import com.cskaoyan.servcie.OrderService;
import com.cskaoyan.utils.OrderIdUtil;
import com.cskaoyan.utils.PageHelper;
import com.cskaoyan.utils.TransactionUtil;

import java.sql.SQLException;
import java.util.List;

public class OrderServiceImpl implements OrderService {


    private OrderDao orderDao = new OrderDaoImpl();

    @Override
    public boolean deleteOrder(String oid) throws SQLException {
        return orderDao.deleteOrder(Integer.parseInt(oid));
    }

    @Override
    public List<OrderItem> findOrderItemsByOid(String oid) throws SQLException {
        return orderDao.findOrderItemsByOid(Integer.parseInt(oid));
    }

    @Override
    public PageHelper<Order> findPartOrders(String num) throws SQLException {
        PageHelper<Order> page = new PageHelper<>();

        int currentPageNum = Integer.parseInt(num);

        // 总记录数据项数 totalRecordsNum
        page.setTotalRecordsNum(orderDao.findPartOrders(), PageHelper.ORDER_PER_PAGE);

        // 当前页码 currentPageNum
        page.setCurrentPageNum(currentPageNum);

        int limit = PageHelper.ORDER_PER_PAGE;
        int offset = limit * (currentPageNum - 1);

        // list
        List<Order> orders = orderDao.findPartSearchProduct(limit, offset);

        // user
        UserDao userDao = new UserDaoImpl();
        for (Order order : orders) {
            User user = userDao.findUserByUid(order.getUid());
            order.setUser(user);
        }

        page.setRecords(orders);
        return page;
    }

    @Override
    public boolean cancelOrder(String oid, String state) throws SQLException {
        return orderDao.setOrderState(Integer.parseInt(oid), Integer.parseInt(state));
    }

    @Override
    public List<Order> findAllOrders(int uid) throws SQLException {
        return orderDao.findAllOrders(uid);
    }

    @Override
    public String placeOrder(Order order, String[] pids, int uid) {
        CartDao cartDao = new CartDaoImpl();
        ProductDao productDao = new ProductDaoImpl();
        String message="Fial";

        // 生成订单 oid
        int oid = OrderIdUtil.generateOrderId();
        order.setOid(oid);

        try {
            // 开启事务
            TransactionUtil.beginTransaction();

            // 插入订单
            boolean b = orderDao.saveOrder(order);


            // 插入订单项，根据 oid pid 插入订单项中
            for (String sPid : pids) {

                // pid 和 sid (uid -> sid) 选中复选框的 ShoppingItem
                int intPid = Integer.parseInt(sPid);
                ShoppingItem shoppingItem = cartDao.findCartItemByUidWithPid(intPid, uid);

                // pid 查出 product
                Product product = productDao.findProductByPid(sPid);

                // 检查商品库存是否足够
                int buynum = shoppingItem.getSnum();
                if (buynum > product.getPnum()) {

                    // 如果下单数量大于商品库存，无法下单
                    throw new IllegalStateException("商品库存不足，请调整商品数量或者等待补充库存...");
                }

                // 库存足够，减少商品库存
                productDao.minusProductCount(intPid, buynum);

                // 构建 OrderItem 对象
                OrderItem item = new OrderItem();
                item.setOid(oid);
                item.setBuynum(buynum);
                item.setPid(intPid);
                // 存入
                orderDao.saveOrderItem(item);
            }

            // 清空购物车，删除 uid ==> sid 对应的 购物车项
            cartDao.clearCart(order.getUid());

            // 提交事务
            TransactionUtil.commitTransaction();
            message = "OK";
        } catch (Exception e) {
            e.printStackTrace();
            message += e.getMessage()+"--"+e.getCause();
             // 如果发生异常回滚
            TransactionUtil.rollBackAndRelease();
        }
        return message;
    }
}
