package com.cskaoyan.controller;

import com.cskaoyan.bean.User;
import com.cskaoyan.form.UserFormBean;
import com.cskaoyan.servcie.UserService;
import com.cskaoyan.servcie.impl.UserServiceImpl;

import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

@WebServlet(name = "UserServlet", value = "/user/UserServlet")
public class UserServlet extends HttpServlet {

    UserService service = new UserServiceImpl();
    private String contentPath;

    @Override
    public void init() throws ServletException {
        contentPath = getServletContext().getContextPath();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        if (null != op) {
            switch (op) {
                case "login":
                    userLogin(request, response);
                    break;
                case "logout":
                    userLogout(request, response);
                    break;
                case "register":
                    userRegister(request, response);
                    break;
                case "update":
                    userUpdate(request, response);
                    break;
                case "activeUser":
                    activeUser(request, response);
                    break;
                case "sendVerifyEmail":
                    sendVerifyEmail(request, response);
                    break;
            }
        }
    }

    private void sendVerifyEmail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (null != user) {
            boolean b = service.sendVerifyEmail(user);
            if (b) {
                request.setAttribute("message", "已发送...快去邮箱看看");
                request.getRequestDispatcher("/user/personal.jsp").forward(request, response);
            } else {
                request.setAttribute("message", "发送失败...请联系管理员");
                request.getRequestDispatcher("/user/personal.jsp").forward(request, response);
            }
        }
    }

    private void activeUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String activeCode = request.getParameter("activeCode");
        if (null != activeCode) {
            boolean b = false;
            try {
                b = service.activeUser(activeCode);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (b) {
                // 更新 Session 中 user status
                User user = (User) request.getSession().getAttribute("user");
                if (null != user) {
                    user.setStatus(1);
                }
                response.getWriter().write("验证成功，正在跳转首页...");
                response.setHeader("refresh", "1;" + contentPath + "/index.jsp");
            } else {
                response.getWriter().write("验证失败，请联系工作人员，正在跳转首页...");
                response.setHeader("refresh", "1;" + contentPath + "/index.jsp");

            }
        }
    }

    private void userUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User user = new User();
        try {
            BeanUtils.populate(user, request.getParameterMap());
            String message = service.validateUserForUpdate(user);
            if ("YES".equals(message)) {
                // 验证成功，修改数据库
                boolean b = service.updateUser(user);
                if (b) {
                    // 更新 Session 域中 user
                    HttpSession session = request.getSession();
                    // uid
                    User sessionUser = (User) session.getAttribute("user");
                    if (null != sessionUser) {
                        copyProperties(user, sessionUser);
                    }

                    message = "修改成功...";
                } else {
                    message = "修改失败...请稍后再试...";
                }
            }

            request.setAttribute("message", message);
            request.getRequestDispatcher("/user/personal.jsp").forward(request, response);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void userRegister(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        UserFormBean msg = new UserFormBean();
        try {
            BeanUtils.populate(msg, request.getParameterMap());
            boolean available = service.isUsernameAvailable(msg.getUsername());
            // 验证用户名是否重复
            if (!available) {
                msg.error.put("username", "用户名重复...请重新输入");
            }

            // 验证邮箱或日期格式
            boolean invalidate = msg.invalidate();
            if (!invalidate) {
                request.setAttribute("msg", msg);
                request.getRequestDispatcher(contentPath + "/user/register.jsp").forward(request, response);
                return;
            }

            // 验证正确，复制表单 Bean 属性
            User user = new User();
            // 如果 User 的 birthday 数据类型为 Date，需要转化才能复制
            // ConvertUtils.register(new DateConverter(), Date.class);
            BeanUtils.copyProperties(user, msg);
            // 存入数据库
            boolean b = service.saveUser(user);
            if (b) {
                response.getWriter().write("注册成功，正在跳转登录页面......");//请去验证邮箱吧
                response.setHeader("refresh", "1;" + contentPath + "/user/login.jsp");
            } else {
                response.getWriter().write("注册失败，请重试...");
                response.setHeader("refresh", "1;" + contentPath + "/user/register.jsp");
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void copyProperties(User updateUser, User sessionUser) {
        sessionUser.setNickname(updateUser.getNickname());
        sessionUser.setEmail(updateUser.getEmail());
        sessionUser.setBirthday(updateUser.getBirthday());
        sessionUser.setPassword(updateUser.getPassword());
    }

    private void userLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (null != session) {
            session.invalidate();
        }
        response.sendRedirect(contentPath + "/index.jsp");
    }

    private void userLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember_me");
        String verifyCode = request.getParameter("verifyCode");
        try {
            Object code = request.getSession().getAttribute("verifyCode");
            /*if (!verifyCode.equals(code)) {
                request.setAttribute("message", verifyCode+"验证码输入错误，请重新输入..."+code);
                request.setAttribute("username", username);
                request.getRequestDispatcher("/user/login.jsp").forward(request, response);
                return;
            }*/
            User user = service.userLogin(username.trim(), password.trim());
            if (null != user) {
                // 登录成功，保存用户会话信息
                request.getSession().setAttribute("user", user);

                // 如果点击保存密码，将用户信息通过 Cookie 写回浏览器
                Cookie usernameCookie;
                Cookie passwordCookie;
                if (null != remember && "on".equals(remember)) {
                    usernameCookie = new Cookie("username", username.trim());
                    passwordCookie = new Cookie("password", password.trim());
                } else {
                    // 没有点击保存密码，删除之前的 Cookie
                    usernameCookie = deleteCookie("username");
                    passwordCookie = deleteCookie("password");
                }

                response.addCookie(usernameCookie);
                response.addCookie(passwordCookie);
                response.sendRedirect(contentPath + "/index.jsp");
            } else {
                request.setAttribute("message", "登录失败，用户名或密码错误...");
                request.getRequestDispatcher("/user/login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Cookie deleteCookie(String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setPath(contentPath);
        cookie.setMaxAge(0);
        return cookie;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
