package com.example.myapplication.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.myapplication.R;

/**
 * author:wsl
 * 创建时间：2021/8/17 8:25
 * 描述：接力型多点触控
 * 一个手指按下view并向上拖动，这时又一个手指落下按住view向上拖动。此时，view会跟随第二根手指移动
 * 第一根手指失效。
 * </p>
 * 思路：
 * 第一根手指落下时，记录落下时的xy及view偏移量，move过程中通过滑动过程中xy与初始xy的差值和偏移量进行计算view的位置。
 * 有第二根手指落下时，更新落点xy及view的偏移量，move过程中通过滑动过程中xy与初始xy的差值和偏移量进行计算view的位置。
 * 非最后一根手指抬起时，更新xy及view的偏移量为非抬起的手指对应的值，move过程中通过滑动过程中xy与初始xy的差值和偏移量进行计算view的位置。
 */

public class MultiTouchView1 extends View {
    public final String TAG = this.getClass().getName();
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap bitmap;
    private float downX;
    private float downY;
    private float offsetX;
    private float offsetY;
    private float originalOffsetX;
    private float originalOffsetY;
    private int pointerId;

    public MultiTouchView1(Context context) {
        this(context, null);
    }

    public MultiTouchView1(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiTouchView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        bitmap = getBitmap(400);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, offsetX, offsetY, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
//                第一根手指的按下
                pointerId = event.getPointerId(0);
                downX = event.getX();
                downY = event.getY();
//                原始偏移
                originalOffsetX = offsetX;
                originalOffsetY = offsetY;
//                Log.e(TAG, "ACTION_DOWN");
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
//                非第一根手指的按下
                int actionIndex = event.getActionIndex();
                pointerId = event.getPointerId(actionIndex);
                downX = event.getX(actionIndex);
                downY = event.getY(actionIndex);
//                原始偏移
                originalOffsetX = offsetX;
                originalOffsetY = offsetY;
//                Log.e(TAG, "ACTION_POINTER_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
//                所有手指的移动 都会触发该事件
                int pointerIndex = event.findPointerIndex(pointerId);
//                整体偏移
                offsetX = event.getX(pointerIndex) - downX + originalOffsetX;
                offsetY = event.getY(pointerIndex) - downY + originalOffsetY;
                invalidate();
//                Log.e(TAG, "ACTION_MOVE");
                break;
            case MotionEvent.ACTION_POINTER_UP:
//                非最后一根手指抬起
                int mActionIndex = event.getActionIndex();
                int mPointerId = event.getPointerId(mActionIndex);
//                如果是正在获取焦点的那根手指谈起，进行处理，否则不处理
                if (mPointerId == pointerId) {
//                    如果屏幕上有三根手指，且有一根抬起，则该事件 event.getPointerCount()==3
                    int newIndex = -1;
                    if (mActionIndex == event.getPointerCount() - 1) {
//                        取倒数第二个值
                        newIndex = event.getPointerCount() - 2;
                    } else {
//                        取最后一个值
                        newIndex = event.getPointerCount() - 1;
                    }
                    pointerId = event.getPointerId(newIndex);
                    downX = event.getX(newIndex);
                    downY = event.getY(newIndex);
                    originalOffsetX = offsetX;
                    originalOffsetY = offsetY;
                }

//                Log.e(TAG, "ACTION_POINTER_UP");
                break;
            case MotionEvent.ACTION_UP:
//                Log.e(TAG, "ACTION_UP");
                break;
            default:
                break;
        }
        return true;
    }

    private Bitmap getBitmap(int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.icon_android3, options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = width;
        return BitmapFactory.decodeResource(getResources(), R.drawable.icon_android3, options);
    }
}
