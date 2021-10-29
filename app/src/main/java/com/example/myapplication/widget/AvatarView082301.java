package com.example.myapplication.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.myapplication.R;

/**
 * author:wsl
 * 创建时间：2021/8/23 11:25
 * 描述：
 */

public class AvatarView082301 extends View {
    public static final int IMAGE_WIDTH = 400;
    public static final int IMAGE_PADDING = 20;
    private RectF rectF = new RectF(IMAGE_PADDING, IMAGE_PADDING, IMAGE_PADDING + IMAGE_WIDTH, IMAGE_PADDING + IMAGE_WIDTH);
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap bitmap;
    private RectF rectF2 = new RectF(0, 0, 400, 350);
    Bitmap circleBitmap = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888);

    public AvatarView082301(Context context) {
        this(context, null);
    }

    public AvatarView082301(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AvatarView082301(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        bitmap = getBitmap(IMAGE_WIDTH);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Canvas canvas = new Canvas();
        canvas.setBitmap(circleBitmap);
        paint.setColor(Color.parseColor("#D81B60"));
        canvas.drawRect(100, 0, 300, 200, paint);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(circleBitmap, 0, 0, paint);

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
