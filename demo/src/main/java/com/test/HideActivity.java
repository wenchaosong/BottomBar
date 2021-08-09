package com.test;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.ms.bottombar.NavigationController;
import com.ms.bottombar.PageNavigationView;

public class HideActivity extends AppCompatActivity {

    static NavigationController mNavigationController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hide);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PageNavigationView pageBottomTabLayout = (PageNavigationView) findViewById(R.id.tab);

        mNavigationController = pageBottomTabLayout.material()
                .addItem(R.drawable.ic_restore_teal_24dp, "Recents")
                .addItem(R.drawable.ic_favorite_teal_24dp, "Favorites")
                .addItem(R.drawable.ic_nearby_teal_24dp, "Nearby")
                .build();

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new TestViewPagerAdapter(getSupportFragmentManager()));

        mNavigationController.setupWithViewPager(viewPager);
    }

    /**
     * 监听列表的滑动来控制底部导航栏的显示与隐藏
     */
    private static class ListScrollListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (dy > 8) {//列表向上滑动
                mNavigationController.hideBottomLayout();
            } else if (dy < -8) {//列表向下滑动
                mNavigationController.showBottomLayout();
            }
        }
    }

    //下面几个类都是为了测试写的

    private class TestViewPagerAdapter extends FragmentPagerAdapter {

        TestViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new TestFragment();
        }

        @Override
        public int getCount() {
            return mNavigationController.getItemCount();
        }
    }

    public static class TestFragment extends Fragment {

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.recyclerview, container, false);
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            recyclerView.setAdapter(new TestAdapter());
            recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
            recyclerView.addOnScrollListener(new ListScrollListener());
        }
    }

    private static class TestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            int padding = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 16, parent.getResources().getDisplayMetrics());
            TextView textView = new TextView(parent.getContext());
            textView.setPadding(padding, padding, padding, padding);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

            return new RecyclerView.ViewHolder(textView) {
            };
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            if (holder.itemView instanceof TextView) {
                ((TextView) holder.itemView).setText(String.valueOf(position));
            }
        }

        @Override
        public int getItemCount() {
            return 100;
        }
    }
}
