package com.example.myapplication.model;

import com.example.myapplication.bean.Account;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * author:wsl
 * 创建时间：2021/10/8 14:53
 * 描述：
 */

//第一步 添加@Module 注解
@Module
public class MainModule {
    //第二步 使用Provider 注解 实例化对象
    @Singleton
    @Named("第一个")
    @Provides
    Account provideA() {
        return new Account("第一个");
    }

    @Singleton
    @Named("第二个")
    @Provides
    Account provideA2() {
        return new Account("第二个");
    }

}
