package com.zww.hichat.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zww.hichat.R;
import com.zww.hichat.view.activity.NikeNameActivity;
import com.zww.hichat.view.activity.RegisterActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 风流倜傥,英俊潇洒,走位风骚,意识一流宇宙强无敌祝 on 2017/4/14.
 * fragment基类
 */

public abstract class BaseFragment extends Fragment {

    @BindView(R.id.ib_back)
    ImageButton mIbBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.header_left)
    LinearLayout mHeaderLeft;
    @BindView(R.id.header_right)
    LinearLayout mHeaderRight;
    @BindView(R.id.iv_empty)
    ImageView mIvEmpty;
    @BindView(R.id.tv_info)
    TextView mTvInfo;
    @BindView(R.id.bt_register)
    Button mBtRegister;
    @BindView(R.id.bt_login)
    Button mBtLogin;
    @BindView(R.id.empty_ll)
    LinearLayout mEmptyLl;
    @BindView(R.id.content)
    FrameLayout mContent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.base_fragment, container, false);

        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    /**
     * 初始化布局
     */
    private void initView() {
        //隐藏头部返回箭头
        mIbBack.setVisibility(View.GONE);
        //设置头部的显示
        setDefaultHeaderInfo(mTvTitle);
        //设置内容区域的显示内容
        setEmptyBodyInfo(mIvEmpty,mTvInfo);
    }

    /**
     * 设置默认的标题
     * @param tvTitle
     */
    protected abstract void setDefaultHeaderInfo(TextView tvTitle);

    /**
     * 每一个子fragment的空白显示图片和灰色文字描述不同,所以定义成抽象的强制自雷进行重写
     * @param iv
     * @param tv
     */
    public abstract void setEmptyBodyInfo(ImageView iv, TextView tv);

    /**
     * 点击注册和登录
     * @param view
     */
    @OnClick({R.id.bt_register, R.id.bt_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_register:
                //进行注册
                register();
                break;
            case R.id.bt_login:
                break;
        }
    }

    /**
     * 注册
     */
    private void register() {
        Intent intent = new Intent(getContext(), NikeNameActivity.class);
        startActivity(intent);
    }
}














