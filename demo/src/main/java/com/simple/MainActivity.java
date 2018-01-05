package com.simple;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.bottom.NavigationController;
import com.bottom.PageNavigationView;
import com.bottom.item.BaseTabItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private ViewPager mVpContent;
    private List<TabFragment> mFragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mVpContent = (ViewPager) findViewById(R.id.vp_content);

        initData();

        mVpContent.setAdapter(new MyAdapter(getSupportFragmentManager()));

        PageNavigationView bottomTabLayout = (PageNavigationView) findViewById(R.id.tab);
        PageNavigationView.CustomBuilder custom = bottomTabLayout.custom();
        NavigationController build = custom
                .addItem(newItem(android.R.drawable.ic_menu_camera, android.R.drawable.ic_menu_camera, "相机"))
                .addItem(newItem(android.R.drawable.ic_menu_compass, android.R.drawable.ic_menu_compass, "位置"))
                .addItem(newItem(android.R.drawable.ic_menu_search, android.R.drawable.ic_menu_search, "搜索"))
                .addItem(newItem(android.R.drawable.ic_menu_help, android.R.drawable.ic_menu_help, "帮助"))
                .build();
        build.setupWithViewPager(mVpContent);
    }

    private void initData() {

        TabFragment homeFragment = new TabFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putString(TabFragment.CONTENT, "首页");
        homeFragment.setArguments(bundle1);
        mFragmentList.add(homeFragment);

        TabFragment videoFragment = new TabFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString(TabFragment.CONTENT, "视频");
        videoFragment.setArguments(bundle2);
        mFragmentList.add(videoFragment);

        TabFragment microFragment = new TabFragment();
        Bundle bundle3 = new Bundle();
        bundle3.putString(TabFragment.CONTENT, "微头条");
        microFragment.setArguments(bundle3);
        mFragmentList.add(microFragment);

        TabFragment meFragment = new TabFragment();
        Bundle bundle4 = new Bundle();
        bundle4.putString(TabFragment.CONTENT, "我的");
        meFragment.setArguments(bundle4);
        mFragmentList.add(meFragment);
    }

    //创建一个Item
    private BaseTabItem newItem(int drawable, int checkedDrawable, String text) {
        BottomItemTab normalItemView = new BottomItemTab(this);
        normalItemView.initialize(drawable, checkedDrawable, text);
        normalItemView.setTextDefaultColor(getResources().getColor(R.color.tab_gb));
        normalItemView.setTextCheckedColor(getResources().getColor(R.color.selector_grey));
        return normalItemView;
    }

    class MyAdapter extends FragmentStatePagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }
}
