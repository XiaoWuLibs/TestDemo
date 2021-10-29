package com.example.myapplication.bean;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.example.myapplication.BR;

/**
 * author:wsl
 * 创建时间：2021/9/29 14:21
 * 描述：
 */

public class User extends BaseObservable {
    public String name;

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }
}
