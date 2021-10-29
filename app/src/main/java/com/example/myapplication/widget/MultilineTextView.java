package com.example.myapplication.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.myapplication.R;

/**
 * author:wsl
 * 创建时间：2021/7/7 17:40
 * 描述：
 */

public class MultilineTextView extends View {
    private static final String text = "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32.The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from \"de Finibus Bonorum et Malorum\" by Cicero are also reproduced in their exact original form, accompanied by English versions from the 1914 translation by H. Rackham.";
    private TextPaint paint = new TextPaint();
    private Bitmap bitmap = getAvatar(200);

    public MultilineTextView(Context context) {
        super(context);
    }

    public MultilineTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MultilineTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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

    @Override
    protected void onDraw(Canvas canvas) {

        paint.setAntiAlias(true);
        paint.setTextSize(50);

//        StaticLayout staticLayout = new StaticLayout(text, paint, getWidth(), Layout.Alignment.ALIGN_NORMAL, 1, 0, false);
//        staticLayout.draw(canvas);

        //绘制图片
        canvas.drawBitmap(bitmap, getWidth() - 200, 70, paint);
        Paint.FontMetrics fontMetrics = new Paint.FontMetrics();
        paint.getFontMetrics(fontMetrics);

        float[] measuredWidth = new float[0];


//        文字的开始位置
        int start = 0;
//        当前行 显示多少个字符
        int count = 0;
//        每行的间距
        float verticalOffset = fontMetrics.descent - fontMetrics.top;
//        每行的宽 。有时候是整个屏幕的宽；如果有图片需要绕过图片，宽度就会变小（屏幕宽度 - 图片宽度）
        int width;

        //循环截取文字,然后进行绘制
        while (start < text.length()) {
//            如果文字的bottom在图片上沿之上 或者 文字的top在图片下沿之下，则width = getWidth，否则width = getWidth-imgWidth
            if (verticalOffset < 70 || (verticalOffset - paint.getFontSpacing()) > (70 + 200)) {
                width = getWidth();
            } else {
                width = getWidth() - 200;
            }
            //计算每行显示的字符数量
            count = paint.breakText(text, start, text.length(), true, width, measuredWidth);
//            绘制每行的文字
            canvas.drawText(text, start, start + count
                    , 0, verticalOffset, paint);

//            重新赋值每行的Y值.  paint.getFontSpacing:第一行文字下边沿和第二行下边沿之间的距离
            verticalOffset += paint.getFontSpacing();
//            重新赋值文字的开始位置
            start += count;
        }
//        canvas.drawText(text, 0, 0 + (fontMetrics.descent - fontMetrics.top), paint);

    }
}
