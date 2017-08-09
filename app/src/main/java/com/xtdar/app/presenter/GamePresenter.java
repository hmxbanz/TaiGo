package com.xtdar.app.presenter;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.xtdar.app.server.response.TagResponse;
import com.xtdar.app.view.activity.Main2Activity;
import com.xtdar.app.view.fragment.SpecialGameFragment;
import com.xtdar.app.view.fragment.MallGameFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class GamePresenter extends BasePresenter {
    private static final int GETTAGS = 1;
    private final int CURRENTVIEWPAGEINDEX =1;
    private final int MAXCACHEVIEWPAGES =2;
    private TabLayout tabLayout;
    private List<TagResponse.DataBean> tagList;
    private List<Fragment> mFragments;
    private ViewPager viewPager;

    private Main2Activity activity;
    public GamePresenter(Context context){
        super(context);
        activity = (Main2Activity) context;
    }

    public void init(TabLayout tabLayout, ViewPager viewPager) {
        this.tabLayout=tabLayout;
        this.viewPager=viewPager;
        initMianViewPager();
        tabLayout.getTabAt(0).setText("大厅");
        tabLayout.getTabAt(1).setText("专区");
    }

    private void initMianViewPager() {
        FragmentPagerAdapter mFragmentPagerAdapter; //将 tab  页面持久在内存中
        mFragments = new ArrayList<>();
        mFragments.add(MallGameFragment.getInstance());
        mFragments.add(SpecialGameFragment.getInstance());

        mFragmentPagerAdapter = new FragmentPagerAdapter(activity.getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }
            @Override
            public int getCount() {
                return mFragments.size();
            }
        };
        viewPager.setAdapter(mFragmentPagerAdapter);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(MAXCACHEVIEWPAGES);
        //mViewPager.setOnPageChangeListener(new PageChangerListener());
        //initData();
        tabLayout.setupWithViewPager(viewPager);

    }


}