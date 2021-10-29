package com.example.myapplication.utils;

import android.util.Log;

/**
 * author:wsl
 * 创建时间：2021/5/27 11:26
 * 描述：
 */

public class AlarmUtil {
    private static final String TAG = AlarmUtil.class.getName();

    public static AlarmUtil getInstance() {
        return AlarmUtilHolder.mAlarmUtil;
    }

    private static class AlarmUtilHolder {
        private static final AlarmUtil mAlarmUtil = new AlarmUtil();
    }

    /**
     * doSomething
     */
    public void doSomething() {
        Log.e(TAG, "doSomething");
    }
}
