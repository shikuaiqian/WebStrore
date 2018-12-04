package com.cskaoyan.dao;


import com.cskaoyan.bean.Product;
import com.cskaoyan.form.SearchCondition;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {
    boolean saveProduct(Product product) throws SQLException;

    boolean isPidAvailable(int pid) throws SQLException;

    int findAllProductCount() throws SQLException;

    List<Product> findPartProduct(int limit, int offset) throws SQLException;

    String deleteProduct(String pid) throws SQLException;

    Product findProductByPid(String pid) throws SQLException;

    boolean updateProduct(Product product) throws SQLException;


    List<Product> findProductByCid(String cid) throws SQLException;



    List<Object> findProductByName(String name) throws SQLException;

    int findProductCountByMultiCondition(String pid, String cid, String pname, String minprice, String maxprice);

    List<Product> findProductsByMultiCondition(String pid, String cid, String pname, String minprice, String maxprice) throws SQLException;

    int findAllSearchProductCount(SearchCondition condition) throws SQLException;

    List<Product> findPartSearchProduct(SearchCondition condition, int limit, int offset) throws SQLException;


    int findSimpleSearchProductCount(SearchCondition condition) throws SQLException;

    List<Product> findSimpleSearchProduct(SearchCondition condition, int limit, int offset) throws SQLException;


    List<Product> findTopProducts() throws SQLException;


    List<Product> findHotProducts() throws SQLException;

    void minusProductCount(int intPid, int buynum) throws SQLException;

}
