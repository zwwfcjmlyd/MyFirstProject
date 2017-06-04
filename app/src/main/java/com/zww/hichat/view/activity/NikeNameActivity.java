package com.zww.hichat.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.zww.hichat.R;
import com.zww.hichat.adapter.SimpleTextWatcher;
import com.zww.hichat.base.BaseActivity;

import butterknife.ButterKnife;

public class NikeNameActivity extends BaseActivity implements View.OnClickListener {


    TextInputEditText mEtNickname;
    Button mBtNext;
    LinearLayout mActivityRegister;
    private String mNickName;

    @Override
    protected void initContantView(View view) {
        mEtNickname = ButterKnife.findById(view, R.id.et_nickname);
        mBtNext = (Button) view.findViewById(R.id.bt_next);
        mActivityRegister = ButterKnife.findById(view, R.id.activity_register);
        mEtNickname.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                mNickName = mEtNickname.getText().toString().trim();
                if (TextUtils.isEmpty(mNickName)) {
                    mBtNext.setEnabled(false);
                } else {
                    mBtNext.setEnabled(true);
                }
            }
        });
        //按钮下一步设置点击事件
        mBtNext.setOnClickListener(this);

    }

    @Override
    public View addBodyView(LayoutInflater inflater, FrameLayout content) {
        return inflater.inflate(R.layout.activity_enter_nick_name, content, true);
    }

    //在父类已经定义了点击事件,子类想实现自己特有的需求,需要对父类的点击事件方法进行重写
    public void onClick(View view){
        switch (view.getId()){
            case R.id.bt_next:
                enterPersonalInfoUI();
                break;
            case R.id.ib_back:
                showExitAlertDialog();
                break;
        }
    }

    /**
     * 进入注册详情界面
     */
    private void enterPersonalInfoUI() {
        Intent intent = new Intent(this,PersonalInfoActivity.class);
        intent.putExtra("username",mEtNickname.getText().toString().trim());
        startActivity(intent);
    }

    /**
     * 弹出退出提示对话框
     */
    private void showExitAlertDialog() {
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("确定要放弃注册吗?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("取消",null)
                .show();
    }


    //启用软件盘时
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //当点击软键盘的返回键时
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            showExitAlertDialog();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
