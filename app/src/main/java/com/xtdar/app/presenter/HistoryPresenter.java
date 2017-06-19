package com.xtdar.app.presenter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;


import com.xtdar.app.R;
import com.xtdar.app.adapter.FavorAdapter;
import com.xtdar.app.adapter.HistoryAdapter;
import com.xtdar.app.model.UserList;
import com.xtdar.app.server.async.OnDataListener;
import com.xtdar.app.view.activity.HistoryActivity;
import com.xtdar.app.view.fragment.HistoryMusicFragment;
import com.xtdar.app.view.fragment.HistoryVideoFragment;
import com.xtdar.app.view.fragment.HomeRecommendFragment;
import com.xtdar.app.view.widget.LoadDialog;

import java.util.ArrayList;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class HistoryPresenter extends BasePresenter implements OnDataListener {
    private HistoryActivity mActivity;
    private ListView mListView;
    private HistoryAdapter mHistoryAdapter;
    private ViewPager viewpager;
    private ArrayList<Fragment> mFragments;
    private FragmentPagerAdapter mFragmentPagerAdapter;


    public HistoryPresenter(Context context){
        super(context);
        mActivity = (HistoryActivity) context;
    }

    public void init() {
        //LoadDialog.show(mContext);

        TabLayout tabLayout= (TabLayout) mActivity.findViewById(R.id.tabLayout);
//        tabLayout.addTab(tabLayout.newTab().setText("视频"), true);//添加 Tab,默认选中
//        tabLayout.addTab(tabLayout.newTab().setText("音频"),false);//添加 Tab,默认不选中

        viewpager = (ViewPager) mActivity.findViewById(R.id.history_viewpager);

        mFragments = new ArrayList<>();
        mFragments.add(HistoryVideoFragment.getInstance());
        mFragments.add(HistoryMusicFragment.getInstance());

        mFragmentPagerAdapter = new FragmentPagerAdapter(mActivity.getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }
            @Override
            public int getCount() {
                return mFragments.size();
            }
        };
        viewpager.setAdapter(mFragmentPagerAdapter);
        viewpager.setCurrentItem(0);
        viewpager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewpager);
        tabLayout.getTabAt(0).setText("视频");
        tabLayout.getTabAt(1).setText("音频");

    }


}