package com.example.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;

import com.example.myapplication.R;
import com.example.myapplication.bean.InfoBean;
import com.example.myapplication.bean.MessageWrap;
import com.example.myapplication.bean.Repo;
import com.example.myapplication.bean.Second2MainWrap;
import com.example.myapplication.databinding.ActivitySecondBinding;
import com.example.myapplication.http.GithubService;
import com.example.myapplication.model.NameViewModel;
import com.google.android.material.button.MaterialButton;
import com.zkk.view.rulerview.RulerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * author:wsl
 * 创建时间：2021/6/8 15:26
 * 描述：
 */

public class SecondActivity extends AppCompatActivity {
    private ActivitySecondBinding dataBinding;
    private InfoBean infoBean;
    private NameViewModel viewModel;
    private TextView tvContent;
    private ViewStub viewStub;
    private View emptyView;
    private TextView tvMsg;
    private MaterialButton btnShowHide;
    private Button btnBack;
    private View inflate;
    private RulerView ruler_height;

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, SecondActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_second);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_second);
        infoBean = new InfoBean();
        infoBean.setVisiable(false);
        dataBinding.setInfoBean(infoBean);
        tvContent = findViewById(R.id.tvContent);
        btnShowHide = findViewById(R.id.btnShowHide);
        viewStub = findViewById(R.id.viewStub);
        btnBack = findViewById(R.id.btnBack);
        EventBus.getDefault().register(this);
        Thread thread2 = new Thread() {
            @Override
            public void run() {
                super.run();
                printStr();
            }
        };

        thread2.start();

        tvContent.setOnClickListener(v -> {
            EventBus.getDefault().post(new Second2MainWrap("我是返回来的"));
            finish();
        });

        btnShowHide.setOnClickListener(v -> {
            if (emptyView == null || emptyView.getVisibility() != View.VISIBLE) {
                showViewStub();
            } else {
                hideViewStub();
            }
        });

        btnBack.setOnClickListener(v -> {
            moveTaskToBack(true);


            WindowManager windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
            WindowManager.LayoutParams params = new WindowManager.LayoutParams();
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.gravity = Gravity.LEFT | Gravity.TOP;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            } else {
                params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
            }
            params.x = 70;
            params.y = 210;

            inflate = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_test_demo, null);
            inflate.setOnClickListener(v1 -> {
                windowManager.removeView(inflate);

                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), SecondActivity.class);
                startActivity(intent);
            });

            windowManager.addView(inflate, params);

        });
        System.out.println("-------------------onCreate");

        ruler_height = (RulerView) findViewById(R.id.ruler_height);
//        ruler_height.setOnValueChangeListener(new RulerView.OnValueChangeListener() {
//            @Override
//            public void onValueChange(float value) {
//                tv_register_info_weight_value.setText(value + "");
//            }
//        });

/**
 *
 * @param selectorValue 未选择时 默认的值 滑动后表示当前中间指针正在指着的值
 * @param minValue   最大数值
 * @param maxValue   最小的数值
 * @param per   最小单位  如 1:表示 每2条刻度差为1.   0.1:表示 每2条刻度差为0.1 在demo中 身高mPerValue为1  体重mPerValue 为0.1
 */
        ruler_height.setValue(165, 80, 250, 1);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GithubService service = retrofit.create(GithubService.class);

        Call<List<Repo>> repos = service.listRepos("octocat");

        repos.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                if (response.body() != null) {
                    Log.e("onResponse", (response.body().get(0).name));
                }
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {

            }
        });

        dataBinding.btnSubmit.setOnClickListener(v -> {
            if (infoBean.isVisiable()) {
                infoBean.setVisiable(false);
            } else {
                infoBean.setVisiable(true);
            }
        });


//        viewModel = new ViewModelProvider(this).get(NameViewModel.class);


        dataBinding.btnGoToThird.setOnClickListener(view -> {
            ThirdActivity.startActivity(SecondActivity.this);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("-------------------onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("-------------------onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("-------------------onStop");
    }


    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("-------------------onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("-------------------onRestart");
    }

    private void printStr() {
        System.out.println("-----------------");
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getIntentData(MessageWrap msg) {
        tvContent.setText(msg.msg);
    }


    private void showViewStub() {
        if (emptyView == null) {
            emptyView = viewStub.inflate();
            tvMsg = findViewById(R.id.tvMsg);
            tvMsg.setText("哈哈，网络被攻击了");
        }
        emptyView.setVisibility(View.VISIBLE);
    }

    private void hideViewStub() {
        if (emptyView == null) {
            emptyView = viewStub.inflate();
        }
        emptyView.setVisibility(View.GONE);
    }

    @BindingAdapter("paddingLeft")
    public static void setPaddingLeft(View view, int oldPadding, int newPadding) {
        view.setPadding(newPadding,
                view.getPaddingTop(),
                view.getPaddingRight(),
                view.getPaddingBottom());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        System.out.println("-------------------onDestroy");
    }
}
