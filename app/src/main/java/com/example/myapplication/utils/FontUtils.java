package com.example.myapplication.utils;

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * author:wsl
 * 创建时间：2021/6/29 13:49
 * 描述：
 */

public class FontUtils {
    public static float dp2px(float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, Resources.getSystem().getDisplayMetrics());
    }
}
