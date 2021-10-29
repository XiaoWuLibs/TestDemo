package com.example.myapplication.lifecycle;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * author:wsl
 * 创建时间：2021/9/30 11:17
 * 描述：
 */

public class MyObserver implements LifecycleObserver {
    public static final String TAG = MyObserver.class.getSimpleName();

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        Log.e(TAG, "Lifecycle.Event.ON_CREATE");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        Log.e(TAG, "Lifecycle.Event.ON_DESTROY");
    }
}
