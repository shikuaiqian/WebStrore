package com.cskaoyan.dao.impl;


import com.cskaoyan.bean.Admin;
import com.cskaoyan.dao.AdminDao;
import com.cskaoyan.utils.MyC3P0DataSouce;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class AdminDaoImpl implements AdminDao {
    @Override
    public Admin adminLogin(String username, String password) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(MyC3P0DataSouce.getDataSource());
        return queryRunner.query("select * from `admin` where username = ? and password = ?", new BeanHandler<>(Admin.class), username, password);
    }

    @Override
    public List<Admin> findPartCategory(int limit, int offset) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(MyC3P0DataSouce.getDataSource());
        return queryRunner.query("select * from `admin` limit ? offset ?", new BeanListHandler<>(Admin.class), limit, offset);
    }

    @Override
    public int findAllAdminCount() throws SQLException {
        QueryRunner queryRunner = new QueryRunner(MyC3P0DataSouce.getDataSource());
        Long query = (Long)queryRunner.query("select count(*) from `admin`", new ScalarHandler());
        return query.intValue();
    }

    @Override
    public boolean isUsernameAvailable(String username) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(MyC3P0DataSouce.getDataSource());
        Long query = (Long)queryRunner.query("select count(*) from `admin` where username = ?", new ScalarHandler(), username);
        return 1L != query;
    }

    @Override
    public boolean updateAdmin(String aid, String password) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(MyC3P0DataSouce.getDataSource());
        int update = queryRunner.update("update `admin` set password = ? where aid = ?", password, aid);
        return 1 == update;
    }

    @Override
    public List<Admin> findAllAdmin() throws SQLException {
        QueryRunner queryRunner = new QueryRunner(MyC3P0DataSouce.getDataSource());
        return queryRunner.query("select * from `admin`", new BeanListHandler<>(Admin.class));
    }

    @Override
    public boolean saveAdmin(Admin admin) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(MyC3P0DataSouce.getDataSource());
        int update = queryRunner.update("insert into `admin` values (null, ?, ?)",
                admin.getUsername(), admin.getPassword());
        return 1 == update;
    }
}
