package com.example.myapplication.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * author:wsl
 * 创建时间：2021/7/12 15:51
 * 描述：
 */

public class CircleView extends View {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float mCenterX;
    private float mCenterY;
    private float radius = 100;

    public CircleView(Context context) {
        super(context);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        init();

    }

    private void init() {
        mCenterX = getMeasuredWidth() / 2;
        mCenterY = getMeasuredHeight() / 2;
    }

    public void setRadius(float pRadius) {
        this.radius = pRadius;
        invalidate();
    }

    public void setMCenterX(float pX) {
        this.mCenterX = pX;
        invalidate();
    }

    public void setMCenterY(float pY) {
        this.mCenterY = pY;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(mCenterX, mCenterY, radius, paint);
    }
}
