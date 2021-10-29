package com.example.myapplication.base;

import android.app.Application;

/**
 * author:wsl
 * 创建时间：2021/6/10 11:42
 * 描述：
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("onCreate");
    }

    @Override
    public void onTerminate() {
        System.out.println("onTerminate");
        super.onTerminate();
    }
}
