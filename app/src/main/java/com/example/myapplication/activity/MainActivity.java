package com.example.myapplication.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.myapplication.R;
import com.example.myapplication.bean.Second2MainWrap;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.FileNotFoundException;
import java.util.HashMap;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;

public class MainActivity extends AppCompatActivity {
    public String[] per = new String[]{
            Manifest.permission.SYSTEM_ALERT_WINDOW
    };

    private boolean cycleTag = true;
    private int count;
    private String str = null;
    private Object object = new Object();

    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);

        boolean isAllGranted = checkPermissionAllGranted(per);
        if (!isAllGranted) {
            ActivityCompat.requestPermissions(this, per,
                    0);
        }
        btnSubmit = findViewById(R.id.btnSubmit);


        HashMap<String, String> map = new HashMap<>();
        map.put("", "");

        Single.just(1).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Throwable {
                return integer.toString();
            }
        }).subscribe(new SingleObserver<String>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull String s) {
                btnSubmit.setText(s);
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

            }
        });


        btnSubmit.setOnClickListener(v -> {
//            EventBus.getDefault().postSticky(new MessageWrap("这是页面跳转传递的数据"));
//            SecondActivity.startActivity(MainActivity.this);
//            MVVMActivity.startActivity(MainActivity.this);
//            ObservableActivity.startActivity(MainActivity.this);
//            LiveDataActivity.startActivity(MainActivity.this);
//            DaggerActivity.startActivity(MainActivity.this);
            com.study.testdemosubmodule.MainActivity.startActivity(MainActivity.this);
        });
    }


    public Drawable LayoutToDrawable(int layout_id) {

        LayoutInflater inflator = getLayoutInflater();
        View viewHelp = inflator.inflate(/*R.layout.test */ layout_id, null);

        TextView textView = (TextView) viewHelp.findViewById(R.id.textTitle);
        int size = (int) textView.getText().length();
        Bitmap snapshot = convertViewToBitmap(viewHelp, size);
        Drawable drawable = (Drawable) new BitmapDrawable(snapshot);

        return drawable;

    }

    public static Bitmap convertViewToBitmap(View view, int size) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        int width = size * 40;
        view.layout(0, 0, width, view.getMeasuredHeight());  //根据字符串的长度显示view的宽度
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    private void loadBitmap(String picturePath) throws FileNotFoundException {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getData(Second2MainWrap wrap) {
        btnSubmit.setText(wrap.content);
    }


    /**
     * 检查是否有指定的权限
     *
     * @param permissions 权限列表
     * @return 是否有权限
     */
    private boolean checkPermissionAllGranted(String[] permissions) {
        for (String permission : permissions) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (getApplicationContext().checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            boolean isAllGranted = true;
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }

            if (isAllGranted) {
                Toast.makeText(this, "已授权 ", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "未授权 ", Toast.LENGTH_SHORT).show();
//                finish();
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}