package com.cskaoyan.servcie;

import com.cskaoyan.bean.User;
import com.cskaoyan.utils.PageHelper;


import java.sql.SQLException;

public interface UserService {
    boolean saveUser(User user) throws SQLException;

    String validateUser(User user) throws SQLException;

    PageHelper<User> findPartUser(String num) throws SQLException;

    boolean isUsernameAvailable(String username) throws SQLException;

    User userLogin(String username, String password) throws SQLException;

    String validateUserForUpdate(User user);

    boolean updateUser(User user) throws SQLException;

    boolean activeUser(String activeCode) throws SQLException;

    boolean sendVerifyEmail(User user);
}
