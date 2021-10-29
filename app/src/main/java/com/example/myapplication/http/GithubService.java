package com.example.myapplication.http;

import com.example.myapplication.bean.Repo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * author:wsl
 * 创建时间：2021/6/22 10:26
 * 描述：
 */

public interface GithubService {
    @GET("users/{user}/repos")
    Call<List<Repo>> listRepos(@Path("user") String user);
}
