package com.example.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.example.myapplication.R;
import com.example.myapplication.bean.User;
import com.example.myapplication.databinding.ActivityObservableBinding;

/**
 * author:wsl
 * 创建时间：2021/9/29 14:52
 * 描述：
 */

public class ObservableActivity extends AppCompatActivity {
    private User user;

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, ObservableActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = new User();
        ActivityObservableBinding dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_observable);
        dataBinding.setUserInfo(user);

        dataBinding.btnSubmit.setOnClickListener(v -> {
            user.setName(dataBinding.edit2.getText().toString());
        });
    }
}
