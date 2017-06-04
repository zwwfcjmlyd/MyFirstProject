package com.zww.hichat;

import android.app.Application;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

import cn.bmob.v3.Bmob;

/**
 * Created by 风流倜傥,英俊潇洒,走位风骚,意识一流宇宙强无敌祝 on 2017/4/16.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    //初始化
    private void init() {
        //初始化Bmob
        initBmob();
        //初始化环信
        initEM();

    }

    private void initBmob() {
        //第一：默认初始化
        Bmob.initialize(this, "db0948c0e2a9706e03658a1e593358eb");
    }

    /**
     * 初始化环信的SDK
     */
    private void initEM() {
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        //初始化
        EMClient.getInstance().init(this, options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
    }
}
