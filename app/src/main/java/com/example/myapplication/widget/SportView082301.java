package com.example.myapplication.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.myapplication.R;

import java.util.Random;

/**
 * author:wsl
 * 创建时间：2021/8/23 14:11
 * 描述：
 */

public class SportView082301 extends androidx.appcompat.widget.AppCompatTextView {
    private String[] colors = {"#3f51b5", "#2196f3", "#009688", "#ff9800", "#795548"};
    private static final int PADDING = 10;
    private static final int RADIUS = 20;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public SportView082301(Context context) {
        this(context, null);
    }

    public SportView082301(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SportView082301(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTextColor(Color.parseColor("#ffffff"));
        setGravity(Gravity.CENTER);
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

