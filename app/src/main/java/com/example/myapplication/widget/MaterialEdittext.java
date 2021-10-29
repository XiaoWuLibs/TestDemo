package com.example.myapplication.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

import com.example.myapplication.R;

/**
 * author:wsl
 * 创建时间：2021/7/16 10:29
 * 描述：
 */

public class MaterialEdittext extends AppCompatEditText {
    private int TEXT_SIZE = 40;
    private int TEXT_MARGIN = 35;
    private static final int HORIZONTAL_OFFSET = 15;
    private static final int VERTICAL_OFFSET = 70;
    private static final int EXTRA_VERTICAL_OFFSET = 90;
    private boolean floatingLabelShowing = false;
    private float floatingLabelFraction = 0f;
    private boolean useFloatingLabel = true;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private ObjectAnimator alphaAnimator;


    public MaterialEdittext(Context context) {
        super(context);
        init(context, null);
    }

    public MaterialEdittext(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MaterialEdittext(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * int[] styleable MaterialEdittext { 0x7f030357 }
     * int styleable MaterialEdittext_useFloatingLabel 0
     * int attr useFloatingLabel 0x7f030357
     */

    private void init(Context context, AttributeSet attrs) {
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            System.out.println("Attrs:" + attrs.getAttributeName(i) + ":" + attrs.getAttributeValue(i));
        }
//        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MaterialEdittext);
//        useFloatingLabel = typedArray.getBoolean(R.styleable.MaterialEdittext_useFloatingLabel, true);
//        typedArray.recycle();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, new int[]{R.attr.useFloatingLabel,R.attr.subtitle});
        useFloatingLabel = typedArray.getBoolean(0, true);
        String subtitle = typedArray.getString(1);
        typedArray.recycle();


        paint.setTextSize(TEXT_SIZE);
        if (useFloatingLabel) {
            setPadding(getPaddingLeft(), getPaddingTop() + TEXT_SIZE + TEXT_MARGIN, getPaddingRight(), getPaddingBottom());
            alphaAnimator = ObjectAnimator.ofFloat(this, "floatingLabelFraction", 1f, 0f)
            ;
        }
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);

        if (useFloatingLabel) {
            if (TextUtils.isEmpty(text.toString()) && floatingLabelShowing) {
//消失
                floatingLabelShowing = false;
                alphaAnimator.start();

            } else if (!TextUtils.isEmpty(text.toString()) && !floatingLabelShowing) {
//            显示
                floatingLabelShowing = true;
                alphaAnimator.reverse();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (useFloatingLabel) {
            paint.setAlpha((int) (0xff * floatingLabelFraction));
            int curVerticalOffset = (int) (VERTICAL_OFFSET + EXTRA_VERTICAL_OFFSET * (1 - floatingLabelFraction));
            canvas.drawText(getHint().toString(), HORIZONTAL_OFFSET, curVerticalOffset, paint);
        }
    }

    public float getFloatingLabelFraction() {
        return floatingLabelFraction;
    }

    public void setFloatingLabelFraction(float floatingLabelFraction) {
        this.floatingLabelFraction = floatingLabelFraction;
        invalidate();
    }

    public boolean isUseFloatingLabel() {
        return useFloatingLabel;
    }

    public void setUseFloatingLabel(boolean useFloatingLabel) {
        if (this.useFloatingLabel != useFloatingLabel) {
            this.useFloatingLabel = useFloatingLabel;
            if (this.useFloatingLabel) {
                setPadding(getPaddingLeft(), getPaddingTop() + TEXT_SIZE + TEXT_MARGIN, getPaddingRight(), getPaddingBottom());
            } else {
                setPadding(getPaddingLeft(), getPaddingTop() - TEXT_SIZE - TEXT_MARGIN, getPaddingRight(), getPaddingBottom());
            }
        }
    }
}
