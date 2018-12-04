package com.cskaoyan.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "ManagementAuthorityCheck", value = "/admin/*")
public class    ManagementAuthorityCheck implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        Object admin = request.getSession().getAttribute("admin");
        String requestURI = request.getRequestURI();

        String op = request.getParameter("op");
        boolean checkLoginAction = requestURI.endsWith("AdminServlet") && (null != op && "login".equals(op));
        if (null != admin
                || requestURI.endsWith("index.jsp")
                || requestURI.endsWith("AjaxServlet")
                || requestURI.contains("/css/")
                || requestURI.contains("/js/")
                || requestURI.contains("/images/")
                || checkLoginAction) {
            chain.doFilter(req, resp);
        } else {
            ((HttpServletResponse) resp).sendRedirect(request.getContextPath() + "/admin/index.jsp");
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
