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
 * 创建时间：2021/8/17 16:12
 * 描述：画板效果
 * 多根手指可以在屏幕上分别同时进行绘画，且互不干扰。
 * </p>
 * 思路：
 * 第一步，先实现一根手指进行绘制的功能。通过Path进行绘制，第一根手指按下时，记录开始点坐标。
 * 随着手指的移动，不断记录移动过程中的点坐标，并及时更新到画布。
 * 第二步，多根手指按下时，通过map进行记录（这里用的是SparseArray），为每根手指分配一个Path对象用来记录手指轨迹。
 * 随着手指的移动，循环更新全部的Path轨迹并及时通知画布更新。
 * 循环把全部的Path绘制到画布上。
 */

public class MultiTouchView3 extends View {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path path = new Path();
//    private SparseArray<Path> paths = new SparseArray<Path>();

    public MultiTouchView3(Context context) {
        this(context, null);
    }

    public MultiTouchView3(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiTouchView3(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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

//        for (int i = 0; i < paths.size(); i ++) {
//            Path path = paths.valueAt(i);
//            canvas.drawPath(path, paint);
//        }
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
//            case MotionEvent.ACTION_DOWN:
//            case MotionEvent.ACTION_POINTER_DOWN:
//                int actionIndex = event.getActionIndex();
//                Path path = new Path();
//                path.moveTo(event.getX(actionIndex), event.getY(actionIndex));
//                paths.append(event.getPointerId(actionIndex), path);
//                invalidate();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                for (int i = 0; i < paths.size(); i++) {
//                    int pointerId = event.getPointerId(i);
//                    Path path1 = paths.get(pointerId);
//                    path1.lineTo(event.getX(i), event.getY(i));
//                }
//                invalidate();
//                break;
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_POINTER_UP:
//                int actionIndex1 = event.getActionIndex();
//                int pointerId = event.getPointerId(actionIndex1);
//                paths.remove(pointerId);
//                invalidate();
//                break;
            case MotionEvent.ACTION_DOWN:
                int actionIndex = event.getActionIndex();
                path.moveTo(event.getX(), event.getY());
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(event.getX(), event.getY());
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                path.reset();
                invalidate();
                break;
        }
        return true;
    }
}
