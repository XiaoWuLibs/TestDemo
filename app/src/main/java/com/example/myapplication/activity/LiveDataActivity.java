package com.example.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityLivedataBinding;
import com.example.myapplication.lifecycle.MyObserver;
import com.example.myapplication.view_model.UserInfoViewModel;

/**
 * author:wsl
 * 创建时间：2021/9/29 14:52
 * 描述：
 */

public class LiveDataActivity extends AppCompatActivity {

    private UserInfoViewModel viewModel;

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, LiveDataActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityLivedataBinding dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_livedata);
        viewModel = new ViewModelProvider.NewInstanceFactory().create(UserInfoViewModel.class);
        Observer<String> userObserver = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                dataBinding.tv2.setText(s);
            }
        };
        viewModel.getName().observe(this, userObserver);

        dataBinding.btnSubmit.setOnClickListener(v -> {
            viewModel.getName().setValue(dataBinding.edit2.getText().toString());
        });

        getLifecycle().addObserver(new MyObserver());
    }
}
