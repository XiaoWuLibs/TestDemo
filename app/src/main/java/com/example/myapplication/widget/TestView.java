package com.example.myapplication.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathMeasure;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.myapplication.utils.FontUtils;

/**
 * author:wsl
 * 创建时间：2021/6/29 10:03
 * 描述：
 */

public class TestView extends View {
    private static final float RADIUS = 200f;
    private float OPEN_ANGLE = 120f;
    private final float DASH_WIDTH = 3;
    private final float DASH_HEIGHT = 10;
    private Paint paint = new Paint();
    private Path path = new Path();
    private Path dash = new Path();
    private PathDashPathEffect effect;

    public TestView(Context context) {
        super(context);
        init();
    }

    public TestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        dash.addRect(0, 0, DASH_WIDTH, DASH_HEIGHT, Path.Direction.CCW);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        path.reset();
//        Path.Direction.CCW：代表绘制的方向，CW：顺时针 ； CCW：逆时针
//        path.addCircle(getWidth() / 2, getHeight() / 2, RADIUS, Path.Direction.CCW);
//        path.addRect(getWidth() / 2 - RADIUS
//                , getHeight() / 2
//                , getWidth() / 2 + RADIUS
//                , getHeight() / 2 + 2 * RADIUS
//                , Path.Direction.CCW);
//        path.addCircle(getWidth() / 2, getHeight() / 2, RADIUS * 1.5f, Path.Direction.CCW);
//
//        path.setFillType(Path.FillType.EVEN_ODD);
        path.reset();
        path.addArc(getWidth() / 2 - RADIUS
                , getHeight() / 2 - RADIUS
                , getWidth() / 2 + RADIUS
                , getHeight() / 2 + RADIUS
                , 90 + OPEN_ANGLE / 2
                , 360 - OPEN_ANGLE);

        PathMeasure pathMeasure = new PathMeasure(path, false);

        effect = new PathDashPathEffect(dash, (pathMeasure.getLength() - DASH_WIDTH) / 20, 0, PathDashPathEffect.Style.MORPH);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.drawPath(path, paint);
        canvas.drawPath(path, paint);


        paint.setPathEffect(effect);
        canvas.drawPath(path, paint);

        paint.setPathEffect(null);


        canvas.drawLine(getWidth() / 2
                , getHeight() / 2
                , getWidth() / 2 + (RADIUS-15) * (float) Math.cos(Math.toRadians((90 + OPEN_ANGLE / 2) + (360 - OPEN_ANGLE) / 20 * 15))
                , getHeight() / 2 + (RADIUS-15) * (float) Math.sin(Math.toRadians((90 + OPEN_ANGLE / 2) + (360 - OPEN_ANGLE) / 20 * 15))
                , paint);
    }
}
