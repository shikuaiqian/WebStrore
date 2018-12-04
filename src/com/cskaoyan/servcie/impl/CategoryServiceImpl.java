package com.cskaoyan.servcie.impl;

import com.cskaoyan.bean.Category;
import com.cskaoyan.dao.CategoryDao;
import com.cskaoyan.dao.impl.CategoryDaoImpl;
import com.cskaoyan.servcie.CategoryService;
import com.cskaoyan.utils.PageHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    CategoryDao categoryDao=new CategoryDaoImpl();
    private static  final int PAGE_COUNT=3;


    @Override
    public boolean addCategory(String cname) throws SQLException {

        return categoryDao.addCategory(cname);
    }

    @Override
    public List<Category> findAllCategory() throws SQLException {
        return categoryDao.findAllCategory();
    }




    @Override
    public boolean updateCategory(Category category) throws SQLException {
        // TODO Auto-generated method stub
        return categoryDao.updateCategory(category);
    }
    @Override
    public boolean deleteCategory(int cid) throws SQLException {
        // TODO Auto-generated method stub
        return categoryDao.deleteCategoryById(cid);
    }

    @Override
    public void deleteCategories(String[] cids) {
        // TODO Auto-generated method stub

        categoryDao.deleteCategoriesByIds(cids);
    }

    @Override
    public Category getCategoryByCid(String cid) throws SQLException, Exception {
        int intcid = Integer.parseInt(cid);

        return categoryDao.getCategoryByCid(intcid);
    }




    @Override
    public boolean isCategoryNameAvailable(String cname) throws SQLException {
        return categoryDao.isCategoryNameAvailable(cname);
    }


    /**
     *
     * 获取分页的数据
     * @param num
     * @return
     */
    @Override
    public PageHelper<Category> findCategoryListByPagenumber(String num) throws SQLException {


        //前置信息
        int totalNumber = categoryDao.findTotalNumber();
        int currentPageNumber = Integer.parseInt(num);

        PageHelper<Category> pageHelper=new PageHelper<> (currentPageNumber, totalNumber,PAGE_COUNT);;

        //分页也可以内聚到PageHelper里面
        //总的页码数，
        //每一页需要显示的记录数
        /*int recordsNumberPerPage =2;
        int totalPageNumber =( totalNumber%recordsNumberPerPage)==0 ?
                        totalNumber/recordsNumberPerPage :
                        totalNumber/recordsNumberPerPage+1;

        pageHelper.setTotalPageNum(totalPageNumber);*/

        int limit=PAGE_COUNT;
        int offset= (currentPageNumber-1)*PAGE_COUNT;
        List<Category> categories =categoryDao.findPartCategory(limit,offset);
        pageHelper.setRecords(categories);

        return pageHelper;
    }

}
