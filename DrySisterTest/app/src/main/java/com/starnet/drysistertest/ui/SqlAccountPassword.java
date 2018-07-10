package com.starnet.drysistertest.ui;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangyong on 18-7-10.
 */

public class SqlAccountPassword {

    /**创建数据库*/
    public void CreateDatabase() {
        Connector.getDatabase();
    }

    /**增加*/
    public boolean AddDatabase(String account, String password){
        if (account.isEmpty() || password.isEmpty()) {
            return false;
        }
        LoginData loginData = new LoginData();
        loginData.setAccount(account);
        loginData.setPassword(password);
        loginData.save();
        return true;
    }

    /**删除*/

    /**登入验证*/
    public boolean ValidateLogin(String account, String password) {
        List<LoginData> loginDatas = DataSupport.findAll(LoginData.class);
        for (LoginData loginData: loginDatas) {
            if (loginData.getAccount().equals(account) && loginData.getPassword().equals(password)) {
                return true;
            } else {
            }
        }
        return false;
    }

    /**注册验证*/
    public boolean ValidateRegister(String account) {
        List<LoginData> loginDatas = DataSupport.findAll(LoginData.class);
        for (LoginData loginData: loginDatas) {
            if (loginData.getAccount().equals(account)) {
                return true;
            } else {
            }
        }
        return false;
    }

}
