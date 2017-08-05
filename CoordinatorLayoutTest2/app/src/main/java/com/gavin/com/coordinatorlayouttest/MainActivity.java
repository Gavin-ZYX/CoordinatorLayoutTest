package com.gavin.com.coordinatorlayouttest;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @BindView(R.id.table_layout)
    TabLayout mTabLayout;
    @BindView(R.id.vp)
    ViewPager mVp;
    @BindView(R.id.ll)
    LinearLayout mLl;

    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.sticky_view)
    View mStickyView;
    @BindView(R.id.header)
    View mHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initViewPager();
        setAppBarListener();
    }

    private void setAppBarListener() {
        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Log.i(TAG, "offset : " + verticalOffset);
                int minScrollHeight = mHeader.getMeasuredHeight();
                int margin = minScrollHeight + verticalOffset;
                margin = margin > 0 ? 0 : margin;
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mStickyView.getLayoutParams();
                layoutParams.topMargin = margin;
                mStickyView.setLayoutParams(layoutParams);
            }
        });
    }

    private List<Fragment> mFragmentList = new ArrayList<>();

    private void initViewPager() {
        mFragmentList.add(new MyFragment());
        mFragmentList.add(new MyFragment());
        mFragmentList.add(new MyFragment());
        mVp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }
        });

        mTabLayout.addTab(mTabLayout.newTab().setText("Tab1"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Tab2"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Tab3"));

        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mVp));
        mVp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
    }
}
