package com.cskaoyan.dao;

import com.cskaoyan.bean.Admin;

import java.sql.SQLException;
import java.util.List;

public interface AdminDao {
    boolean saveAdmin(Admin admin) throws SQLException;

    List<Admin> findAllAdmin() throws SQLException;

    boolean updateAdmin(String aid, String password) throws SQLException;

    boolean isUsernameAvailable(String username) throws SQLException;

    int findAllAdminCount() throws SQLException;

    List<Admin> findPartCategory(int limit, int offset) throws SQLException;

    Admin adminLogin(String username, String password) throws SQLException;
}
