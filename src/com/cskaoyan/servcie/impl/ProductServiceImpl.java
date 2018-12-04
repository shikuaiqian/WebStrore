package com.cskaoyan.servcie.impl;

 
import com.cskaoyan.bean.Category;
import com.cskaoyan.bean.Product;
import com.cskaoyan.dao.ProductDao;
import com.cskaoyan.dao.impl.ProductDaoImpl;
import com.cskaoyan.form.SearchCondition;
import com.cskaoyan.servcie.ProductService;
import com.cskaoyan.utils.PageHelper;

import java.sql.SQLException;
import java.util.List;

public class ProductServiceImpl implements ProductService {

    private ProductDao dao = new ProductDaoImpl();

    /*private PageHelper<Product> splitPage(int currentPageNum, int totalRecordNum, int perPageCount) {
        PageHelper<Product> page = new PageHelper<>();

        // 总记录数据项数 totalRecordsNum
        page.setTotalRecordsNum(totalRecordNum, perPageCount);

        // 当前页码 currentPageNum
        page.setCurrentPageNum(currentPageNum);

        int offset = perPageCount * (currentPageNum - 1);

        // 记录数据 records
       List<Product> products = dao.findSimpleSearchProduct(condition, perPageCount, offset);

        page.setRecords(products);
        return page;
    }*/

    @Override
    public String findProductByName(String name) throws SQLException {
        List<Object> r = dao.findProductByName(name);

        String s = r.toString();
        return r.size() > 0 ? s.substring(1, s.length() - 1) : "";
    }




    @Override
    public boolean updateProduct(Product product) throws SQLException {
        return dao.updateProduct(product);
    }

    @Override
    public Product findProductByPid(String pid) throws SQLException {
        return dao.findProductByPid(pid);
    }

    @Override
    public String deleteProduct(String pid) throws SQLException {
        return dao.deleteProduct(pid);
    }

    @Override
    public PageHelper<Product> findPartProduct(List<Category> categories, String num) throws SQLException {
        PageHelper<Product> page = new PageHelper<>();
        int currentPageNum = Integer.parseInt(num);

        // 总记录数据项数 totalRecordsNum
        page.setTotalRecordsNum(dao.findAllProductCount(), PageHelper.PRODUCT_PER_PAGE);

        // 当前页码 currentPageNum
        page.setCurrentPageNum(currentPageNum);

        int limit = PageHelper.PRODUCT_PER_PAGE;
        int offset = limit * (currentPageNum - 1);

        // 记录数据 records
        List<Product> products = dao.findPartProduct(limit, offset);

        // 传入每一个 category
        for (Product product : products) {
            for (Category category : categories) {
                if (category.getCid() == product.getCid()) {
                    product.setCategory(category);
                }
            }
        }

        page.setRecords(products);
        return page;
    }

    @Override
    public boolean isPidAvailable(int pid) throws SQLException {
        return dao.isPidAvailable(pid);
    }

    @Override
    public String validateProduct(Product product) throws SQLException {

        String message;

        // 商品号为数字
        if (0 != product.getPid()) {
            // 商品名不为空
            if (!product.getPname().isEmpty()) {
                // 商品价格为数字
                if (0 != product.getEstorePrice() || 0 != product.getMarkPrice()) {
                    // 描述文字不能为空
                    if (!product.getDescription().isEmpty()) {
                        message = "YES";
                    } else {
                        message = "请输入商品描述...";
                    }
                } else {
                    message = "价格输入错误...";
                }
            } else {
                message = "请输入商品名...";
            }
        } else {
            message = "商品号 输入错误，应该为数字...";
        }
        return message;
    }

    @Override
    public boolean saveProduct(Product product) throws SQLException {
        return dao.saveProduct(product);
    }


    @Override
    public PageHelper<Product> findProductBYCondition(String pid, String cid, String pname, String minprice, String maxprice, String num) throws SQLException {

        //

        //总的记录数
        int totalNumber=  dao.findProductCountByMultiCondition(pid,cid,pname,minprice,maxprice);

        //当前的页面
        int currentPageNumber= Integer.parseInt(num);

        //总的页码
        int recordPerPage=3;

        PageHelper<Product> pageHelper =new PageHelper<>(currentPageNumber,totalNumber,recordPerPage);

        List<Product> records = dao.findProductsByMultiCondition(pid,cid,pname,minprice,maxprice);


        pageHelper.setRecords(records);

        return pageHelper;
    }


    @Override
    public PageHelper<Product> multiConditionSearch(SearchCondition condition, List<Category> categories, String num) throws SQLException {
        PageHelper<Product> page = new PageHelper<>();

        int currentPageNum = Integer.parseInt(num);

        // 总记录数据项数 totalRecordsNum
        page.setTotalRecordsNum(dao.findAllSearchProductCount(condition), PageHelper.PRODUCT_SEARCH_PER_PAGE);

        // 当前页码 currentPageNum
        page.setCurrentPageNum(currentPageNum);

        int limit = PageHelper.PRODUCT_SEARCH_PER_PAGE;
        int offset = limit * (currentPageNum - 1);

        // 记录数据 records
        List<Product> products = dao.findPartSearchProduct(condition, limit, offset);

        // 传入每一个 category
        for (Product product : products) {
            for (Category category : categories) {
                if (category.getCid() == product.getCid()) {
                    product.setCategory(category);
                }
            }
        }

        page.setRecords(products);

        return page;
    }

    @Override
    public List<Product> findTopProducts() throws SQLException {
        return dao.findTopProducts();
    }

    @Override
    public List<Product> findHotProducts() throws SQLException {
        return dao.findHotProducts();
    }


    @Override
    public PageHelper<Product> simpleConditionSearch(SearchCondition condition, String num) throws SQLException {
        PageHelper<Product> page = new PageHelper<>();

        int currentPageNum = Integer.parseInt(num);

        // 总记录数据项数 totalRecordsNum
        page.setTotalRecordsNum(dao.findSimpleSearchProductCount(condition), PageHelper.PRODUCT_PER_PAGE_FRONT_END);

        // 当前页码 currentPageNum
        page.setCurrentPageNum(currentPageNum);

        int limit = PageHelper.PRODUCT_PER_PAGE_FRONT_END;
        int offset = limit * (currentPageNum - 1);

        // 记录数据 records
        List<Product> products = dao.findSimpleSearchProduct(condition, limit, offset);

        page.setRecords(products);
        return page;
    }


    @Override
    public PageHelper<Product> findProductByCid(SearchCondition condition, String num) throws SQLException {
        PageHelper<Product> page = new PageHelper<>();

        int currentPageNum = Integer.parseInt(num);

        // 总记录数据项数 totalRecordsNum
        page.setTotalRecordsNum(dao.findAllSearchProductCount(condition), PageHelper.PRODUCT_PER_PAGE_FRONT_END);

        // 当前页码 currentPageNum
        page.setCurrentPageNum(currentPageNum);

        int limit = PageHelper.PRODUCT_PER_PAGE_FRONT_END;
        int offset = limit * (currentPageNum - 1);

        // 记录数据 records
        List<Product> products = dao.findPartSearchProduct(condition, limit, offset);

        page.setRecords(products);
        return page;
    }

}
