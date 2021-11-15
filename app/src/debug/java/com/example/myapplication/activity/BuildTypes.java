package com.example.myapplication.activity;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

/**
 * author:wsl
 * 创建时间：2021/11/15 11:12
 * 描述：
 */

public class BuildTypes {
    public static void drawBadge(Activity activity){
        View view = new View(activity);
        view.setBackgroundColor(Color.YELLOW);
        TextView textView  = new TextView(activity);
        textView.setText("debug");
        textView.setTextColor(Color.YELLOW);
        textView.setTextSize(50);
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        decorView.addView(textView);
    }
}
