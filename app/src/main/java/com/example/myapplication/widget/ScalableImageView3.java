package com.example.myapplication.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.OverScroller;
import android.widget.Scroller;

import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.ViewCompat;

import com.example.myapplication.R;

/**
 * author:wsl
 * 创建时间：2021/8/5 8:59
 * 描述：图片缩放控件 第二讲升级版
 */

public class ScalableImageView3 extends View {
    private final String TAG = this.getClass().getSimpleName();
    private static final int IMAGE_SIZE = 400;
    private static final float EXTRA_SCALE_FACTOR = 1.5f;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap bitmap = null;
    private float originalOffsetX = 0;
    private float originalOffsetY = 0;
    private float offsetX = 0;
    private float offsetY = 0;
    private float smallScale = 0f;
    private float bigScale = 0f;
    private boolean big = false;
    private float currentScale;
    private ObjectAnimator objectAnimator;
    //    Scroller 和 OverScroller的区别：
//    1、Scroller不支持overX和overY；
//    2、Scroller的初始速度失效，无论初始速度为多少，都是同样的速度；
    private OverScroller overScroller;
    private Scroller scroller;
    private HenFlingRunner flingRunner = new HenFlingRunner();
    private HenGestureListener henGestureListener = new HenGestureListener();
    private GestureDetectorCompat gestureDetectorCompat = new GestureDetectorCompat(getContext(), henGestureListener);
    private HenScaleGestureListener henScaleGestureListener = new HenScaleGestureListener();
    private ScaleGestureDetector scaleGestureDetector = new ScaleGestureDetector(getContext(), henScaleGestureListener);

    public ScalableImageView3(Context context) {
        this(context, null);
    }

