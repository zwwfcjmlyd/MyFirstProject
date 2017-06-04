package com.zww.hichat.view.fragment;

import android.widget.ImageView;
import android.widget.TextView;

import com.zww.hichat.R;
import com.zww.hichat.base.BaseFragment;

/**
 * Created by 风流倜傥,英俊潇洒,走位风骚,意识一流宇宙强无敌祝 on 2017/4/14.
 */

public class LiveFragment extends BaseFragment {
    @Override
    protected void setDefaultHeaderInfo(TextView tvTitle) {
        tvTitle.setText("直播");
    }

    @Override
    public void setEmptyBodyInfo(ImageView iv, TextView tv) {
    }
}
