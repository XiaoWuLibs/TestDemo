package com.example.myapplication.model;

import com.example.myapplication.bean.Account;
import com.example.myapplication.callBack.MVVMCallBack;

import java.util.Random;

/**
 * author:wsl
 * 创建时间：2021/9/29 9:39
 * 描述：
 */

public class MVVMModel {
    public static MVVMModel newMVVMModel() {
        return new MVVMModel();
    }

    public void getAccountData(String accountName, MVVMCallBack callBack) {
        Random random = new Random();
        boolean isSuccess = random.nextBoolean();
        if (isSuccess) {
            Account account = Account.newAccount();
            account.name = accountName;
            account.level = 100;
            callBack.onSuccess(account);
        } else {
            callBack.onFailed();
        }
    }
}
