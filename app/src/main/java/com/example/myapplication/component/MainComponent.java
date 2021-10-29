package com.example.myapplication.component;

import com.example.myapplication.activity.DaggerActivity;
import com.example.myapplication.model.MainModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * author:wsl
 * 创建时间：2021/10/8 14:57
 * 描述：
 */
//第一步 添加@Component
//第二步 添加module
@Singleton
@Component(modules = {MainModule.class})
public interface MainComponent {
    //第三步  写一个方法 绑定Activity /Fragment
    void inject(DaggerActivity daggerActivity);
}
