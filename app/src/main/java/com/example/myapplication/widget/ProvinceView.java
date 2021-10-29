package com.example.myapplication.widget;

import android.animation.TypeEvaluator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * author:wsl
 * 创建时间：2021/7/15 7:27
 * 描述：
 */

public class ProvinceView extends View {
    private static final List<String> provinces = Arrays.asList(new String[]{"北京", "上海", "广州", "深圳", "河北", "天津", "雄安"});
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private String province = "北京市";

    public ProvinceView(Context context) {
        super(context);
        init();
    }

    public ProvinceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProvinceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint.setTextSize(100);
        paint.setTextAlign(Paint.Align.CENTER);
    }

    public void setProvince(String province) {
        this.province = province;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(province, getWidth() / 2, getHeight() / 2, paint);
    }

    public static class ProvinceType implements TypeEvaluator<String> {
        @Override
        public String evaluate(float fraction, String startValue, String endValue) {
            int indexStart = provinces.indexOf(startValue);
            int indexEnd = provinces.indexOf(endValue);
            int curIndex = (int) (indexStart + (indexEnd - indexStart) * fraction);
            return provinces.get(curIndex);
        }
    }
}