    public ScalableImageView3(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScalableImageView3(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        bitmap = getAvatar(IMAGE_SIZE);
        objectAnimator = ObjectAnimator.ofFloat(ScalableImageView3.this, "currentScale", smallScale, bigScale);
        overScroller = new OverScroller(context);
        scroller = new Scroller(context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        /**
         * 为图片设置偏移量，偏移量 = （view宽 - 图片尺寸）/2
         */
        originalOffsetX = (getWidth() - IMAGE_SIZE) / 2;
        originalOffsetY = (getHeight() - IMAGE_SIZE) / 2;

        if (Float.valueOf(bitmap.getWidth() / bitmap.getHeight()) > Float.valueOf(getWidth() / getHeight())) {
            smallScale = getWidth() / bitmap.getWidth();
            bigScale = getHeight() / bitmap.getHeight() * EXTRA_SCALE_FACTOR;
        } else {
            smallScale = getHeight() / bitmap.getHeight();
            bigScale = getWidth() / bitmap.getWidth() * EXTRA_SCALE_FACTOR;
        }

        currentScale = smallScale;
        objectAnimator.setFloatValues(smallScale, bigScale);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float scaleFraction = (currentScale - smallScale) / (bigScale - smallScale);
        canvas.translate(offsetX * scaleFraction, offsetY * scaleFraction);
        canvas.scale(currentScale, currentScale, getWidth() / 2, getHeight() / 2);
        canvas.drawBitmap(bitmap, originalOffsetX, originalOffsetY, paint);
    }

    private Bitmap getAvatar(int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.icon_android3, options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = width;
        return BitmapFactory.decodeResource(getResources(), R.drawable.icon_android3, options);
    }

    public float getCurrentScale() {
        return currentScale;
    }

    public void setCurrentScale(float currentScale) {
        this.currentScale = currentScale;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        touch事件的混合使用
        scaleGestureDetector.onTouchEvent(event);
        if (!scaleGestureDetector.isInProgress()) {
            gestureDetectorCompat.onTouchEvent(event);
        }
        return true;
    }


    /**
     * 边缘修正
     */
    private void fixOffsets() {
        //大于最小值，小于最大值
        offsetX = Math.min(offsetX, (bitmap.getWidth() * bigScale - getWidth()) / 2);
        offsetX = Math.max(offsetX, -(bitmap.getWidth() * bigScale - getWidth()) / 2);

        //大于最小值，小于最大值
        offsetY = Math.min(offsetY, (bitmap.getHeight() * bigScale - getHeight()) / 2);
        offsetY = Math.max(offsetY, -(bitmap.getHeight() * bigScale - getHeight()) / 2);
    }

    private class HenGestureListener extends GestureDetector.SimpleOnGestureListener {
        /**
         * 每次 ACTION_DOWN 事件出现的时候都会被调用
         * 这里返回true才会处理后续的手势操作，如果为false，不会处理此后的其他事件
         *
         * @param e
         * @return
         */
        @Override
        public boolean onDown(MotionEvent e) {

            return true;
        }

        /**
         * 相当于onMove事件
         *
         * @param downEvent    onDown方法返回true时的MotionEvent
         * @param currentEvent 最新的当前事件
         * @param distanceX    本次事件 和 上次事件 两个点之间的距离  （distance = 旧点 - 新点），所以使用的时候前面加个负号
         * @param distanceY
         * @return 返回值表示是否消费了事件
         */
        @Override
        public boolean onScroll(MotionEvent downEvent, MotionEvent currentEvent, float distanceX, float distanceY) {
//        意义：相当于onMove事件
            if (big) {
                offsetX -= distanceX;
                offsetY -= distanceY;
                fixOffsets();
                invalidate();
            }
            return true;
        }


        /**
         * 手指快速滑动
         * 手指在 屏幕滑动 并 迅速抬起时 被触发，用于用户希望 控件进行惯性滑动 的场景
         * view如何滑动，需要自己事件
         *
         * @param downEvent    onDown方法返回true时的MotionEvent
         * @param currentEvent 最新的当前事件
         * @param velocityX    横向速率
         * @param velocityY    纵向速率
         * @return
         */
        @Override
        public boolean onFling(MotionEvent downEvent, MotionEvent currentEvent, float velocityX, float velocityY) {
            /**
             * 开始快速滑动时调用该方法
             *
             * @param startX 快速滑动，松手时的坐标X 相对值（和view坐标系无关）
             * @param startY 快速滑动，松手时的坐标Y 相对值（和view坐标系无关）
             * @param minX view所在范围的边框，左 上右下 相对值（和view坐标系无关）
             * @param maxX view所在范围的边框，左上 右 下 相对值（和view坐标系无关）
             * @param minY view所在范围的边框，左 上 右下 相对值（和view坐标系无关）
             * @param maxY view所在范围的边框，左上右 下 相对值（和view坐标系无关）
             * @param maxY view所在范围的边框，左上右 下 相对值（和view坐标系无关）
             * @param maxY view所在范围的边框，左上右 下 相对值（和view坐标系无关）
             * @param overX view到达边框后，再拉显示留白的宽度，
             * @param overY view到达边框后，再拉显示留白的宽度，
             * @return true if a springback was initiated, false if startX and startY were
             *          already within the valid range.
             */
            if (big) {
                overScroller.fling((int) offsetX
                        , (int) offsetY
                        , (int) velocityX
                        , (int) velocityY
                        , -(int) (bitmap.getWidth() * bigScale - getWidth()) / 2
                        , (int) (bitmap.getWidth() * bigScale - getWidth()) / 2
                        , -(int) (bitmap.getHeight() * bigScale - getHeight()) / 2
                        , (int) (bitmap.getHeight() * bigScale - getHeight()) / 2
                        , 100
                        , 100);

//            postOnAnimation() 和 post()区别：
//            postOnAnimation()下一帧到来时推到主线程显示 ，
//            而 post()同一时间可能会推好几帧到主线程
                ViewCompat.postOnAnimation(ScalableImageView3.this, flingRunner);

//            post(this);
            }
            return true;
        }


        /**
         * 按下 抬起 再按下 会触发该事件
         * <p>
         * 注意：第二次按下时就调用，而不是抬起时
         * <p>
         * 两次按下事件间隔不超过300ms，且不少于40ms
         *
         * @param e
         * @return
         */
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            big = !big;
            if (big) {
//                图片放大 跟手处理，图片双击缩放，放大滑动  再双击缩小 导致的图片偏移
                offsetX = -(bigScale / smallScale - 1) * (e.getX() - getWidth() / 2);
                offsetY = -(bigScale / smallScale - 1) * (e.getY() - getHeight() / 2);
//                双击的边缘修正
                fixOffsets();
                objectAnimator.start();
            } else {
                objectAnimator.reverse();
            }
            return true;
        }
    }

    private class HenScaleGestureListener implements ScaleGestureDetector.OnScaleGestureListener {
        /**
         * 手指缩放过程的事件
         *
         * @param detector ScaleGestureDetector 手指缩放探测器
         * @return 这个返回值表示「事件是否消耗」，即「这次这个事件算不算数」
         */
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
//            缩放倍数，范围：0 ~ 正无穷
//            detector.getScaleFactor()
            float tempCurrentScale = detector.getScaleFactor() * currentScale;

//            缩放 边缘修正
            if (tempCurrentScale < smallScale || tempCurrentScale > bigScale) {
                return false;
            } else {
                currentScale = detector.getScaleFactor() * currentScale;
                invalidate();
                return true;
            }

        }

        /**
         * 手指缩放开始事件
         * 这个事件必须返回true，Scale事件才会生效
         *
         * @param detector
         * @return
         */
        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
//            缩放 跟手处理
            offsetX = -(bigScale / smallScale - 1) * (detector.getFocusX() - getWidth() / 2);
            offsetY = -(bigScale / smallScale - 1) * (detector.getFocusY() - getHeight() / 2);
            return true;
        }

        /**
         * 手指缩放结束事件
         *
         * @param detector
         */
        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {

        }
    }

    private class HenFlingRunner implements Runnable {
        @Override
        public void run() {
            if (overScroller.computeScrollOffset()) {
                offsetX = overScroller.getCurrX();
                offsetY = overScroller.getCurrY();
                invalidate();

                ViewCompat.postOnAnimation(ScalableImageView3.this, this);
//            post(this);
            }
        }
    }
}
