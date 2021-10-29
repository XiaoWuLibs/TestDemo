package com.example.myapplication.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.myapplication.R;

/**
 * author:wsl
 * 创建时间：2021/7/8 16:06
 * 描述：
 */

public class CameraView extends View {
    public static final int BITMAP_SIZE = 400;
    public static final int BITMAP_PADDING = 100;
    private Bitmap bitmap = getAvatar(BITMAP_SIZE);
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path path = new Path();
    private Camera camera = new Camera();

    public CameraView(Context context) {
        super(context);
        init();
    }

    public CameraView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CameraView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        camera.rotateX(30);
        camera.setLocation(0, 0, -6 * getResources().getDisplayMetrics().density);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.clipRect(BITMAP_PADDING, BITMAP_PADDING, BITMAP_PADDING + BITMAP_SIZE / 2, BITMAP_PADDING + BITMAP_SIZE / 2);
//        canvas.drawBitmap(getAvatar(BITMAP_SIZE), BITMAP_PADDING, BITMAP_PADDING, paint);

//        path.addOval(BITMAP_PADDING, BITMAP_PADDING, BITMAP_PADDING + BITMAP_SIZE, BITMAP_PADDING + BITMAP_SIZE, Path.Direction.CCW);
//        canvas.clipPath(path);
//        canvas.drawBitmap(bitmap, BITMAP_PADDING, BITMAP_PADDING, paint);
//
//        canvas.clipOutRect(BITMAP_PADDING, BITMAP_PADDING
//                , (BITMAP_PADDING + BITMAP_SIZE) / 2, (BITMAP_PADDING + BITMAP_SIZE) / 2);

//        canvas.clipPath(path);

//        camera.applyToCanvas(canvas);
//        canvas.drawBitmap(bitmap, BITMAP_PADDING, BITMAP_PADDING, paint);
//
//        canvas.translate(BITMAP_PADDING + BITMAP_SIZE / 2, BITMAP_PADDING + BITMAP_SIZE / 2);
//        camera.applyToCanvas(canvas);
//        canvas.translate(-(BITMAP_PADDING + BITMAP_SIZE / 2), -(BITMAP_PADDING + BITMAP_SIZE / 2));
//        canvas.drawBitmap(bitmap, BITMAP_PADDING, BITMAP_PADDING, paint);

//        canvas.save();
//        canvas.translate(BITMAP_PADDING+BITMAP_SIZE/2,BITMAP_PADDING+BITMAP_SIZE/2);
//        canvas.clipRect(-BITMAP_SIZE/2,-BITMAP_SIZE/2,BITMAP_SIZE/2,0);
//        canvas.translate(-(BITMAP_PADDING+BITMAP_SIZE/2),-(BITMAP_PADDING+BITMAP_SIZE/2));
//        canvas.drawBitmap(bitmap,BITMAP_PADDING,BITMAP_PADDING,paint);
//        canvas.restore();
//
//        canvas.save();
//        canvas.translate(BITMAP_PADDING+BITMAP_SIZE/2,BITMAP_PADDING+BITMAP_SIZE/2);
//        camera.applyToCanvas(canvas);
//        canvas.clipRect(-BITMAP_SIZE/2,0,BITMAP_SIZE/2,BITMAP_SIZE/2);
//        canvas.translate(-(BITMAP_PADDING+BITMAP_SIZE/2),-(BITMAP_PADDING+BITMAP_SIZE/2));
//        canvas.drawBitmap(bitmap,BITMAP_PADDING,BITMAP_PADDING,paint);
//        canvas.restore();

        canvas.save();
        canvas.translate(BITMAP_PADDING + BITMAP_SIZE / 2, BITMAP_PADDING + BITMAP_SIZE / 2);
        canvas.rotate(-30);
        canvas.clipRect(-BITMAP_SIZE, -BITMAP_SIZE, BITMAP_SIZE, 0);
        canvas.rotate(30);
        canvas.translate(-(BITMAP_PADDING + BITMAP_SIZE / 2), -(BITMAP_PADDING + BITMAP_SIZE / 2));
        canvas.drawBitmap(bitmap, BITMAP_PADDING, BITMAP_PADDING, paint);
        canvas.restore();

        canvas.save();
        canvas.translate(BITMAP_PADDING + BITMAP_SIZE / 2, BITMAP_PADDING + BITMAP_SIZE / 2);
        canvas.rotate(-30);
        camera.applyToCanvas(canvas);
        canvas.clipRect(-BITMAP_SIZE, 0, BITMAP_SIZE, BITMAP_SIZE);
        canvas.rotate(30);
        canvas.translate(-(BITMAP_PADDING + BITMAP_SIZE / 2), -(BITMAP_PADDING + BITMAP_SIZE / 2));
        canvas.drawBitmap(bitmap, BITMAP_PADDING, BITMAP_PADDING, paint);
        canvas.restore();
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
}
