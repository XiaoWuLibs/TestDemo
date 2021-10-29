package com.example.myapplication.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;

import java.util.Random;

/**
 * author:wsl
 * 创建时间：2021/8/25 16:05
 * 描述：
 */

public class DefineView0825 extends androidx.appcompat.widget.AppCompatTextView {
    private float RADIUS = 20;
    private int PADDING = 20;
    private String[] colors = {"#f9201a", "#ff0000", "#00ff00", "#0000ff", "#0ff0f0"};
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public DefineView0825(Context context) {
        this(context, null);
    }

    public DefineView0825(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DefineView0825(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setPadding(PADDING, PADDING, PADDING, PADDING);
        paint.setColor(Color.parseColor(colors[new Random().nextInt(colors.length)]));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRoundRect(0, 0, getWidth(), getHeight(), RADIUS, RADIUS, paint);
        super.onDraw(canvas);
    }
}
