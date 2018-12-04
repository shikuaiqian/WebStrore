package com.cskaoyan.servcie;

import com.cskaoyan.bean.Category;
import com.cskaoyan.bean.Product;
import com.cskaoyan.form.SearchCondition;
import com.cskaoyan.utils.PageHelper;


import java.sql.SQLException;
import java.util.List;

public interface ProductService {
    boolean saveProduct(Product product) throws SQLException;

    String validateProduct(Product product) throws SQLException;

    boolean isPidAvailable(int pid) throws SQLException;

    PageHelper findPartProduct(List<Category> categories, String num) throws SQLException;

    String deleteProduct(String pid) throws SQLException;

    Product findProductByPid(String pid) throws SQLException;

    boolean updateProduct(Product product) throws SQLException;



    String findProductByName(String key) throws SQLException;

    PageHelper<Product> findProductBYCondition(String pid, String cid, String pname, String minprice, String maxprice, String num) throws SQLException;

    PageHelper<Product> multiConditionSearch(SearchCondition condition, List<Category> categories, String num)  throws SQLException;

    List<Product> findHotProducts() throws SQLException;

    List<Product> findTopProducts() throws SQLException;

    PageHelper<Product> simpleConditionSearch(SearchCondition condition, String num)  throws SQLException;


    PageHelper<Product> findProductByCid(SearchCondition condition, String num)  throws SQLException;

}
