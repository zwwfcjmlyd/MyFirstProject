package com.zww.hichat.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.FrameLayout;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.zww.hichat.R;
import com.zww.hichat.utils.MyLogger;
import com.zww.hichat.view.fragment.ContactFragment;
import com.zww.hichat.view.fragment.ConversationFragment;
import com.zww.hichat.view.fragment.LiveFragment;
import com.zww.hichat.view.fragment.NearByFragment;
import com.zww.hichat.view.fragment.PersonalFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {

    @BindView(R.id.fl_container)
    FrameLayout mFlContainer;
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar mBottomNavigationBar;
    private BadgeItem mBadgeItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initBottomNavigationBar();
        initFragment();
    }

    private void initFragment() {
        //获取fragment管理器
        FragmentManager fm = getSupportFragmentManager();
        //开启事务
        FragmentTransaction ft = fm.beginTransaction();
        //将附近添加到第一个
        NearByFragment nearByFragment = new NearByFragment();
        ft.add(R.id.fl_container,nearByFragment,"" + 0);
        ft.commit();
    }


    //底部导航条图片资源
    int[] barIcons = new int[]{
            R.drawable.ic_nav_nearby_active,
            R.drawable.ic_nav_live_active,
            R.drawable.ic_nav_conversation_active,
            R.drawable.ic_nav_contacts_active,
            R.drawable.ic_nav_personal_active
    };

    Class[] fragments = new Class[]{
            NearByFragment.class,
            LiveFragment.class,
            ConversationFragment.class,
            ContactFragment.class,
            PersonalFragment.class
    };

    /**
     * 初始化BottomNavigationBar
     * 底部tab列表
     */
    private void initBottomNavigationBar() {
        //从资源文件中获取底部tab文字资源
        String[] bottombarlabels = getResources().getStringArray(R.array.bottombarlabel);
        mBadgeItem = new BadgeItem().setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
        mBadgeItem.setText("12");
        mBottomNavigationBar
                .setBarBackgroundColor(R.color.bottombarcolor)
                .addItem(new BottomNavigationItem(barIcons[0], bottombarlabels[0]))
                .addItem(new BottomNavigationItem(barIcons[1], bottombarlabels[1]))
                .addItem(new BottomNavigationItem(barIcons[2], bottombarlabels[2]).setBadgeItem(mBadgeItem))
                .addItem(new BottomNavigationItem(barIcons[3], bottombarlabels[3]))
                .addItem(new BottomNavigationItem(barIcons[4], bottombarlabels[4]))
                .setActiveColor(R.color.activecolor)//设置选中的颜色
                .setInActiveColor(R.color.inactivecolor)//设置未选中的颜色
                .setFirstSelectedPosition(0)//设置第一个默认选项
                .setMode(BottomNavigationBar.MODE_FIXED)//设置混合模式
                .initialise();//初始化

        //给mBottomNavigationBar设置监听
        mBottomNavigationBar.setTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(int position) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentByTag(position + "");
        FragmentTransaction ft = fm.beginTransaction();
        if (fragment == null) {
            try {
                fragment= (Fragment) fragments[position].newInstance();
                ft.add(R.id.fl_container,fragment,position +"");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            //显示已经添加的fragment,已经被fm管理起来的fragment不用再add
            ft.show(fragment);
        }
        ft.commit();

    }

    @Override
    public void onTabUnselected(int position) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentByTag(position + "");
        FragmentTransaction ft = fm.beginTransaction();
        ft.hide(fragment);
        ft.commit();
    }

    @Override
    public void onTabReselected(int position) {
        //再起点击时回调,当前页面是列表时,会用来处理回到列表的顶部的操作
        MyLogger.d(position + "");
    }
}
