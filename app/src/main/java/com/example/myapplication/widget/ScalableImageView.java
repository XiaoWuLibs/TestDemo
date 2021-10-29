package com.example.myapplication.widget;

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
 * 描述：图片缩放控件 第一讲初版
 */

public class ScalableImageView extends View implements GestureDetector.OnGestureListener, Runnable {
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
    private float scaleFraction;
    private ObjectAnimator objectAnimator;
    //    Scroller 和 OverScroller的区别：
//    1、Scroller不支持overX和overY；
//    2、Scroller的初始速度失效，无论初始速度为多少，都是同样的速度；
    private OverScroller overScroller;
    private Scroller scroller;
    private GestureDetectorCompat gestureDetectorCompat = new GestureDetectorCompat(getContext(), this);

    public ScalableImageView(Context context) {
        this(context, null);
    }

    public ScalableImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScalableImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        bitmap = getAvatar(IMAGE_SIZE);
        objectAnimator = ObjectAnimator.ofFloat(ScalableImageView.this, "scaleFraction", 0, 1);
        overScroller = new OverScroller(context);
        scroller = new Scroller(context);
        gestureDetectorCompat.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
            /**
             * 用户单机时被调用
             *
             * 和onSingleTapUp() 的区别在于，用户做完一次完整的点击事件后，不会被立即调用，
             * 而是等待300ms，如果没有再次触发单击事件（确认用户不是进行双击），该方法才会被触发，用于兼容双击事件
             * 支持双击时更准确
             * @param e
             * @return
             */
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                return false;
            }

            /**
             * 按下 抬起 再按下 会触发该事件
             *
             * 注意：第二次按下时就调用，而不是抬起时
             *
             * 两次按下事件间隔不超过300ms，且不少于40ms
             * @param e
             * @return
             */
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                big = !big;
                if (big) {
                    objectAnimator.start();
                } else {
                    objectAnimator.reverse();
                }
                return true;
            }

            /**
             * 快速双击事件 的后续操作
             *
             * 触发条件：用户双击第二次按下时、第二次按下后移动时、第二次按下后抬起时都会被调用
             *
             * 使用场景：双击后滑动、双击拖拽等
             *
             * @param e
             * @return
             */
            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                return false;
            }
        });
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
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(offsetX, offsetY);
        float scale = smallScale + (bigScale - smallScale) * scaleFraction;
        canvas.scale(scale, scale, getWidth() / 2, getHeight() / 2);
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

    public float getScaleFraction() {
        return scaleFraction;
    }

    public void setScaleFraction(float scaleFraction) {
        this.scaleFraction = scaleFraction;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetectorCompat.onTouchEvent(event);
    }

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
     * 显示view是否为按下状态的回调。 用户触摸，并且触摸超过100ms才会出发
     *
     * @param e
     */
    @Override
    public void onShowPress(MotionEvent e) {

    }

    /**
     * 只要完成一次单击事件就会被立即触发，不会等待300ms，和onDoubleTap 会有冲突
     * 注：
     * 1、如果支持长按，长按后松手不会调用；
     * 2、双击的第二下时不会被调用
     *
     * @param e
     * @return
     */
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
//        一次单击事件。onClick的替代品。返回值意义： 是否消费了点击事件（供系统使用）
        Log.e(TAG, "onSingleTapUp");
        return false;
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
            //大于最小值，小于最大值
            offsetX = Math.min(offsetX, (bitmap.getWidth() * bigScale - getWidth()) / 2);
            offsetX = Math.max(offsetX, -(bitmap.getWidth() * bigScale - getWidth()) / 2);

            //大于最小值，小于最大值
            offsetY = Math.min(offsetY, (bitmap.getHeight() * bigScale - getHeight()) / 2);
            offsetY = Math.max(offsetY, -(bitmap.getHeight() * bigScale - getHeight()) / 2);
            invalidate();
        }
        return true;
    }

    /**
     * 长按事件
     * 触发条件：用户长按（按下 500ms 不松手）后会被调用
     *
     * @param e
     */
    @Override
    public void onLongPress(MotionEvent e) {

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
            ViewCompat.postOnAnimation(ScalableImageView.this, this);

//            post(this);
        }
        return true;
    }

    @Override
    public void run() {
        if (overScroller.computeScrollOffset()) {
            offsetX = overScroller.getCurrX();
            offsetY = overScroller.getCurrY();
            invalidate();

            ViewCompat.postOnAnimation(ScalableImageView.this, this);
//            post(this);
        }
    }
}
