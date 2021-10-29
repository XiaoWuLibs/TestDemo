package com.example.myapplication.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.myapplication.R;

import java.util.Random;

/**
 * author:wsl
 * 创建时间：2021/7/20 10:22
 * 描述：
 */

public class ColoredTextView extends AppCompatTextView {
    private static final int[] COLORS = new int[]{
            Color.parseColor("#e91e63"),
            Color.parseColor("#673ab7"),
            Color.parseColor("#3f51b5"),
            Color.parseColor("#2196f3"),
            Color.parseColor("#009688"),
            Color.parseColor("#ff9800"),
            Color.parseColor("#ff5722"),
            Color.parseColor("#795548")
    };
    private float CORNER_RADIUS = 8;
    private int PADDING_X = 16;
    private int PADDING_Y = 8;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Random random = new Random();

    public ColoredTextView(Context context) {
        super(context);
        init();
    }

    public ColoredTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ColoredTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setTextColor(Color.WHITE);
        paint.setColor(COLORS[random.nextInt(COLORS.length)]);
        setPadding(PADDING_X, PADDING_Y, PADDING_X, PADDING_Y);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRoundRect(0f, 0f, getWidth(), getHeight(), CORNER_RADIUS, CORNER_RADIUS, paint);
        super.onDraw(canvas);
    }
}
