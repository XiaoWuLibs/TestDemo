package com.example.myapplication.config;

import androidx.databinding.ObservableField;

/**
 * author:wsl
 * 创建时间：2021/5/21 11:01
 * 描述：
 */

public class ConfigBean {
    private static ConfigBean configBean;

    public static ConfigBean getInstance() {
        if (configBean == null) {
            configBean = new ConfigBean();
        }
        return configBean;
    }

    public ConfigBean() {
        this.count.set(0);
    }

    private ObservableField<Integer> userId = new ObservableField<>();
    private ObservableField<String> userName = new ObservableField<>();
    private ObservableField<Integer> count = new ObservableField<>();

    public ObservableField<Integer> getUserId() {
        return userId;
    }

    public ObservableField<String> getUserName() {
        return userName;
    }

    public ObservableField<Integer> getCount() {
        return count;
    }
}
