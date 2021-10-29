package com.example.myapplication.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * author:wsl
 * 创建时间：2021/7/7 15:10
 * 描述：
 */

public class SportView extends View {
    public static final int RADIUS = 200;
    private Paint paint;
    private String content = "abab";

    public SportView(Context context) {
        super(context);
        init();
    }

    public SportView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SportView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(15);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(getWidth() / 2, getHeight() / 2, RADIUS, paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(150);
        canvas.drawText(content, getWidth() / 2, getHeight() / 2 , paint);

//        Rect rect = new Rect();
//        paint.getTextBounds(content, 0, content.length(), rect);
//        canvas.drawText(content, getWidth() / 2, getHeight() / 2 + (rect.bottom - rect.top) / 2, paint);
        Paint.FontMetrics fontMetrics = new Paint.FontMetrics();
        paint.getFontMetrics(fontMetrics);
        canvas.drawText(content, getWidth() / 2, getHeight() / 2 + (fontMetrics.descent - fontMetrics.ascent) / 2, paint);

        paint.setTextAlign(Paint.Align.LEFT);
        Rect rect = new Rect();
        paint.getTextBounds(content,0,content.length(),rect);
        canvas.drawText(content, 0, 0+(rect.bottom-rect.top), paint);
    }
}
