package com.starnet.drysistertest.ui;

import org.litepal.crud.DataSupport;

/**
 * Created by yangyong on 18-7-10.
 */

public class LoginData extends DataSupport {
    private int id;
    private String account;
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
