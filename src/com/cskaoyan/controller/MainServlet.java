package com.cskaoyan.controller;



import com.cskaoyan.bean.Product;
import com.cskaoyan.servcie.ProductService;
import com.cskaoyan.servcie.impl.ProductServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "MainServlet", value = "/MainServlet")
public class MainServlet extends HttpServlet {

    private ProductService productService = new ProductServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // topProducts
        // hotProducts
        try {
            List<Product> hotProducts = productService.findHotProducts();
            List<Product> topProducts = productService.findTopProducts();

            request.setAttribute("hotProducts", hotProducts);
            request.setAttribute("topProducts", topProducts);
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
