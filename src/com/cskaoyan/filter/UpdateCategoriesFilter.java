package com.cskaoyan.filter;

import com.cskaoyan.bean.Category;
import com.cskaoyan.servcie.CategoryService;
import com.cskaoyan.servcie.impl.CategoryServiceImpl;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebFilter(filterName = "UpdateCategoriesFilter", value = "/admin/CategoryServlet")
public class UpdateCategoriesFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        String op = req.getParameter("op");
        chain.doFilter(req, resp);

        // 监听 Category 添加、删除、修改操作完后
        // 更新内存中列表
        if (null != op) {
            switch (op) {
                case "addCategory":
                case "updateCategory":
                case "deleteCategory":
                case "deleteMulti":
                    CategoryService service = new CategoryServiceImpl();
                    List<Category> categories;
                    try {
                        categories = service.findAllCategory();
                        req.getServletContext().setAttribute("categories", categories);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
