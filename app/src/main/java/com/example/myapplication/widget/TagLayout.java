package com.example.myapplication.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * author:wsl
 * 创建时间：2021/7/20 10:46
 * 描述：
 */

public class TagLayout extends ViewGroup {
    List<Rect> boundsList = new ArrayList<>();

    public TagLayout(Context context) {
        super(context);
    }

    public TagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        1、测量每个子view的尺寸
        int widthUsed = 0;
        int heightUsed = 0;
        int lineWidthUsed = 0;
        int lineMaxHeight = 0;
        int specWidth = MeasureSpec.getSize(widthMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            measureChildWithMargins(childAt, widthMeasureSpec, 0, heightMeasureSpec, heightUsed);
            if (lineWidthUsed + childAt.getMeasuredWidth() > specWidth) {
                lineWidthUsed = 0;
                heightUsed += lineMaxHeight;
                lineMaxHeight = 0;
            }

            //        2、保存每个子view的尺寸
            if (i >= boundsList.size()) {
                boundsList.add(new Rect());
            }
            Rect rect = boundsList.get(i);
            rect.set(lineWidthUsed, heightUsed, lineWidthUsed + childAt.getMeasuredWidth()
                    , heightUsed + childAt.getMeasuredHeight());

            lineWidthUsed += childAt.getMeasuredWidth();
            widthUsed = Math.max(lineWidthUsed, widthUsed);
            lineMaxHeight = Math.max(lineMaxHeight, childAt.getMeasuredHeight());
        }


//        3、设置viewGroup的宽高
        int selfWidth = widthUsed;
        int selfHeight = heightUsed + lineMaxHeight;
        setMeasuredDimension(selfWidth, selfHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        调用每个子view的layout，设置子view的尺寸
        for (int i = 0; i < getChildCount(); i++) {
            Rect rect = boundsList.get(i);
            getChildAt(i).layout(rect.left, rect.top, rect.right, rect.bottom);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
