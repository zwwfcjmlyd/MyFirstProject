package com.zww.hichat.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zww.hichat.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public abstract class BaseActivity extends AppCompatActivity {

    @BindView(R.id.ib_back)
    public ImageButton mIbBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.header_left)
    LinearLayout mHeaderLeft;
    @BindView(R.id.header_right)
    LinearLayout mHeaderRight;
    @BindView(R.id.content)
    FrameLayout mContent;
    @BindView(R.id.rl_root)
    RelativeLayout mRlRoot;

    public RelativeLayout getRlRoot() {
        return mRlRoot;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        ButterKnife.bind(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = addBodyView(inflater, mContent);
        initContantView(view);

    }


    /**
     * 添加内容view的方法,要求子类进行重写,具体添加什么样的布局,具体由子类决定
     *
     * @param inflater
     * @param content
     * @return
     */
    public abstract View addBodyView(LayoutInflater inflater, FrameLayout content);

    /**
     * 初始化子布局
     *
     * @param view
     */
    protected abstract void initContantView(View view);

    public void setTitle(String title) {
        mTvTitle.setText(title);
    }

    @OnClick(R.id.ib_back)
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ib_back:
                finish();
                break;
        }
    }


}
