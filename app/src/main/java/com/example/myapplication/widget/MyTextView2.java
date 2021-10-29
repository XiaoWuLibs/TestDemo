package com.example.myapplication.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * author:wsl
 * 创建时间：2021/5/17 20:40
 * 描述：
 */

public class MyTextView2 extends View {
    //Tag填充画笔
    private Paint tagPaint;
    //内容填充画笔
    private Paint contentPaint;
    //用户名填充画笔
    private Paint titlePaint;
    //标准的字体颜色
    private String contentNormalColor = "#737373";
    //有焦点的字体颜色
    private String contentFocuedColor = "#333333";
    //控件宽度
    private int viewWidth = 0;
    //控件高度
    private int viewHeight = 0;
    //标准的字的样式
    public static final int TEXT_TYPE_NORMAL = 1;
    //控件获取焦点的时候进行的处理
    public static final int TEXT_TYPE_FOCUED = 2;
    //默认是标准的样式
    private int currentTextType = TEXT_TYPE_NORMAL;
    //默认当前的颜色
    private String textColor = "#444444";
    //标题的颜色
    private String titleTextColor = "#00ff00";
    //字体大小
    private int textSize = 60;
    //等级标记tag
    private String mTag = "V1";
    //标题
    private String mTitleText = "测试的标题信息";
    //内容
    private String mText = "测试的文字信息";
    //最小view高度
    private float minHeight = 0;
    //最小view宽度
    private float minWidth = 0;
    //行间距
    private float lineSpace;
    //最大行数
    private int maxLines = 0;
    //文字测量工具
    private Paint.FontMetricsInt textFm;
    ///真实的行数
    private int realLines;
    //当前显示的行数
    private int line;
    //在末尾是否显示省略号
    private boolean showEllipsise;

    //文字显示区的宽度
    private int textWidth;
    //单行文字的高度
    private int signleLineHeight;
    private int mPaddingLeft, mPaddingRight, mPaddingTop, mPaddingBottom;
    float startL = 0.0f;
    float startT = 0.0f;
    /**
     * 省略号
     **/
    private String ellipsis = "...";

    public MyTextView2(Context context) {
        this(context, null);
    }

    public MyTextView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTextView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        initAttr(context,attrs);
        init();
    }

    private boolean isFristload;

    /**
     * 初始化
     */
    private void init() {
        contentPaint = new Paint();
        contentPaint.setTextSize(textSize);
        contentPaint.setAntiAlias(true);
        contentPaint.setStrokeWidth(2);
        contentPaint.setColor(Color.parseColor(textColor));
        contentPaint.setTextAlign(Paint.Align.LEFT);
        textFm = contentPaint.getFontMetricsInt();
        signleLineHeight = Math.abs(textFm.top - textFm.bottom);

        titlePaint = new Paint();
        titlePaint.setTextSize(textSize);
        titlePaint.setAntiAlias(true);
        titlePaint.setStrokeWidth(2);
        titlePaint.setColor(Color.parseColor(titleTextColor));
        titlePaint.setTextAlign(Paint.Align.LEFT);

        tagPaint = new Paint();
        tagPaint.setTextSize(textSize);
        tagPaint.setAntiAlias(true);
        tagPaint.setStrokeWidth(2);
        tagPaint.setColor(Color.parseColor(titleTextColor));
        tagPaint.setTextAlign(Paint.Align.LEFT);
    }

    /**
     * 初始化属性
     *
     * @param context
     * @param attrs
     */
