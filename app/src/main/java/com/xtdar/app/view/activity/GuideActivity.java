package com.xtdar.app.view.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.xtdar.app.listener.OnPageScrolledListener;

import com.xtdar.app.R;
import com.xtdar.app.adapter.GuideAdapter;
import com.xtdar.app.widget.scaleIndicator.ScaleIndicatorLayout;

public class GuideActivity extends BaseActivity {
    private ViewPager mViewPager;
    private ScaleIndicatorLayout mIndicator;
    protected void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_guide);
        initView();
    }
    private void initView()  {
        mIndicator =(ScaleIndicatorLayout) findViewById(R.id.indicator);
        this.mViewPager = ((ViewPager)findViewById(R.id.GuiderViewPager));
        GuideAdapter GuideAdapter = new GuideAdapter(getSupportFragmentManager(), this);
        this.mViewPager.setAdapter(GuideAdapter);
        OnPageScrolledListener listener = new OnPageScrolledListener() {
            @Override
            public void onPageScrolled(int curItem, int nextItem, float radio) {
                mIndicator.setRadio(curItem, 1f - radio);
                mIndicator.setRadio(nextItem, radio);
            }
            @Override
            public void onPageSelected(int position) {
                mIndicator.setVisibility(position == 2 ? View.GONE : View.VISIBLE);
            }
        };
        listener.setViewPager(this.mViewPager);
        int curIndex = 0;
        this.mViewPager.setCurrentItem(curIndex);
        mIndicator.setRadio(curIndex, 1f);
    }
    @Override
    protected void onStop() {
        finish();
        super.onStop();
    }

}
