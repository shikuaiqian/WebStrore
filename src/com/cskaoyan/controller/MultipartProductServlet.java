package com.cskaoyan.controller;



import com.cskaoyan.bean.Product;
import com.cskaoyan.servcie.ProductService;
import com.cskaoyan.servcie.impl.ProductServiceImpl;
import com.cskaoyan.utils.FileUploadUtil;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@WebServlet(name = "AddProductServlet", value = "/admin/MultipartProductServlet")
public class MultipartProductServlet extends HttpServlet {

    private String fileBase;
    private ProductService service = new ProductServiceImpl();
    private String contentPath;

    @Override
    public void init() throws ServletException {
        contentPath = getServletContext().getContextPath();
        fileBase = getServletContext().getRealPath("/files");
        new File(fileBase).mkdirs();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        if (null != op) {
            switch (op) {
                case "addProduct":
                    addProduct(request, response);
                    break;
                case "updateProduct":
                    updateProduct(request, response);
                    break;
            }
        }
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String oldImgUrl = request.getParameter("oldImgUrl");
        Map<String, String> parameterMap = new HashMap<>();
        Product product = new Product();
        String message;
        try {
            // 拿到上传的文件，并填充 parameterMap
            byte[] fileUpload = FileUploadUtil.handleFileUpload(request, parameterMap);

            // 验证输入的数据合法性
            BeanUtils.populate(product, parameterMap);
            message = service.validateProduct(product);

            if ("YES".equals(message)) {
                if (0 != fileUpload.length) {
                    // 如果上传新文件，删除旧文件
                    if (null != oldImgUrl) {
                        new File(fileBase, oldImgUrl).delete();
                    }
                    // 保存新文件到服务器，并设置新的文件路径
                    String fileName = saveUploadFile(product.getImgUrl(), fileUpload);
                    product.setImgUrl(fileName);
                } else {
                    // 如果没有上传新文件，不更改文件
                    product.setImgUrl(oldImgUrl);
                }

                // 存入数据库
                boolean b = service.updateProduct(product);
                if (b) {
                    message = "修改商品成功...";
                } else {
                    message = "修改商品失败...";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "修改商品出错... Exception: " + e.getMessage();
        }

        response.getWriter().write(message);
       // response.setHeader("refresh", "1;" + contentPath + "/admin/ProductServlet?op=findAllProduct&num=1");
        response.setHeader("refresh", "1;" + contentPath + "/admin/ProductServlet?op=findProductByUpdate&pid="+product.getPid());




    }

    private void addProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String> parameterMap = new HashMap<>();
        Product product = new Product();
        String message;
        try {
            // 拿到上传的文件，并填充 parameterMap
            byte[] fileUpload = FileUploadUtil.handleFileUpload(request, parameterMap);
            BeanUtils.populate(product, parameterMap);

            // 验证输入的数据合法性
            boolean available = service.isPidAvailable(product.getPid());
            if (available) {
                message = service.validateProduct(product);
                if ("YES".equals(message)) {
                    // 验证通过，保存文件到服务器，并设置新的文件路径
                    String fileName = saveUploadFile(product.getImgUrl(), fileUpload);
                    product.setImgUrl(fileName);
                    // 存入数据库
                    service.saveProduct(product);
                    message = "添加商品成功...";
                }
            } else {
                message = "商品号重复...请重新输入";
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "添加商品出错... Exception: " + e.getMessage();
        }

        response.getWriter().write(message);
        response.setHeader("refresh", "1;" + contentPath + "/admin/product/addProduct.jsp");

    }

    private String saveUploadFile(String fileName, byte[] fileUpload) throws IOException {
        // 如果没有上传文件，就不保存
        if (0 == fileUpload.length) {
            return "default.jpg";
        }

        // 先将 fileName 添加 UUID 防止重名
        fileName = UUID.randomUUID() + fileName;

        // 将上传的文件分文件夹保存
        /*int hash = fileName.hashCode();
        String hexString = Integer.toHexString(hash);
        String dir = getDir(hexString);
        File directory = new File(fileBase, dir);*/

        File directory = new File(fileBase);
        directory.mkdirs();

        // 写入文件
        File file = new File(directory, fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
        bos.write(fileUpload);
        return  fileName;
    }

    private String getDir(String hexString) {
        char[] chars = hexString.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            sb.append(c).append('/');
        }
        return sb.toString();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
