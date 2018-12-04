package com.cskaoyan.servcie.impl;

import com.cskaoyan.bean.User;
import com.cskaoyan.dao.UserDao;
import com.cskaoyan.dao.impl.UserDaoImpl;
import com.cskaoyan.servcie.UserService;
import com.cskaoyan.utils.PageHelper;
import com.cskaoyan.utils.SendJMail;

import java.sql.SQLException;
import java.util.UUID;

public class UserServiceImpl implements UserService {

    private UserDao dao = new UserDaoImpl();


    @Override
    public boolean updateUser(User user) throws SQLException {
        return dao.updateUser(user);
    }

    @Override
    public String validateUserForUpdate(User user) {

        return validateEmailAndBirthday(user);
    }

    @Override
    public User userLogin(String username, String password) throws SQLException {


        return dao.checkUserLogin(username, password);
    }

    @Override
    public PageHelper<User> findPartUser(String num) throws SQLException {
        PageHelper<User> page = new PageHelper<>();
        int currentPageNum = Integer.parseInt(num);

        // 总记录数据项数 totalRecordsNum
        page.setTotalRecordsNum(dao.findAllUserCount(), PageHelper.USER_PER_PAGE);

        // 当前页码 currentPageNum
        page.setCurrentPageNum(currentPageNum);

        int limit = PageHelper.USER_PER_PAGE;
        int offset = limit * (currentPageNum - 1);

        // 记录数据 records
        page.setRecords(dao.findPartUser(limit, offset));

        return page;
    }

    @Override
    public String validateUser(User user) throws SQLException {
        String message;

        // 用户名不为空
        if (null != user.getUsername()) {
            // 用户名不重复
            if (isUsernameAvailable(user.getUsername())) {
                // 邮箱格式正确和日期
                message = validateEmailAndBirthday(user);
            } else {
                message = "用户名重复，请重新输入...";
            }
        } else {
            message = "用户名为空，请重新输入...";
        }

        return message;
    }

    private String validateEmailAndBirthday(User user) {
        String message;
        // 邮箱格式正确
        if (user.getEmail().matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*[.]\\w+([-.]\\w+)*$")) {
            // 日期格式正确
            if (user.getBirthday().matches("^\\d{4}-(0?[1-9]|1[0-2])-((0?[1-9])|([12][0-9])|30|31)$")) {
                message = "YES";
            } else {
                message = "日期格式错误，请重新输入...";
            }
        } else {
            message = "邮箱格式错误，请重新输入...";
        }
        return message;
    }

    public boolean isUsernameAvailable(String username) throws SQLException {
        return dao.isUsernameAvailable(username);
    }

    /**
     * 注册情况下，set status = 0
     * @param user
     * @return
     * @throws SQLException
     */
    @Override
    public boolean saveUser(User user) throws SQLException {
        // 用户注册，验证邮箱
        // 创建一个 UUID，并设置用户状态码为 0
        user.setActiveCode(UUID.randomUUID().toString());
        user.setStatus(0);

        //sendVerifyEmail(user);

        user.setPassword(user.getPassword());

        return dao.saveUser(user);
    }

    @Override
    public boolean sendVerifyEmail(User user) {
        // 构造邮件信息，附带 UUID 跳转连接
        String url = "http://192.168.3.4:8080/user/UserServlet?op=activeUser&activeCode=" + user.getActiveCode();
        String msg = "<a href='" + url + "'>点击激活邮箱...</a>";

        // 向用户发送验证邮件，
        return SendJMail.sendMail(user.getEmail(), msg);
    }


    /**
     * set status = 1
     * @param activeCode
     * @return
     * @throws SQLException
     */
    @Override
    public boolean activeUser(String activeCode) throws SQLException {
        return dao.activeUser(activeCode);
    }
}
