package com.cskaoyan.controller;

import com.cskaoyan.bean.Category;
import com.cskaoyan.servcie.CategoryService;
import com.cskaoyan.servcie.impl.CategoryServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "InitLoadDataServlet",
        value = "/admin/InitLoadDataServlet",
        loadOnStartup = 1)
public class InitLoadDataServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        CategoryService service = new CategoryServiceImpl();
        List<Category> categories;
        try {
            categories = service.findAllCategory();
            this.getServletContext().setAttribute("categories", categories);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
