package com.example.myapplication.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

/**
 * author:wsl
 * 创建时间：2021/5/18 11:48
 * 描述：
 */

public class MyTextView3 extends TextView {
    //内容填充画笔
    private Paint contentPaint;
    //字体大小
    private int textSize = 60;
    //默认当前的颜色
    private String textColor = "#444444";
    //文字测量工具
    private Paint.FontMetricsInt textFm;
    //单行文字的高度
    private int signleLineHeight;

    public MyTextView3(Context context) {
        super(context);
        init();
    }

    public MyTextView3(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyTextView3(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    /**
     * 初始化
     */
    private void init() {
        contentPaint = new Paint();
        contentPaint.setTextSize(textSize);
        contentPaint.setAntiAlias(true);
        contentPaint.setStrokeWidth(2);
        contentPaint.setColor(Color.parseColor(textColor));
        contentPaint.setTextAlign(Paint.Align.LEFT);
        textFm = contentPaint.getFontMetricsInt();
        signleLineHeight = Math.abs(textFm.top - textFm.bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(new Rect(0, 0, 100, 60), contentPaint);
        super.onDraw(canvas);
//        drawText(canvas);
    }

}
