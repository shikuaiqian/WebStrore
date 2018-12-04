package com.cskaoyan.form;

import java.util.HashMap;
import java.util.Map;

public class UserFormBean {
    private String username;
    private String nickname;
    private String password;
    private String email;
    private String birthday;
    public Map<String, String> error = new HashMap<>();

    public boolean invalidate() {
        // 用户名不为空
        if (null == username) {
            error.put("username", "用户名为空，请重新输入...");
        }

        //其他的字段如果需要验证都可以增加到这里... 集中来验证参数合法性

        // 邮箱格式正确
        if (!email.matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*[.]\\w+([-.]\\w+)*$")) {
            error.put("email", "邮箱格式错误，请重新输入...");
        }

        // 日期格式正确
        if (!birthday.matches("^\\d{4}-(0?[1-9]|1[0-2])-((0?[1-9])|([12][0-9])|30|31)$")) {
            error.put("birthday", "日期格式错误，请重新输入...");
        }

        return error.isEmpty();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Map<String, String> getError() {
        return error;
    }

    public void setError(Map<String, String> error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "UserFormBean{" +
                "username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", birthday='" + birthday + '\'' +
                ", error=" + error +
                '}';
    }
}
