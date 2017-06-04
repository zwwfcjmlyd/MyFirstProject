package com.zww.hichat.view.activity;

import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zww.hichat.R;
import com.zww.hichat.base.BaseActivity;
import com.zww.hichat.utils.MyToast;
import com.zww.hichat.utils.ValidateUtils;

import butterknife.ButterKnife;

/**
 * Created by 风流倜傥,英俊潇洒,走位风骚,意识一流宇宙强无敌祝 on 2017/4/14.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    TextInputEditText mEtUsername;
    TextInputEditText mEtPwd;
    TextInputEditText mEtConfirmPwd;
    Button mBtRegister;
    private RelativeLayout mRlLoading;
    private TextView mTvInfo;


    @Override
    public View addBodyView(LayoutInflater inflater, FrameLayout content) {
        View view = inflater.inflate(R.layout.activity_register, content, true);
        return view;
    }

    @Override
    protected void initContantView(View view) {
        setTitle("注册");
        mEtUsername = ButterKnife.findById(view, R.id.et_username);
        mEtPwd = ButterKnife.findById(view, R.id.et_pwd);
        mEtConfirmPwd = ButterKnife.findById(view, R.id.et_confirm_pwd);
        mBtRegister = ButterKnife.findById(view, R.id.bt_register);
        //给注册按钮设置点击事件
        mBtRegister.setOnClickListener(this);
        //给输入框设置监听,当输入框都不为空时,按钮才可按下
        MyTextWatcher myTextWatcher = new MyTextWatcher();
        mEtUsername.addTextChangedListener(myTextWatcher);
        mEtPwd.addTextChangedListener(myTextWatcher);
        mEtConfirmPwd.addTextChangedListener(myTextWatcher);

        //监听键盘的输入
        mEtUsername.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    if (TextUtils.isEmpty(mEtUsername.getText().toString().trim())) {
                        MyToast.show(RegisterActivity.this, "用户名输入为空");
                    }
                }
                return false;
            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_register:
                //进行注册
                register();
                break;
            case R.id.ib_back:
                if (mRlLoading != null && mRlLoading.getVisibility() == View.VISIBLE) {
                    //加载中界面可见且不为空,后退按钮不可点击
                    mIbBack.setEnabled(false);
                } else {
                    mIbBack.setEnabled(true);
                    super.onClick(view);
                }
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mRlLoading != null && mRlLoading.getVisibility() == View.VISIBLE) {
                return true;//中断事件
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 注册
     */
    private void register() {
        //1.校验用户名密码//2.校验确认密码
        boolean isOK = validateUsernameAndPwd();
        if (isOK) {
            //显示加载界面
            addLoadingUI();
        }

    }

    /**
     * 显示加载界面
     */
    private void addLoadingUI() {
        View view = getLayoutInflater().inflate(R.layout.loading, getRlRoot(), true);
        mRlLoading = ButterKnife.findById(view, R.id.rl_loading);
        mTvInfo = ButterKnife.findById(view, R.id.tv_info);
        mTvInfo.setText("正在注册");
    }

    /**
     * 校验用户名和密码
     *
     * @return
     */
    private boolean validateUsernameAndPwd() {
        String username = mEtUsername.getText().toString().trim();
        String pwd = mEtPwd.getText().toString().trim();
        String confirmPwd = mEtConfirmPwd.getText().toString().trim();
        //校验用户名
        if (!ValidateUtils.validateUserName(username)) {
            MyToast.show(this, "用户名不合法");
            //获取焦点
            mEtUsername.setFocusable(true);
            return false;
        }
        //校验密码
        if (!ValidateUtils.validatePassword(pwd)) {
            MyToast.show(this, "密码不合法");
            //清除密码
            mEtPwd.getText().clear();
            mEtConfirmPwd.getText().clear();
            mEtPwd.setFocusable(true);
            return false;
        }
        //确认密码
        if (!pwd.equals(confirmPwd)) {
            MyToast.show(this, "密码和确认密码不一致");
            //清除确认密码输入框
            mEtConfirmPwd.getText().clear();
            mEtConfirmPwd.setFocusable(true);
            return false;
        }
        return true;
    }

    /**
     * 输入框监听
     */
    private class MyTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            String userName = mEtUsername.getText().toString().trim();
            String pwd = mEtPwd.getText().toString().trim();
            String confirmPwd = mEtConfirmPwd.getText().toString().trim();
            //用户名,密码,确认密码都不能为空才能进行注册
            if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(pwd) || TextUtils.isEmpty(confirmPwd)) {
                mBtRegister.setEnabled(false);
            } else {
                mBtRegister.setEnabled(true);
            }

        }
    }
}
