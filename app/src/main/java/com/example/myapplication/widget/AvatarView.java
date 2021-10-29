package com.example.myapplication.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
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
import com.example.myapplication.utils.FontUtils;

/**
 * author:wsl
 * 创建时间：2021/7/1 20:22
 * 描述：绘制圆形图片
 */

public class AvatarView extends View {
    public static final int IMAGE_WIDTH = 200;
    public static final int IMAGE_PADDING = 20;
    private RectF rectF = new RectF(IMAGE_PADDING, IMAGE_PADDING, IMAGE_PADDING + IMAGE_WIDTH, IMAGE_PADDING + IMAGE_WIDTH);
    private Paint paint;

    public AvatarView(Context context) {
        super(context);
        init();
    }

    public AvatarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AvatarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        int layer = canvas.saveLayer(rectF, null);
        canvas.drawOval(IMAGE_PADDING, IMAGE_PADDING, IMAGE_PADDING + IMAGE_WIDTH, IMAGE_PADDING + IMAGE_WIDTH, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(getAvatar((int) FontUtils.dp2px(IMAGE_WIDTH)), IMAGE_PADDING, IMAGE_PADDING, paint);
        paint.setXfermode(null);
        canvas.restoreToCount(layer);
    }

    private Bitmap getAvatar(int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.icon_android2, options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = width;
        return BitmapFactory.decodeResource(getResources(), R.drawable.icon_android2, options);
    }
}
