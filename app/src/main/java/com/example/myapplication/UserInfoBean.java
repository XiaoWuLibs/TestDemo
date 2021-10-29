package com.example.myapplication;

/**
 * author:wsl
 * 创建时间：2021/6/3 15:36
 * 描述：
 */

public class UserInfoBean {

    public UserInfoBean() {
    }

    public UserInfoBean(int userId) {
        this.userId = userId;
    }

    public int userId;
    public String userName;
    public String userSex;
    public int userAge;
}
