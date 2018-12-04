package com.cskaoyan.controller;

import com.cskaoyan.bean.Category;
import com.cskaoyan.servcie.CategoryService;
import com.cskaoyan.servcie.impl.CategoryServiceImpl;
import com.cskaoyan.utils.PageHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "CategoryServlet",urlPatterns = "/CategoryServlet")
public class CategoryServlet extends HttpServlet {

    CategoryService categoryService = new CategoryServiceImpl();


    protected void doPost(HttpServletRequest req , HttpServletResponse resp ) throws ServletException, IOException {

        // resp.setContentType("text/html;charset=utf-8");
        // req.setCharacterEncoding("utf-8"); //需求在取数据之前设置

        String op = req.getParameter("op");

        if (op!=null&&!op.isEmpty()){


            switch (op){

                    case  "addCategory":
                        addCategory(req,resp);
                        break;
                    case  "findAllCategory":
                        findAllCategory(req,resp);
                        break;
                    case "toupdateCategory":
                        toUpdateCategory(req,resp);
                        break;
                    case "updateCategory":
                        updateCategory(req,resp);
                        break;
                    case "deleteCategory":
                        deleteCategory(req,resp);
                        break;


                    default:
                        break;
                }





        }else {

            resp.getWriter().println("error！ op Is null！");
        }

    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request,response);
    }


    private void toUpdateCategory(HttpServletRequest request, HttpServletResponse response) {

        String cid = request.getParameter("cid");

        try {
            Category category =categoryService.getCategoryByCid(cid);

            request.setAttribute("category", category);

            request.getRequestDispatcher("/admin/category/updateCategory.jsp").forward(request, response);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 返回所有的
     * 实现分页之后，返回指定的页码的数据
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void findAllCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

      /*  try {
            List<Category> categoriesList=	categoryService.findAllCategory();

            request.setAttribute("categoriesList", categoriesList);

            request.getRequestDispatcher("/admin/category/categoryList.jsp").forward(request, response);


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            //这里写异常的处理的页面

            response.getWriter().println("添加类别发生了异常，请联系管理员! 异常的信息如下:"+e.getMessage());
        }*/


        //当前用户传入的分页的第几页
        String num = request.getParameter("num");
        if (num==null||num.isEmpty()){

            num="1";
        }

        //调用太多的API不简洁，其次不通用
        //代码的复用 需要考虑更通用的做法
        //低耦合，高内聚

        PageHelper<Category>  pageHelper= null;
        try {
            pageHelper = categoryService.findCategoryListByPagenumber(num);
            request.setAttribute("page",pageHelper);
            request.getRequestDispatcher("/admin/category/categoryList.jsp").forward(request, response);


        } catch (SQLException e) {
            e.printStackTrace();
        }






    }

    private void addCategory(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String cname = req.getParameter("cname");

        System.out.println("cname="+cname);
        try {
            boolean ret = categoryService.addCategory(cname);
            if (ret){
                //插入成功！
                resp.getWriter().println("插入成功！");
                String contextPath = req.getContextPath();
                resp.setHeader("refresh","2;url='"+contextPath+"/CategoryServlet?op=findAllCategory'");

            }

        } catch (Exception e) {
            e.printStackTrace();

            //插入发生异常
            resp.getWriter().println("插入发生异常！e="+e.getCause());
        }

    }

    private void deleteCategory(HttpServletRequest request,
                                HttpServletResponse response) throws ServletException, IOException {

        String cid_string = request.getParameter("cid");

        try {
            int cid = Integer.parseInt(cid_string);

            boolean ret =    categoryService.deleteCategory(cid);
            System.out.println("CategoryServlet.updateCategory()"+ret);
            if (ret) {
                request.getRequestDispatcher(
                        "/CategoryServlet?op=findAllCategory")
                        .forward(request, response);;

                //delete ok update cache
                updateCategoryListInCache();

            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response.getWriter().print(e.getMessage());
        }

    }

    private void updateCategory(HttpServletRequest request,
                                HttpServletResponse response)
            throws IOException, ServletException {

        String cid = request.getParameter("cid");
        String cname = request.getParameter("cname");

        try {
            Category category = new Category();
            category.setCid(Integer.parseInt(cid));
            category.setCname(cname);

            boolean ret =    categoryService.updateCategory(category);
            System.out.println("CategoryServlet.updateCategory()"+ret);
            if (ret) {
                request.getRequestDispatcher("/CategoryServlet?op=findAllCategory&num=1").forward(request, response);;
                //update ok update cache
                updateCategoryListInCache();
            }

        } catch ( Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response.getWriter().print(e.getMessage());
        }

    }

    private void  updateCategoryListInCache() {

        /*List<Category> categorylist = categoryService.findallCategory();
        getServletContext().setAttribute("categorylist", categorylist);*/
    }

    private void deleteMulti(HttpServletRequest request,
                             HttpServletResponse response) throws IOException {

        String[] cids = request.getParameterValues("cid");
        System.out.println("CategoryServlet.deleteMulti()"+ Arrays.toString(cids));
        if(cids == null || cids.length == 0){
            response.sendRedirect(request.getContextPath()+"/admin/CategoryServlet?op=findAllCategory&pageNum=1");
            return ;
        }

        try {
            categoryService.deleteCategories(cids);

            //	response.sendRedirect(request.getContextPath()+"/admin/CategoryServlet?op=findAllCategory&num=1");
            response.getWriter().println("批量删除成功!<br>");

            response.setHeader("refresh", "1;url="+request.getServletContext().getContextPath()+"/admin/CategoryServlet?op=findAllCategory&pageNum=1");
        } catch (Exception e) {

            response.getWriter().println("批量删除失败!<br>"+e.getMessage());

            response.setHeader("refresh", "3;url="+request.getServletContext().getContextPath()+"/admin/CategoryServlet?op=findAllCategory&pageNum=1");
        }
    }
}
