package com.cskaoyan.controller;

import com.cskaoyan.bean.Admin;
import com.cskaoyan.servcie.AdminService;
import com.cskaoyan.servcie.impl.AdminServiceImpl;
import com.cskaoyan.utils.PageHelper;

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

@WebServlet(name = "AdminServlet", value = "/admin/AdminServlet")
public class AdminServlet extends HttpServlet {

    private AdminService service = new AdminServiceImpl();

    private String contentPath;

    @Override
    public void init() throws ServletException {
        contentPath = getServletContext().getContextPath();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");

        if (null != op) {
            switch (op) {
                case "addAdmin":
                    addAdmin(request, response);
                    break;
                case "findAllAdmin":
                    findAllAdmin(request, response);
                    break;
                case "updateAdmin":
                    updateAdmin(request, response);
                    break;
                case "login":
                    adminLogin(request, response);
                    break;
                case "logout":
                    adminLogout(request, response);
                    break;
            }
        }
    }


    private void adminLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Object admin = session.getAttribute("admin");
        if (null != admin) {
            session.removeAttribute("admin");
        }
        response.sendRedirect(contentPath + "/admin/index.jsp");
    }

    private void adminLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        try {
            Admin admin = service.adminLogin(username, password);
            if (null != admin) {
                request.getSession().setAttribute("admin", admin);
                response.sendRedirect(contentPath + "/admin/main.jsp");
            } else {
                response.sendRedirect(contentPath + "/admin/index.jsp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String aid = request.getParameter("aid");
        String password = request.getParameter("password");
        String password1 = request.getParameter("password1");


        String message;
        if (!password.isEmpty()) {
            if (password.equals(password1)) {
                try {
                    boolean b = service.updateAdmin(aid, password);
                    if (b) {
                        message = "修改管理员账户成功...";
                    } else {
                        message = "修改管理员账户失败...";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    message = "修改管理员账户出错... Exception: " + e.getMessage();
                }
            } else {
                message = "两次密码不一致...";
            }
        } else {
            message = "请输入后再提交...";
        }

        response.getWriter().write(message);
        response.setHeader("refresh", "1;" + contentPath + "/admin/AdminServlet?op=findAllAdmin&num=1");
    }


    private void findAllAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String num = request.getParameter("num");
        try {
            PageHelper<Admin> page = service.findPartAdmin(num);
            request.setAttribute("page", page);
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("/admin/admin/adminList.jsp").forward(request, response);
    }

    private void addAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String password1 = request.getParameter("password1");

        String message;
        try {
            if (!username.isEmpty()) {
                if (service.isUsernameAvailable(username)) {
                    if (password.equals(password1)) {
                        boolean b = service.saveAdmin(new Admin(username, password));
                        if (b) {
                            message = "添加管理员账户成功...";
                        } else {
                            message = "添加管理员账户失败...";
                        }

                    } else {
                        message = "两次密码不一致...";
                    }
                } else {
                    message = "管理员名重复...";
                }
            } else {
                message = "用户名为空，请重新输入...";
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "添加管理员账户出错... Exception: " + e.getMessage();
        }

        response.getWriter().write(message);
        response.setHeader("refresh", "1;" + contentPath + "/admin/admin/addAdmin.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
