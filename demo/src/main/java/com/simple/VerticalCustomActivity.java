package com.simple;

import android.os.Bundle;

import com.bottom.NavigationController;
import com.bottom.PageNavigationView;
import com.simple.custom.OnlyTextTab;
import com.simple.other.MyViewPagerAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by mjj on 2017/9/26
 */
public class VerticalCustomActivity extends AppCompatActivity {

    private NavigationController mNavigationController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_custom);

        PageNavigationView pageNavigationView = (PageNavigationView) findViewById(R.id.tab);
        mNavigationController = pageNavigationView.custom()
                .addItem(new OnlyTextTab(this, "A"))
                .addItem(new OnlyTextTab(this, "B"))
                .addItem(new OnlyTextTab(this, "C"))
                .addItem(new OnlyTextTab(this, "D"))
                .addItem(new OnlyTextTab(this, "E"))
                .addItem(new OnlyTextTab(this, "F"))
                .addItem(new OnlyTextTab(this, "G"))
                .addItem(new OnlyTextTab(this, "H"))
                .addItem(new OnlyTextTab(this, "I"))
                .addItem(new OnlyTextTab(this, "J"))
                .addItem(new OnlyTextTab(this, "K"))
                .addItem(new OnlyTextTab(this, "L"))
                .addItem(new OnlyTextTab(this, "M"))
                .addItem(new OnlyTextTab(this, "N"))
                .addItem(new OnlyTextTab(this, "O"))
                .enableVerticalLayout()
                .build();

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(), mNavigationController.getItemCount()));

        //自动适配ViewPager页面切换
        mNavigationController.setupWithViewPager(viewPager);
    }
}
