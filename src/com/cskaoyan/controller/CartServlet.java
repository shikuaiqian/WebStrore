package com.cskaoyan.controller;

import com.cskaoyan.bean.ShoppingCart;
import com.cskaoyan.bean.ShoppingItem;
import com.cskaoyan.bean.User;
import com.cskaoyan.servcie.CartService;
import com.cskaoyan.servcie.impl.CartServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;

@WebServlet(name = "CartServlet", value = "/user/CartServlet")
public class CartServlet extends HttpServlet {

    CartService service = new CartServiceImpl();
    private String contextPath;

    @Override
    public void init() throws ServletException {
        contextPath = getServletContext().getContextPath();
    }

      /*switch (op) {
                case "findCart":
                    findCart(request, response);
                    break;
                case "addCart":
                    addCart(request, response);
                    break;
                case "delItem":
                    delItem(request, response);
                    break;
            }*/

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        if (null != op) {


            try {
                Method declaredMethod = this.getClass().getDeclaredMethod(op, HttpServletRequest.class, HttpServletResponse.class);
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(this,request,response);

            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }
    }

    private void delItem(HttpServletRequest request, HttpServletResponse response) {
        String uid = request.getParameter("uid");
        String itemId = request.getParameter("itemId");
        try {
            boolean b = service.deleteItem(itemId, uid);
            if (b) {
                response.setHeader("refresh", "0;" + contextPath + "/user/CartServlet?op=findCart");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uid = request.getParameter("uid");
        // 拿到当前加入购物车商品
        ShoppingItem item = new ShoppingItem();
        try {
            BeanUtils.populate(item, request.getParameterMap());

            // 加入到购物车，写入到数据库
            service.saveShoppingItem(item, uid);

            response.setHeader("refresh", "0;" + contextPath + "/user/CartServlet?op=findCart");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            response.getWriter().println("IllegalAccessException"+e.getCause());
            e.printStackTrace(response.getWriter());

        } catch (IllegalStateException e) {
            e.printStackTrace();
            response.getWriter().write(e.getMessage());
            response.setHeader("refresh", "1;" + contextPath + "/ProductServlet?op=findProductByPid&pid=" + item.getPid());
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            response.getWriter().println("InvocationTargetException"+e.getCause());
            e.printStackTrace(response.getWriter());

        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("SQLException");
            e.printStackTrace(response.getWriter());

        }
    }

    private void findCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        // 如果用户未登录先去登录
        if (null == user) {
            response.sendRedirect(contextPath + "/user/login.jsp");
            return;
        }

        int uid = user.getUid();
        // 每次查看购物车重新查一遍
        try {
            ShoppingCart cart = service.findCart(uid);
            session.setAttribute("shoppingCart", cart);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("shoppingcart.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
