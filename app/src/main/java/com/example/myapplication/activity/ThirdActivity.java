package com.example.myapplication.activity;

import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Property;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Group;
import androidx.constraintlayout.widget.Placeholder;
import androidx.transition.TransitionManager;

import com.example.myapplication.R;
import com.example.myapplication.widget.CircleView;
import com.example.myapplication.widget.MaterialEdittext;
import com.example.myapplication.widget.ProvinceView;

/**
 * author:wsl
 * 创建时间：2021/6/29 9:57
 * 描述：
 */

public class ThirdActivity extends AppCompatActivity {

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, ThirdActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third2);


//        CircleView image = findViewById(R.id.view);

//        ObjectAnimator animatorRadius = ObjectAnimator.ofFloat(image, "radius", 100, 400);
//        animatorRadius.setDuration(2000);
//        animatorRadius.start();
//
//        ObjectAnimator animator = ObjectAnimator.ofFloat(image, "mCenterY", 500, 3000);
//        animator.setDuration(2000);
//        animator.start();
//
//        ObjectAnimator animatorCenterY = ObjectAnimator.ofFloat(image, "mCenterY", 1000);
//        animatorCenterY.setDuration(1500);
//
//        AnimatorSet set = new AnimatorSet();
//        set.playSequentially(animatorRadius, animator,animatorCenterY);
//        set.start();

//        PropertyValuesHolder radiusHolder = PropertyValuesHolder.ofFloat("radius", 100, 400);
//        PropertyValuesHolder mCenterYHolder = PropertyValuesHolder.ofFloat("mCenterY", 300, 3000);
//        ObjectAnimator.ofPropertyValuesHolder(image,radiusHolder,mCenterYHolder)
//                .setDuration(2000)
//                .start();


//        模仿加速度，开始快、中间慢、最后快的效果
//        float length = 1800f;
//        第一个参数：进度；第二个参数：运动了多少距离
//        Keyframe keyframe1 = Keyframe.ofFloat(0f, 300);
//        Keyframe keyframe2 = Keyframe.ofFloat(0.2f, 0.5f * length);
//        Keyframe keyframe3 = Keyframe.ofFloat(0.8f, 0.6f * length);
//        Keyframe keyframe4 = Keyframe.ofFloat(1f, length);
//        PropertyValuesHolder valuesHolder = PropertyValuesHolder.ofKeyframe("mCenterY", keyframe1, keyframe2, keyframe3, keyframe4);
//        ObjectAnimator.ofPropertyValuesHolder(image, valuesHolder)
//                .setDuration(2000)
//                .start();

        //先加速再减速
//        ObjectAnimator.ofFloat().setInterpolator(new AccelerateDecelerateInterpolator());
//        加速
//        ObjectAnimator.ofFloat().setInterpolator(new AccelerateInterpolator());
//        匀速
//        ObjectAnimator.ofFloat().setInterpolator(new LinearInterpolator());


//        View view = findViewById(R.id.view);
//        ObjectAnimator.ofObject(view, "point", new PointFEvaluator(), new PointF(300, 400))
//                .setDuration(2000)
//                .start();
//
//        ObjectAnimator.ofObject(view, "province", new ProvinceView.ProvinceType(), "北京市", "广州")
//                .setDuration(2000)
//                .start();


//        ObjectAnimator.ofObject(view, "province", new ProvinceView.ProvinceType(), "北京", "雄安")
//                .setDuration(2000)
//                .start();

//        MaterialEdittext mEdittext = findViewById(R.id.mEdittext);
//
//
//
//        Button button = findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mEdittext.setUseFloatingLabel(false);
//            }
//        });
    }


    static class PointFEvaluator implements TypeEvaluator<PointF> {
        @Override
        public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
            float startX = startValue.x;
            float endX = endValue.x;
            float curX = startX + (endX - startX) * fraction;
            float startY = startValue.y;
            float endY = endValue.y;
            float curY = startY + (endY - startY) * fraction;

            return new PointF(curX, curY);
        }
    }
}
