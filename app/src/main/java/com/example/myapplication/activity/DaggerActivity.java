package com.example.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.bean.Account;
import com.example.myapplication.component.DaggerMainComponent;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * author:wsl
 * 创建时间：2021/10/8 14:50
 * 描述：
 */

public class DaggerActivity extends AppCompatActivity {
    @Named("第一个")
    @Inject
    Account account;

    @Named("第一个")
    @Inject
    Account account2;

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, DaggerActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger);

        DaggerMainComponent.builder().build().inject(this);

        account.eat();
        Log.e("DaggerActivity", account.toString());
        Log.e("DaggerActivity", account2.toString());

    }
}
