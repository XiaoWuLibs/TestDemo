package com.example.myapplication.bean;

import android.util.Log;

/**
 * author:wsl
 * 创建时间：2021/9/29 9:37
 * 描述：
 */

public class Account {
    public String name;
    public int level;

    public Account() {
    }

    public Account(String name) {
        this.name = name;
    }

    public static Account newAccount() {
        return new Account();
    }

    public void eat() {
        Log.e("Account", "EAT");
    }
}
