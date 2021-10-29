package com.example.myapplication.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * author:wsl
 * 创建时间：2021/5/19 18:06
 * 描述：图片工具类
 */

public class ImageUtils {
    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {
            baos.reset();
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            options -= 10;

        }

        if (!image.isRecycled()) {
            image.recycle();
            image = null;
        }

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        Bitmap bitmap = BitmapFactory.decodeStream(bais, null, null);
        return bitmap;
    }

    public static Bitmap getBitmap(String srcPath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(srcPath, options);

        int outWidth = options.outWidth;
        int outHeight = options.outHeight;

        float mW = 480f;
        float mH = 800f;

        int be = 1;

        if (outWidth > outHeight && outWidth > mW) {
            be = (int) (outWidth / mW);
        } else if (outHeight > outWidth && outHeight > mH) {
            be = (int) (outHeight / mH);
        }

        if (be < 0) {
            be = 1;
        }

        options.inSampleSize = be;
        return BitmapFactory.decodeFile(srcPath, options);
    }
}
