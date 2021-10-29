package com.example.myapplication.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * author:wsl
 * 创建时间：2021/6/22 17:57
 * 描述：
 */

public class NameViewModel extends ViewModel {
    private MutableLiveData<String> curName;

    public MutableLiveData<String> getCurName() {
        if (curName == null) {
            curName = new MutableLiveData<>();
        }
        return curName;
    }

    public void setCurName(MutableLiveData<String> curName) {
        this.curName = curName;
    }
}
