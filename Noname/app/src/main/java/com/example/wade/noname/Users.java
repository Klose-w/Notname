package com.example.wade.noname;

import cn.bmob.v3.BmobObject;

/**
 * Created by wade on 2016/11/6.
 */
public class Users extends BmobObject {
    private String username;
    private String userpassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }
}
