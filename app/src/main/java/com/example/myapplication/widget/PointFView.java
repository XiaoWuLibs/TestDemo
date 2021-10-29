package com.example.myapplication.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * author:wsl
 * 创建时间：2021/7/14 18:01
 * 描述：
 */

public class PointFView extends View {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    //属性值
    private PointF point = new PointF(0, 0);

    public PointFView(Context context) {
        super(context);
        init();
    }

    public PointFView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PointFView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setPoint(PointF pPoint) {
        point = pPoint;
        invalidate();
    }

    private void init() {
        paint.setStrokeWidth(50);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPoint(point.x, point.y, paint);
    }
}
