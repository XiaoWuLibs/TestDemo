package com.example.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityMvvmLayoutBinding;
import com.example.myapplication.view_model.MVVMViewModel;

/**
 * author:wsl
 * 创建时间：2021/9/29 9:24
 * 描述：
 */

public class MVVMActivity extends AppCompatActivity {
    private ActivityMvvmLayoutBinding binding;
    private MVVMViewModel mvvmViewModel;

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, MVVMActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mvvm_layout);

        mvvmViewModel = new MVVMViewModel(getApplication(), binding);
        binding.setViewModel(mvvmViewModel);
    }
}
