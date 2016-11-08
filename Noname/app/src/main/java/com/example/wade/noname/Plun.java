package com.example.wade.noname;

import cn.bmob.v3.BmobObject;

/**
 * Created by wade on 2016/11/8.
 */
public class Plun extends BmobObject {
    String username;
    String pluntalk;
    String talkid;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPluntalk() {
        return pluntalk;
    }

    public void setPluntalk(String pluntalk) {
        this.pluntalk = pluntalk;
    }

    public String getTalkid() {
        return talkid;
    }

    public void setTalkid(String talkid) {
        this.talkid = talkid;
    }
}
