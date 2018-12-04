package com.cskaoyan.dao;

import com.cskaoyan.bean.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryDao {
    boolean addCategory(String cname)throws SQLException;


    boolean isCategoryNameAvailable(String cname) throws SQLException;



    List<Category> findAllCategory() throws SQLException;

    int findTotalNumber() throws SQLException;

    List<Category> findPartCategory(int cagegoryNumPerPage, int i) throws SQLException;


    boolean updateCategory(Category category) throws SQLException;

    boolean deleteCategoryById(int cid) throws SQLException;

    void deleteCategoriesByIds(String[] cids);

    Category getCategoryByCid(int cid) throws SQLException;
}
