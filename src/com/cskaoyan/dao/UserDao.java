package com.cskaoyan.dao;

import com.cskaoyan.bean.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    boolean saveUser(User user) throws SQLException;

    boolean isUsernameAvailable(String username) throws SQLException;

    int findAllUserCount() throws SQLException;

    List<User> findPartUser(int limit, int offset) throws SQLException;

    User checkUserLogin(String username, String password) throws SQLException;

    boolean updateUser(User user) throws SQLException;

    boolean activeUser(String activeCode) throws SQLException;

    User findUserByUid(int uid) throws SQLException;
}
