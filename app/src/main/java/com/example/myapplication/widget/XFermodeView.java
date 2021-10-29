package com.example.myapplication.widget;

import android.content.Context;
import android.graphics.Bitmap;
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

/**
 * author:wsl
 * 创建时间：2021/7/6 17:48
 * 描述：
 */

public class XFermodeView extends View {
    private Paint paint = new Paint();
    private RectF rectF = new RectF(0, 0, 400, 350);
    private Bitmap circleBitmap = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888);
    private Bitmap squareBitmap = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888);

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public XFermodeView(Context context) {
        super(context);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public XFermodeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public XFermodeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init() {
        Canvas canvas = new Canvas();
        canvas.setBitmap(circleBitmap);
        paint.setColor(Color.parseColor("#D81B60"));
        canvas.drawOval(100, 0, 300, 200, paint);

        canvas.setBitmap(squareBitmap);
        paint.setColor(Color.parseColor("#2196F3"));
        canvas.drawRect(0, 100, 200, 300, paint);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {//imsi 等价 imei
        int layer = canvas.saveLayer(rectF, null);

        canvas.drawBitmap(circleBitmap,100,50,paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));

        canvas.drawBitmap(squareBitmap,100,50,paint);

        paint.setXfermode(null);

        canvas.restoreToCount(layer);

    }
}
