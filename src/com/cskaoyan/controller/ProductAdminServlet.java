package com.cskaoyan.controller;

import com.cskaoyan.bean.Category;
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
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ProductAdminServlet", value = "/admin/ProductServlet")
public class ProductAdminServlet extends HttpServlet {

    private ProductService service = new ProductServiceImpl();
    private String contentPath;
    private String fileBase;

    @Override
    public void init() throws ServletException {
        contentPath = getServletContext().getContextPath();
        fileBase = getServletContext().getRealPath("/files");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");

        if (null != op) {
            switch (op) {
                case "findAllProduct":
                    findAllProduct(request, response);
                    break;
                case "deleteProduct":
                    deleteProduct(request, response);
                    break;
                case "findProductByUpdate":
                    findProductByUpdate(request, response);
                    break;
                case "deleteMulti":
                    deleteMulti(request, response);
                    break;
                case "multiConditionSearch":
                    multiConditionSearchProduct(request, response);
                    break;
            }
        }
    }


    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    /*private void multiConditionSearchProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //如何实现？

        String pid = request.getParameter("pid");
        String cid = request.getParameter("cid");
        String pname = request.getParameter("pname");
        String minprice = request.getParameter("minprice");
        String maxprice = request.getParameter("maxprice");
        String num = request.getParameter("num");

        System.out.println(" :num:"+num);


        //做一下参数合法性验证

        //返回一个分页数据
        try {
            PageHelper<Product> pageHelper=   service.findProductBYCondition(pid,cid,pname,minprice,maxprice,num);

            request.setAttribute("pageHelper",pageHelper);
           // request.setAttribute("condtion",condtion);

            //方法1 ，转发到一个新的显示搜索结果的页面
            request.getRequestDispatcher("/admin/product/searchproductList.jsp").forward(request,response);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //分页的条件怎么保留？

        //方法1

        //方法2 可以把刚刚的查询条件cache到Session里





        request.getRequestDispatcher("/admin/product/productSearchList.jsp").forward(request, response);
    }*/


    private void multiConditionSearchProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SearchCondition condition = new SearchCondition();
        List<Category> categories = (List<Category>) getServletContext().getAttribute("categories");
        String num = request.getParameter("num");
        try {
            BeanUtils.populate(condition, request.getParameterMap());
            PageHelper<Product> page = service.multiConditionSearch(condition, categories, num);

            request.setAttribute("page", page);
            request.setAttribute("condition", condition);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("/admin/product/productSearchList.jsp").forward(request, response);
    }

    private void deleteMulti(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] pids = request.getParameterValues("pid");
        String message;

        if (null == pids) {
            message = "请先选择要删除的项，再提交...";
        } else {
            try {
                for (String pid : pids) {
                    String imgUrl = service.deleteProduct(pid);
                    if (null != imgUrl) {
                        new File(fileBase, imgUrl).delete();
                    }
                }
                message = "批量删除成功...";
            } catch (Exception e) {
                e.printStackTrace();
                message = "删除数据失败... Exception: " + e.getMessage();
            }
        }

        response.getWriter().write(message);
        response.setHeader("refresh", "1;" + contentPath + "/admin/ProductServlet?op=findAllProduct&num=1");


    }

    private void findProductByUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pid = request.getParameter("pid");

        if (null != pid) {
            try {
                Product product = service.findProductByPid(pid);
                request.setAttribute("product", product);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        request.getRequestDispatcher("/admin/product/updateProduct.jsp").forward(request, response);
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pid = request.getParameter("pid");
        String imgUrl;
        String message;
        try {
            imgUrl = service.deleteProduct(pid);
            if (null != imgUrl) {
                // 删除商品图片
                new File(fileBase, imgUrl).delete();
                message = "删除成功...";
            } else {
                message = "删除失败...";
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "删除数据失败... Exception: " + e.getMessage();
        }

        response.getWriter().write(message);
        response.setHeader("refresh", "1;" + contentPath + "/admin/ProductServlet?op=findAllProduct&num=1");

    }

    private void findAllProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String num = request.getParameter("num");
        List<Category> categories = (List<Category>) getServletContext().getAttribute("categories");
        try {
            PageHelper page = service.findPartProduct(categories, num);
            request.setAttribute("page", page);
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("/admin/product/productList.jsp").forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
