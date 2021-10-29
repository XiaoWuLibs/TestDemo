package com.example.myapplication.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * author:wsl
 * 创建时间：2021/8/17 17:45
 * 描述：
 */

public class MultiTouchView3to2 extends View {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private SparseArray<Path> paths = new SparseArray<>();

    public MultiTouchView3to2(Context context) {
        this(context, null);
    }

    public MultiTouchView3to2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiTouchView3to2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
//        设置stroke 的cap样式
        paint.setStrokeCap(Paint.Cap.SQUARE);
//        设置连接处样式
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < paths.size(); i++) {
            canvas.drawPath(paths.valueAt(i), paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                int actionIndex1 = event.getActionIndex();
                Path path = new Path();
                path.moveTo(event.getX(actionIndex1), event.getY(actionIndex1));
                paths.append(event.getPointerId(actionIndex1), path);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                for (int i = 0; i < paths.size(); i++) {
                    paths.valueAt(i).lineTo(event.getX(i), event.getY(i));
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                int actionIndex = event.getActionIndex();
                int pointerId = event.getPointerId(actionIndex);
                paths.remove(pointerId);
                invalidate();
                break;
        }
        return true;
    }
}
