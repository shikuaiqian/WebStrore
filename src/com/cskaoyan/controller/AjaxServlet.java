package com.cskaoyan.controller;

import com.cskaoyan.servcie.AdminService;
import com.cskaoyan.servcie.CategoryService;
import com.cskaoyan.servcie.ProductService;
import com.cskaoyan.servcie.UserService;
import com.cskaoyan.servcie.impl.AdminServiceImpl;
import com.cskaoyan.servcie.impl.CategoryServiceImpl;
import com.cskaoyan.servcie.impl.ProductServiceImpl;
import com.cskaoyan.servcie.impl.UserServiceImpl;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "AjaxServlet", value = "/admin/AjaxServlet")
public class AjaxServlet extends HttpServlet {

    private AdminService adminService = new AdminServiceImpl();
    private CategoryService categoryService = new CategoryServiceImpl();
    private ProductService productService = new ProductServiceImpl();
    private UserService userService = new UserServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ajax = request.getParameter("ajax");
        if (null != ajax) {
            switch (ajax) {
                case "isAdminUsernameAvailable":
                    isAdminUsernameAvailable(request, response);
                    break;
                case "isCategoryNameAvailable":
                    isCategoryNameAvailable(request, response);
                    break;
                case "isPidAvailable":
                    isPidAvailable(request, response);
                    break;
                case "isUserUsernameAvailable":
                    isUserUsernameAvailable(request, response);
                    break;
                case "findProductsHintByName":
                    findProductsHintByName(request, response);
                    break;
            }
        }
    }

    private void findProductsHintByName(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String key = request.getParameter("key");
        if (null != key) {
            try {
                String result = productService.findProductByName(key.trim());
                if (result.isEmpty()) result = "";
                response.getWriter().print(result);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void isUserUsernameAvailable(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        if (null != username) {
            try {
                response.getWriter().print(userService.isUsernameAvailable(username.trim()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void isPidAvailable(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pid = request.getParameter("pid");
        if (null != pid) {
            try {
                boolean available = productService.isPidAvailable(Integer.parseInt(pid));
                response.getWriter().print(available);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void isCategoryNameAvailable(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String cname = request.getParameter("cname");
        if (null != cname) {
            try {
                boolean available = categoryService.isCategoryNameAvailable(cname.trim());
                response.getWriter().print(available);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void isAdminUsernameAvailable(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        if (null != username) {
            try {
                boolean available = adminService.isUsernameAvailable(username.trim());
                response.getWriter().print(available);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
