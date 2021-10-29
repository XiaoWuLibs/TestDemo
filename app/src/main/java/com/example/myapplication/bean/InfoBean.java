package com.example.myapplication.bean;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.example.myapplication.BR;

/**
 * author:wsl
 * 创建时间：2021/6/22 14:13
 * 描述：
 */

public class InfoBean extends BaseObservable {

    private String firstName;
    private String lastName;
    private boolean visiable;

    @Bindable
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        notifyPropertyChanged(BR.firstName);
    }

    @Bindable
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        notifyPropertyChanged(BR.lastName);
    }

    @Bindable
    public boolean isVisiable() {
        return visiable;
    }

    public void setVisiable(boolean visiable) {
        this.visiable = visiable;
        notifyPropertyChanged(BR.visiable);
    }
}
