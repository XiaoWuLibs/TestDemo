package com.example.myapplication.view_model;

import android.app.Application;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

import com.example.myapplication.BR;
import com.example.myapplication.bean.Account;
import com.example.myapplication.bean.User;
import com.example.myapplication.callBack.MVVMCallBack;
import com.example.myapplication.databinding.ActivityMvvmLayoutBinding;
import com.example.myapplication.model.MVVMModel;

/**
 * author:wsl
 * 创建时间：2021/9/29 9:45
 * 描述：
 */

public class MVVMViewModel extends BaseObservable {
    private Application application;
    private ActivityMvvmLayoutBinding binding;
    private MVVMModel mvvmModel;
    private String input;
    private String result;

    public MVVMViewModel(Application application, ActivityMvvmLayoutBinding binding) {
        this.application = application;
        this.binding = binding;
        mvvmModel = MVVMModel.newMVVMModel();
    }

    @Bindable
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
        notifyPropertyChanged(BR.result);
    }

    public void getData() {
        String input = binding.etAccount.getText().toString();
        mvvmModel.getAccountData(input, new MVVMCallBack() {
            @Override
            public void onSuccess(Account account) {
                String info = account.name + "|" + account.level;
                setResult(info);
            }

            @Override
            public void onFailed() {
                setResult("消息获取失败");
            }
        });
    }


    @BindingAdapter({"imageDrawable", "error"})
    public static void loadImage(ImageView view, Drawable image, Drawable error) {
        view.setImageDrawable(error);
    }
}
