package com.example.myapplication.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.myapplication.R;

/**
 * author:wsl
 * 创建时间：2021/8/18 15:01
 * 描述：
 */

public class MultiTouchView1to2 extends View {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap bitmap;
    private float originalOffsetX;
    private float originalOffsetY;
    private float offsetX;
    private float offsetY;
    private float downX;
    private float downY;
    private int pointerId;

    public MultiTouchView1to2(Context context) {
        this(context, null);
    }

    public MultiTouchView1to2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiTouchView1to2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
            case MotionEvent.ACTION_POINTER_DOWN:
                int actionIndex = event.getActionIndex();
                pointerId = event.getPointerId(actionIndex);
                downX = event.getX(actionIndex);
                downY = event.getY(actionIndex);
                originalOffsetX = offsetX;
                originalOffsetY = offsetY;
                break;

            case MotionEvent.ACTION_MOVE:
                int pointerIndex = event.findPointerIndex(pointerId);
                offsetX = event.getX(pointerIndex) - downX + originalOffsetX;
                offsetY = event.getY(pointerIndex) - downY + originalOffsetY;

                invalidate();
                break;

            case MotionEvent.ACTION_POINTER_UP:
                int actionIndex1 = event.getActionIndex();
                int mPointerId = event.getPointerId(actionIndex1);

                if (pointerId == mPointerId) {
                    int newIndex = -1;
                    if (actionIndex1 == event.getPointerCount() - 1) {
                        newIndex = event.getPointerCount() - 2;
                    } else {
                        newIndex = event.getPointerCount() - 1;
                    }

                    pointerId = event.getPointerId(newIndex);
                    downX = event.getX(newIndex);
                    downY = event.getY(newIndex);
                    originalOffsetX = offsetX;
                    originalOffsetY = offsetY;
                }


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
