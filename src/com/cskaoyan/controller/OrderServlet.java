package com.cskaoyan.controller;

import com.cskaoyan.bean.Order;
import com.cskaoyan.bean.User;
import com.cskaoyan.servcie.OrderService;
import com.cskaoyan.servcie.impl.OrderServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "OrderServlet", value = "/user/OrderServlet")
public class OrderServlet extends HttpServlet {

    OrderService service = new OrderServiceImpl();
    private String contextPath;

    @Override
    public void init() throws ServletException {
        contextPath = getServletContext().getContextPath();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String op = request.getParameter("op");
        if (null != op) {
            switch (op) {
                case "placeOrder":
                    placeOrder(request, response);
                    break;
                case "myoid":
                    showOrders(request, response);
                    break;
                case "cancelOrder":
                    cancelOrder(request, response);
                    break;

            }
        }
    }

    private void cancelOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (null == user) {
            response.sendRedirect(contextPath + "/user/login.jsp");
            return;
        }

        String state = request.getParameter("state");
        String oid = request.getParameter("oid");

        try {
            boolean b = service.cancelOrder(oid, state);
            if (b) {
                response.sendRedirect("/user/OrderServlet?op=myoid");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void showOrders(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User user = (User) request.getSession().getAttribute("user");
        if (null == user) {
            response.sendRedirect(contextPath + "/user/login.jsp");
            return;
        }
        int uid = user.getUid();
        try {
            List<Order> orders = service.findAllOrders(uid);
            request.setAttribute("orders", orders);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("myOrders.jsp").forward(request, response);
    }

    private void placeOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (null == user) {
            response.sendRedirect(contextPath + "/user/login.jsp");
            return;
        }

        Order order = new Order();
        // 拿到复选框已选择的购物车项
        String[] pids = request.getParameterValues("pid");
        int uid = user.getUid();
        try {
            BeanUtils.populate(order, request.getParameterMap());
            String message = service.placeOrder(order, pids, uid);
            if ("OK".equals(message)) {
                // 跳转到订单
                response.sendRedirect(contextPath+"/user/OrderServlet?op=myoid");
            } else {
                response.getWriter().write(message);
                response.setHeader("refresh", "1;" + contextPath + "/user/CartServlet?op=findCart");
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
