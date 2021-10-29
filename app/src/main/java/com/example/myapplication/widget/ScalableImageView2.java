package com.example.myapplication.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.OverScroller;

import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.ViewCompat;

import com.example.myapplication.R;

/**
 * author:wsl
 * 创建时间：2021/8/6 14:13
 * 描述：图片缩放控件 第一讲练习版
 */

public class ScalableImageView2 extends View implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, Runnable {
    private final String TAG = this.getClass().getSimpleName();
    private static final int IMG_SIZE = 400;
    private static final float EXTRA_SCALE_FACTOR = 1.5f;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap bitmap;
    private float originalOffsetX = 0;
    private float originalOffsetY = 0;
    private float offsetX = 0;
    private float offsetY = 0;
    private float smallScale = 0;
    private float bigScale = 0;
    private boolean isBig = false;
    private float scaleVelocity;
    private ObjectAnimator objectAnimator;
    private OverScroller overScroller;
    private GestureDetectorCompat gestureDetectorCompat = new GestureDetectorCompat(getContext(), this);

    public ScalableImageView2(Context context) {
        this(context, null);
    }

    public ScalableImageView2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScalableImageView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        bitmap = getAvator(IMG_SIZE);
        gestureDetectorCompat.setOnDoubleTapListener(this);
        overScroller = new OverScroller(context);
        objectAnimator = ObjectAnimator.ofFloat(this, "scaleVelocity", 0, 1);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        originalOffsetX = (getWidth() - bitmap.getWidth()) / 2;
        originalOffsetY = (getHeight() - bitmap.getHeight()) / 2;

        if (bitmap.getWidth() / bitmap.getHeight() > getWidth() / getHeight()) {
            smallScale = getWidth() / bitmap.getWidth();
            bigScale = getHeight() / bitmap.getHeight() * EXTRA_SCALE_FACTOR;
        } else {
            smallScale = getHeight() / bitmap.getHeight();
            bigScale = getWidth() / bitmap.getWidth() * EXTRA_SCALE_FACTOR;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(offsetX, offsetY);

        float scale = smallScale + (bigScale - smallScale) * scaleVelocity;
        canvas.scale(scale, scale, getWidth() / 2, getHeight() / 2);
        canvas.drawBitmap(bitmap, originalOffsetX, originalOffsetY, paint);
    }

    private Bitmap getAvator(int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.icon_android3, options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = width;
        return BitmapFactory.decodeResource(getResources(), R.drawable.icon_android3, options);
    }

    public float getScaleVelocity() {
        return scaleVelocity;
    }

    public void setScaleVelocity(float scaleVelocity) {
        this.scaleVelocity = scaleVelocity;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetectorCompat.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        offsetX -= distanceX;
        offsetY -= distanceY;
        if (isBig) {
            offsetX = Math.min(offsetX, (bitmap.getWidth() * bigScale - getWidth()) / 2);
            offsetX = Math.max(offsetX, -(bitmap.getWidth() * bigScale - getWidth()) / 2);
            offsetY = Math.min(offsetY, (bitmap.getHeight() * bigScale - getHeight()) / 2);
            offsetY = Math.max(offsetY, -(bitmap.getHeight() * bigScale - getHeight()) / 2);
        } else {
            offsetX = Math.min(offsetX, (getWidth() - bitmap.getWidth() * smallScale) / 2);
            offsetX = Math.max(offsetX, -(getWidth() - bitmap.getWidth() * smallScale) / 2);
            offsetY = Math.min(offsetY, (getHeight() - bitmap.getHeight() * smallScale) / 2);
            offsetY = Math.max(offsetY, -(getHeight() - bitmap.getHeight() * smallScale) / 2);
        }
        invalidate();
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        if (isBig) {
            overScroller.fling((int) offsetX
                    , (int) offsetY
                    , (int) velocityX
                    , (int) velocityY
                    , -(int) (bitmap.getWidth() * bigScale - getWidth()) / 2
                    , (int) (bitmap.getWidth() * bigScale - getWidth()) / 2
                    , -(int) (bitmap.getHeight() * bigScale - getHeight()) / 2
                    , (int) (bitmap.getHeight() * bigScale - getHeight()) / 2);

            ViewCompat.postOnAnimation(this, this);
        } else {
            overScroller.fling((int) offsetX
                    , (int) offsetY
                    , (int) velocityX
                    , (int) velocityY
                    , -(int) (getWidth() - bitmap.getWidth() * smallScale) / 2
                    , (int) (getWidth() - bitmap.getWidth() * smallScale) / 2
                    , -(int) (getHeight() - bitmap.getHeight() * smallScale) / 2
                    , (int) (getHeight() - bitmap.getHeight() * smallScale) / 2);

            ViewCompat.postOnAnimation(this, this);
        }

        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        isBig = !isBig;
        if (isBig) {
            objectAnimator.start();
        } else {
            objectAnimator.reverse();
        }
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public void run() {
        if (overScroller.computeScrollOffset()) {
            offsetX = overScroller.getCurrX();
            offsetY = overScroller.getCurrY();
            invalidate();

            ViewCompat.postOnAnimation(this, this);
        }
    }
}
