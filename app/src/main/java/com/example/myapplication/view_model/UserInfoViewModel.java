package com.example.myapplication.view_model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * author:wsl
 * 创建时间：2021/9/29 15:56
 * 描述：
 */

public class UserInfoViewModel extends ViewModel {
    private MutableLiveData<String> name;

    public MutableLiveData<String> getName() {
        if (name == null) {
            name = new MutableLiveData<>();
        }
        return name;
    }


}
