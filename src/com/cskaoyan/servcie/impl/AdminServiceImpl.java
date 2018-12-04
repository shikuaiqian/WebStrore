package com.cskaoyan.servcie.impl;



import com.cskaoyan.bean.Admin;
import com.cskaoyan.dao.AdminDao;
import com.cskaoyan.dao.impl.AdminDaoImpl;
import com.cskaoyan.servcie.AdminService;
import com.cskaoyan.utils.PageHelper;

import java.sql.SQLException;
import java.util.List;

public class AdminServiceImpl implements AdminService {

    AdminDao dao = new AdminDaoImpl();

    @Override
    public Admin adminLogin(String username, String password) throws SQLException {
        return dao.adminLogin(username, password);
    }

    @Override
    public PageHelper<Admin> findPartAdmin(String num) throws SQLException {
        PageHelper<Admin> page = new PageHelper<>();
        int currentPageNum = Integer.parseInt(num);

        // 总记录数据项数 totalRecordsNum
        page.setTotalRecordsNum(dao.findAllAdminCount(), PageHelper.ADMIN_PER_PAGE);

        // 当前页码 currentPageNum
        page.setCurrentPageNum(currentPageNum);

        int limit = PageHelper.ADMIN_PER_PAGE;
        int offset = limit * (currentPageNum - 1);

        // 记录数据 records
        page.setRecords(dao.findPartCategory(limit, offset));

        return page;
    }

    @Override
    public boolean isUsernameAvailable(String username) throws SQLException {
        return dao.isUsernameAvailable(username);
    }

    @Override
    public boolean updateAdmin(String aid, String password) throws SQLException {
        return dao.updateAdmin(aid, password);
    }

    @Override
    public List<Admin> findAllAdmin() throws SQLException {
        return dao.findAllAdmin();
    }

    @Override
    public boolean saveAdmin(Admin admin) throws SQLException {
        return dao.saveAdmin(admin);
    }
}
