package com.cskaoyan.controller;

import com.cskaoyan.bean.Product;
import com.cskaoyan.form.SearchCondition;
import com.cskaoyan.servcie.ProductService;
import com.cskaoyan.servcie.impl.ProductServiceImpl;

import com.cskaoyan.utils.PageHelper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

@WebServlet(name = "ProductServlet", value = "/ProductServlet")
public class ProductServlet extends HttpServlet {

    private ProductService service = new ProductServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        if (null != op) {
            switch (op) {
                case "findProductByPid":
                    findProductByPid(request, response);
                    break;
                case "findProductByCid":
                    findProductsByCid(request, response);
                    break;
                case "findProductsByName":
                    findProductsByName(request, response);
                    break;
            }
        }
    }

    private void findProductsByName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SearchCondition condition = new SearchCondition();
        String num = request.getParameter("num");
        if (null == num || num.isEmpty() || "0".equals(num)) num = "1";
        try {
            BeanUtils.populate(condition, request.getParameterMap());
            PageHelper<Product> page = service.simpleConditionSearch(condition, num);

            request.setAttribute("condition", condition);
            request.setAttribute("page", page);
            request.getRequestDispatcher("/searchProducts.jsp").forward(request, response);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    private void findProductsByCid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SearchCondition condition = new SearchCondition();
        String num = request.getParameter("num");
        if (null == num || num.isEmpty()) num = "1";
        try {
            BeanUtils.populate(condition, request.getParameterMap());
            PageHelper<Product> page = service.findProductByCid(condition, num);

            request.setAttribute("condition", condition);
            request.setAttribute("page", page);
            request.getRequestDispatcher("/products.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void findProductByPid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pid = request.getParameter("pid");
        try {
            Product product = service.findProductByPid(pid.trim());
            request.setAttribute("product", product);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("/productdetail.jsp").forward(request, response);
    }
}
