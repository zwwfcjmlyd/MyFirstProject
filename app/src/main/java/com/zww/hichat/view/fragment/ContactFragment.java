package com.zww.hichat.view.fragment;

import android.widget.ImageView;
import android.widget.TextView;

import com.zww.hichat.R;
import com.zww.hichat.base.BaseFragment;

/**
 * Created by 风流倜傥,英俊潇洒,走位风骚,意识一流宇宙强无敌祝 on 2017/4/14.
 */

public class ContactFragment extends BaseFragment {
    @Override
    protected void setDefaultHeaderInfo(TextView tvTitle) {
        tvTitle.setText("通讯录");
    }

    @Override
    public void setEmptyBodyInfo(ImageView iv, TextView tv) {
        iv.setImageResource(R.drawable.ic_guest_contact_empty);
        tv.setText("可以让附近的人互动");
    }
}
