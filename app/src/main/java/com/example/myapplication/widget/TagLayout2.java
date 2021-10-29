package com.example.myapplication.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * author:wsl
 * 创建时间：2021/7/21 10:47
 * 描述：
 */

public class TagLayout2 extends ViewGroup {
    private List<Rect> boundsList = new ArrayList<>();

    public TagLayout2(Context context) {
        super(context);
    }

    public TagLayout2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TagLayout2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthUsed = 0;
        int heightUsed = 0;
        int lineMaxHeight = 0;
//        1、测量每个子view的位置并记录
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);

            measureChildWithMargins(childAt, widthMeasureSpec, widthUsed, heightMeasureSpec, heightUsed);

            if (i >= boundsList.size()) {
                boundsList.add(new Rect());
            }
            Rect rect = boundsList.get(i);
            rect.set(widthUsed, heightUsed, widthUsed + childAt.getMeasuredWidth()
                    , heightUsed + childAt.getMeasuredHeight());

            widthUsed += childAt.getMeasuredWidth();
            lineMaxHeight = Math.max(lineMaxHeight, childAt.getMeasuredHeight());
        }
//        2、通过子view，计算出view Group的宽高，保存
        int selfWidth = widthUsed;
        int selfHeight = lineMaxHeight;
        setMeasuredDimension(selfWidth, selfHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        3、调用每个view的layout存储位置信息
        for (int i = 0; i < getChildCount(); i++) {
            Rect rect = boundsList.get(i);
            View view = getChildAt(i);
            view.layout(rect.left, rect.top, rect.right, rect.bottom);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