//    private void initAttr(Context context,AttributeSet attrs) {
//        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyTextView);
//        mPaddingLeft = typedArray.getDimensionPixelSize(R.styleable.MyTextView_paddingLeft, 0);
//        mPaddingRight = typedArray.getDimensionPixelSize(R.styleable.MyTextView_paddingRight, 0);
//        mPaddingTop = typedArray.getDimensionPixelSize(R.styleable.MyTextView_paddingTop, 0);
//        mPaddingBottom = typedArray.getDimensionPixelSize(R.styleable.MyTextView_paddingBottom, 0);
//
//        mText = typedArray.getString(R.styleable.MyTextView_text);
//        textColor = typedArray.getString(R.styleable.MyTextView_textColor);
//        if(textColor==null){
//            textColor="#444444";
//        }
//        //   textSize = typedArray.getDimensionPixelSize(R.styleable.MyTextView_textSize, 60);
//
//        textSize = typedArray.getDimensionPixelSize(R.styleable.MyTextView_textSize, 15);
////        lineSpace = typedArray.getInteger(R.styleable.MyTextView_lineSpacing, 20);
//        typedArray.recycle();
//    }
    public void setText(CharSequence text) {
        this.mText = text.toString();
        invalidate();
    }

    public void setText(CharSequence tag, CharSequence title, CharSequence content) {
        this.mTag = tag.toString();
        this.mTitleText = title.toString();
        this.mText = content.toString();
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = getMeasuredWidth();
        textWidth = viewWidth - mPaddingLeft - mPaddingRight;
        viewHeight = (int) getViewHeight();
        setMeasuredDimension(viewWidth, viewHeight);
    }

    private float getViewHeight() {
        String totalStr = mTag + mTitleText + mText;
        char[] textChars = totalStr.toCharArray();
        float ww = 0.0f;
        int count = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < textChars.length; i++) {
            float v = contentPaint.measureText(textChars[i] + "");
            if (ww + v <= textWidth) {
                sb.append(textChars[i]);
                ww += v;
            } else {
                count++;
                sb = new StringBuilder();
                ww = 0.0f;
                ww += v;
                sb.append(textChars[i]);
            }
        }
        if (sb.toString().length() != 0) {
            count++;
        }
        return count * signleLineHeight + lineSpace * (count - 1) + mPaddingBottom + mPaddingTop;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(new Rect(mPaddingLeft, 0, 100, 60), contentPaint);
        drawTagText(canvas);
        drawTitleText(canvas);
        drawText(canvas);
    }

    private void drawTagText(Canvas canvas) {
        int cyc = 0;
        char[] tagChars = mTag.toCharArray();
        float ww = 0.0f;
//        float startL = 0.0f;
//        float startT = 0.0f;
        startL += mPaddingLeft;
        startT += mPaddingTop + signleLineHeight / 2 + (textFm.bottom - textFm.top) / 2 - textFm.bottom;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < tagChars.length; i++) {
            float v = tagPaint.measureText(tagChars[i] + "");
            if (ww + v <= textWidth) {
                sb.append(tagChars[i]);
                ww += v;
                Log.e("TAG", "if");
            } else {
                canvas.drawText(sb.toString(), cyc == 0 ? startL + 120 : startL, startT, tagPaint);
                startT += signleLineHeight + lineSpace;
                sb = new StringBuilder();
                ww = 0.0f;
                ww += v;
                sb.append(tagChars[i]);
                Log.e("TAG", "else");
                cyc++;
            }
        }

        if (sb.toString().length() > 0) {
            canvas.drawText(sb.toString(), cyc == 0 ? startL + 120 : startL, startT, tagPaint);
            Log.e("TAG", "finally");
        }

    }

    private void drawTitleText(Canvas canvas) {
        int cyc = 0;
        char[] titleChars = mTitleText.toCharArray();
        float ww = 0.0f;
//        float startL = 0.0f;
//        float startT = 0.0f;
        startL += mPaddingLeft;
        startT += mPaddingTop + signleLineHeight / 2 + (textFm.bottom - textFm.top) / 2 - textFm.bottom;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < titleChars.length; i++) {
            float v = titlePaint.measureText(titleChars[i] + "");
            if (ww + v <= textWidth) {
                sb.append(titleChars[i]);
                ww += v;
                Log.e("TAG", "if");
            } else {
                canvas.drawText(sb.toString(), cyc == 0 ? startL + 120 : startL, startT, titlePaint);
                startT += signleLineHeight + lineSpace;
                sb = new StringBuilder();
                ww = 0.0f;
                ww += v;
                sb.append(titleChars[i]);
                Log.e("TAG", "else");
                cyc++;
            }
        }

        if (sb.toString().length() > 0) {
            canvas.drawText(sb.toString(), cyc == 0 ? startL + 120 : startL, startT, titlePaint);
            Log.e("TAG", "finally");
        }

    }

    /**
     * 循环遍历画文字
     *
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        int cyc = 0;
        char[] textChars = mText.toCharArray();
        float ww = 0.0f;
//        float startL = 0.0f;
//        float startT = 0.0f;
        startL += mPaddingLeft;
        startT += mPaddingTop + signleLineHeight / 2 + (textFm.bottom - textFm.top) / 2 - textFm.bottom;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < textChars.length; i++) {
            float v = contentPaint.measureText(textChars[i] + "");
            if (ww + v <= textWidth) {
                sb.append(textChars[i]);
                ww += v;
                Log.e("TAG", "if");
            } else {
                canvas.drawText(sb.toString(), cyc == 0 ? startL + 120 : startL, startT, contentPaint);
                startT += signleLineHeight + lineSpace;
                sb = new StringBuilder();
                ww = 0.0f;
                ww += v;
                sb.append(textChars[i]);
                Log.e("TAG", "else");
                cyc++;
            }
        }

        if (sb.toString().length() > 0) {
            canvas.drawText(sb.toString(), cyc == 0 ? startL + 120 : startL, startT, contentPaint);
            Log.e("TAG", "finally");
        }

    }

}