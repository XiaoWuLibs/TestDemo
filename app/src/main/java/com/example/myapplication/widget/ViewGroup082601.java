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
 * 创建时间：2021/8/26 16:31
 * 描述：自动折行的Layout
 */

public class ViewGroup082601 extends ViewGroup {
    private List<Rect> rectList = new ArrayList<>();

    public ViewGroup082601(Context context) {
        this(context, null);
    }

    public ViewGroup082601(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewGroup082601(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int usedWidth = 0;
        int usedHeight = 0;
        int lineUsedWidth = 0;
        int lineMaxHeight = 0;

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, usedHeight);

            if (lineUsedWidth + child.getMeasuredWidth() > widthSize && widthMode != MeasureSpec.AT_MOST) {
                usedHeight += lineMaxHeight;
                lineUsedWidth = 0;
                lineMaxHeight = 0;
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, usedHeight);
            }

            if (i >= rectList.size()) {
                rectList.add(new Rect());
            }
            rectList.get(i).left = lineUsedWidth;
            rectList.get(i).top = usedHeight;
            rectList.get(i).right = lineUsedWidth + child.getMeasuredWidth();
            rectList.get(i).bottom = usedHeight + child.getMeasuredHeight();


            lineUsedWidth += child.getMeasuredWidth();
            usedWidth = Math.max(usedWidth, lineUsedWidth);
            lineMaxHeight = Math.max(lineMaxHeight, child.getMeasuredHeight());

        }
        int parentWidth = usedWidth;
        int parentHeight = usedHeight + lineMaxHeight;
        setMeasuredDimension(parentWidth, parentHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            Rect rect = rectList.get(i);
            View childAt = getChildAt(i);
            childAt.layout(rect.left, rect.top, rect.right, rect.bottom);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
