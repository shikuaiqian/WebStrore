package com.cskaoyan.servcie;

import com.cskaoyan.bean.Admin;
import com.cskaoyan.utils.PageHelper;

import java.sql.SQLException;
import java.util.List;

public interface AdminService {
    boolean saveAdmin(Admin admin) throws SQLException;

    List<Admin> findAllAdmin() throws SQLException;

    boolean updateAdmin(String aid, String password) throws SQLException;

    boolean isUsernameAvailable(String username) throws SQLException;

    PageHelper<Admin> findPartAdmin(String num) throws SQLException;

    Admin adminLogin(String username, String password) throws SQLException;
}
