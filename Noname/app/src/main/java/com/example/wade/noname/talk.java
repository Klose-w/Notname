package com.example.wade.noname;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobSMS;

/**
 * Created by wade on 2016/11/7.
 */
public class talk extends BmobObject {
    private int id;
    private String username;
    private String talk;
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTalk() {
        return talk;
    }

    public void setTalk(String talk) {
        this.talk = talk;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
