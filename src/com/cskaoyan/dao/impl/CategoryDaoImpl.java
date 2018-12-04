package com.cskaoyan.dao.impl;

import com.cskaoyan.bean.Category;
import com.cskaoyan.dao.CategoryDao;
import com.cskaoyan.utils.MyC3P0DataSouce;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {


    @Override
    public boolean addCategory(String cname) throws SQLException {

        QueryRunner queryRunner = new QueryRunner(MyC3P0DataSouce.getDataSource());

        int update = queryRunner.update("insert into category values (null,?);", cname);

        return update==1?true:false;
    }

    @Override
    public List<Category> findAllCategory() throws SQLException {
        QueryRunner qr = new QueryRunner(MyC3P0DataSouce.getDataSource());

        List<Category> query = qr.query("select * from category", new BeanListHandler<Category>(Category.class));

        return query;

    }

    @Override
    public int findTotalNumber() throws SQLException {
        QueryRunner qr = new QueryRunner(MyC3P0DataSouce.getDataSource());

        Long query = (Long) qr.query("select count(*) from category", new ScalarHandler());

        return query.intValue();
    }

    @Override
    public List<Category> findPartCategory(int limit, int offset) throws SQLException {
        QueryRunner qr = new QueryRunner(MyC3P0DataSouce.getDataSource());

        List<Category> query = qr.query("select * from category limit ? offset ?",
                new BeanListHandler<Category>(Category.class),
                limit,
                offset);

        return query;
    }


    @Override
    public boolean updateCategory(Category category) throws SQLException {
        // TODO Auto-generated method stub

        boolean ret =false;


        QueryRunner qr = new QueryRunner(MyC3P0DataSouce.getDataSource());

        try {
            int update = qr.update("update   category  set   cname =? where cid =? ;", category.getCname(),category.getCid());

            System.out.println("CategoryDaoImpl.addCategoryToDB()"+update);
            if (update==1) {
                ret =true;

            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw  new RuntimeException("update  category error! ");
        }



        return ret;
    }

    @Override
    public boolean deleteCategoryById(int cid) throws SQLException {
        // TODO Auto-generated method stub


        boolean ret =false;


        QueryRunner qr = new QueryRunner(MyC3P0DataSouce.getDataSource());

        try {
            int update = qr.update("delete  from category    where cid =? ;", cid);

            System.out.println("CategoryDaoImpl.deleteCategoryById()"+update);
            if (update==1) {
                ret =true;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw  new RuntimeException("detete  category error! " + e.getMessage());
        }



        return ret;

    }

    @Override
    public void deleteCategoriesByIds(String[] cids) {

        QueryRunner qRunner = new QueryRunner();
        Connection connection =null;
        try {

            connection = MyC3P0DataSouce.getConnection();

            Object[][] params = new Object[cids.length][];

            for (int i =0;i<cids.length;i++) {

                Object[] o= { cids[i]};
                params[i]=o;

            }

            //开启事务
            connection.setAutoCommit(false);

            int[] batch = qRunner.batch(connection,"delete from category where cid =? ;", params);

            //提交
            connection.commit();
            for (int i : batch) {

                System.out.println("CategoryDaoImpl.deleteCategoriesByIds()"+i);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            if(connection!=null){
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }

            throw new RuntimeException(e.getMessage());
        }


    }

    @Override
    public Category getCategoryByCid(int cid) throws SQLException {
        // TODO Auto-generated method stub
        QueryRunner qr = new QueryRunner(MyC3P0DataSouce.getDataSource());

        Category query = qr.query("select * from category where cid =?", new BeanHandler<Category>(Category.class), cid);

        return query;
    }


    @Override
    public boolean isCategoryNameAvailable(String cname) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(MyC3P0DataSouce.getDataSource());
        Long query = (Long)queryRunner.query("select count(*) from category where cname = ?", new ScalarHandler(), cname);
        return 1L != query;
    }


}
