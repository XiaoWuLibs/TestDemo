package com.example.myapplication.callBack;

import com.example.myapplication.bean.Account;

/**
 * author:wsl
 * 创建时间：2021/9/29 9:38
 * 描述：
 */

public interface MVVMCallBack {
    void onSuccess(Account account);

    void onFailed();
}
