package com.zww.hichat.view.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.zww.hichat.R;
import com.zww.hichat.utils.CheckPermissionUtils;

/**
 * Created by 风流倜傥,英俊潇洒,走位风骚,意识一流宇宙强无敌祝 on 2017/4/11.
 *
 */

public class SplashActivity extends AppCompatActivity {

    private static final int DELAY = 100;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == DELAY) {
                enterMainUi();
            }
        }
    };

    //开启intent不可以直接写在handleMessage方法中
    private void enterMainUi() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //检查权限
        String[] permissions = CheckPermissionUtils.checkPermission(this);
        //权限都申请了
        if (permissions.length == 0) {
            mHandler.sendEmptyMessageDelayed(DELAY, 1000);

        }else {//存在没有申请的权限
            //申请权限    100-->请求码
            ActivityCompat.requestPermissions(this,permissions,100);
        }

    }

    /**
     * 申请权限的回调
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            for (int grantResult : grantResults) {
                //权限申请被拒绝
                if (grantResult == PackageManager.PERMISSION_DENIED) {
                    //结束界面
                    finish();
                    return;
                }
            }
            //循环走完说明没有被拒绝
            mHandler.sendEmptyMessageDelayed(DELAY, 1000);
        }
    }
}
