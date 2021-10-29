package com.example.myapplication.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathMeasure;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * author:wsl
 * 创建时间：2021/8/20 11:24
 * 描述：
 */

public class TestView08201 extends View {
    private static final float RADIUS = 200f;
    private final float OPEN_ANGLE = 120f;
    private final float DASH_WIDTH = 8;
    private final float DASH_HEIGHT = 15;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path path = new Path();
    private Path dash = new Path();
    private PathDashPathEffect effect;

    public TestView08201(Context context) {
        this(context, null);
    }

    public TestView08201(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestView08201(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        dash.addRect(0, 0, DASH_WIDTH, DASH_HEIGHT, Path.Direction.CW);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        path.reset();
        path.addArc(getWidth() / 2 - RADIUS, getHeight() / 2 - RADIUS
                , getWidth() / 2 + RADIUS, getHeight() / 2 + RADIUS
                , 90 + OPEN_ANGLE / 2, 360 - OPEN_ANGLE);

        PathMeasure pathMeasure = new PathMeasure(path, false);
        effect = new PathDashPathEffect(dash, (pathMeasure.getLength() - DASH_WIDTH) / 20, 0, PathDashPathEffect.Style.MORPH);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, paint);

        paint.setPathEffect(effect);
        canvas.drawPath(path, paint);

    }
}
