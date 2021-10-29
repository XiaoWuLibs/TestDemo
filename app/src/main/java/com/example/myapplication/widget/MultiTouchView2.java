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
 * 创建时间：2021/8/17 8:25
 * 描述：协作型多点触控
 * 多个手指进行操作view。滑动方向相同时全速滑动；反向滑动时位移中和，view保持不变。
 * <p>
 * 思路：协作型多点触控指多个手指对view进行操作，这种情况可以把多个手指对应的点取平均值。
 * 相当于对多个手指的中心点进行位移操作。计算公式：（point1+point2+point3+...+pointn）/n
 */

public class MultiTouchView2 extends View {
    public final String TAG = this.getClass().getName();
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap bitmap;
    private float downX;
    private float downY;
    private float offsetX;
    private float offsetY;
    private float originalOffsetX;
    private float originalOffsetY;

    public MultiTouchView2(Context context) {
        this(context, null);
    }

    public MultiTouchView2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiTouchView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        float focusX;
        float focusY;
        float sumX = 0;
        float sumY = 0;
        int pointerCount = event.getPointerCount();
//        当前点击事件是否为非最后一根手指抬起
        boolean isPointerUp = event.getActionMasked() == MotionEvent.ACTION_POINTER_UP;


//       如果MotionEvent.ACTION_POINTER_UP触发，则 event.getPointerCount() 的值包含刚刚抬起的这根手指
        for (int i = 0; i < event.getPointerCount(); i++) {
//            如果是非最后一根手指抬起 并且 抬起的那根手指坐标 不进行求和中心点计算
            if (!(isPointerUp && i == event.getActionIndex())) {
                sumX += event.getX(i);
                sumY += event.getY(i);
            }
        }
        if (isPointerUp) {
            pointerCount--;
        }
        focusX = sumX / pointerCount;
        focusY = sumY / pointerCount;

        switch (event.getActionMasked()) {
//            有手指按下或者抬起时，都需要重新进行赋值中心点
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_POINTER_UP:
//                第一根手指的按下
                downX = focusX;
                downY = focusY;
                originalOffsetX = offsetX;
                originalOffsetY = offsetY;
//                Log.e(TAG, "ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
//                所有手指的移动 都会触发该事件
                offsetX = focusX - downX + originalOffsetX;
                offsetY = focusY - downY + originalOffsetY;
                invalidate();
//                Log.e(TAG, "ACTION_MOVE");
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
