package com.example.myapplication.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * author:wsl
 * 创建时间：2021/8/24 15:45
 * 描述：
 */

public class DefineGroupLayout extends ViewGroup {
    public DefineGroupLayout(Context context) {
        this(context, null);
    }

    public DefineGroupLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DefineGroupLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            LayoutParams layoutParams = childView.getLayoutParams();

            childView.layout(0, 0, layoutParams.width, layoutParams.height);
        }
    }
}
