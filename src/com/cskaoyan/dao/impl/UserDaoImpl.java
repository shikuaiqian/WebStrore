package com.cskaoyan.dao.impl;

 
import com.cskaoyan.bean.User;
import com.cskaoyan.dao.UserDao;
import com.cskaoyan.utils.MyC3P0DataSouce;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public User findUserByUid(int uid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(MyC3P0DataSouce.getDataSource());
        return queryRunner.query("select * from user where uid = ?",new BeanHandler<>(User.class), uid);
    }

    @Override
    public boolean activeUser(String activeCode) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(MyC3P0DataSouce.getDataSource());
        int update = queryRunner.update("update user set status = 1 where activeCode = ?", activeCode);
        return 1 == update;
    }

    @Override
    public boolean updateUser(User user) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(MyC3P0DataSouce.getDataSource());
        int update = queryRunner.update("update user set nickname = ?, password = ?, email = ?, birthday = ? where uid = ?",
                user.getNickname(),
                user.getPassword(),
                user.getEmail(),
                user.getBirthday(),
                user.getUid());
        return 1 == update;
    }

    @Override
    public User checkUserLogin(String username, String password) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(MyC3P0DataSouce.getDataSource());
        return queryRunner.query("select * from user where username = ? and password = ?",
                new BeanHandler<>(User.class), username, password);
    }

    @Override
    public List<User> findPartUser(int limit, int offset) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(MyC3P0DataSouce.getDataSource());
        return queryRunner.query("select * from user limit ? offset ?", new BeanListHandler<>(User.class), limit, offset);
    }

    @Override
    public int findAllUserCount() throws SQLException {
        QueryRunner queryRunner = new QueryRunner(MyC3P0DataSouce.getDataSource());
        Long query = (Long) queryRunner.query("select count(*) from user", new ScalarHandler());
        return query.intValue();
    }

    @Override
    public boolean isUsernameAvailable(String username) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(MyC3P0DataSouce.getDataSource());
        Long query = (Long) queryRunner.query("select count(*) from user where username = ?", new ScalarHandler(), username);
        return 1L != query;
    }

    @Override
    public boolean saveUser(User user) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(MyC3P0DataSouce.getDataSource());
        int update = queryRunner.update(
                "INSERT INTO user (uid, username, nickname, password, email, birthday, activeCode, status)" +
                        " VALUES (null, ?, ?, ?, ?, ?, ?, ?)",
                user.getUsername(),
                user.getNickname(),
                user.getPassword(),
                user.getEmail(),
                user.getBirthday(),
                user.getActiveCode(),
                user.getStatus()
        );
        return 1 == update;
    }
}
