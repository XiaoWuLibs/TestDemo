package com.example.myapplication.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * author:wsl
 * 创建时间：2021/7/19 14:55
 * 描述：onMeasure
 */

public class CircleView2 extends View {
    private int RADIDS = 300;
    private int PADDING = 200;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public CircleView2(Context context) {
        super(context);
    }

    public CircleView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        不使用父View的onMeasure()方法计算，通过自己的算法计算宽高
        int size = (PADDING + RADIDS) * 2;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int resultWidth;
//        这部分可以替换为resolveSize
        switch (widthMode) {
            case MeasureSpec.AT_MOST:
                if (size > widthSpecSize) {
                    resultWidth = widthSpecSize;
                } else {
                    resultWidth = size;
                }
                break;
            case MeasureSpec.EXACTLY:
                resultWidth = widthSpecSize;
                break;
            case MeasureSpec.UNSPECIFIED:
            default:
                resultWidth = size;
                break;
        }

        int resultHeight = resolveSize(size, heightMeasureSpec);
        setMeasuredDimension(resultWidth, resultHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(PADDING + RADIDS, PADDING + RADIDS, RADIDS, paint);
    }
}
