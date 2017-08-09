package com.xtdar.app.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xtdar.app.R;
import com.xtdar.app.presenter.DiscoveryPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AMing on 16/6/21.
 * Company RongCloud
 */
public class DiscoveryFragment extends Fragment implements View.OnClickListener  {
    private final int CURRENTVIEWPAGEINDEX =1;
    private final int MAXCACHEVIEWPAGES =3;
    private ViewPager mViewPager;
    private List<Fragment> mFragments;

    private View view;
    public static DiscoveryFragment instance = null;

    private TabLayout tabLayout;

    private DiscoveryPresenter discoveryPresenter;
    private RelativeLayout layout_back;
    private TextView title;

    public static DiscoveryFragment getInstance() {
        if (instance == null) {
            instance = new DiscoveryFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_discovery, null);
        initViews();
        initMianViewPager();
//        initData();
        discoveryPresenter = new DiscoveryPresenter(getContext());
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        discoveryPresenter.init(tabLayout);
    }

    private void initViews() {
        tabLayout= (TabLayout) view.findViewById(R.id.tabLayout);

        //tabLayout.setTabTextColors(R.color.appTextColor, R.color.red);//设置文本在选中和为选中时候的颜色
        //tabLayout.setupWithViewPager(mViewPager);
    }
    private void initMianViewPager() {
        FragmentPagerAdapter mFragmentPagerAdapter; //将 tab  页面持久在内存中
        mViewPager = (ViewPager) view.findViewById(R.id.discovery_viewpager);

        mFragments = new ArrayList<>();
        mFragments.add(HomeRecommendFragment.getInstance());
        mFragments.add(ShowFragment.getInstance());
        mFragments.add(ShowFirstFragment.getInstance());

        mFragmentPagerAdapter = new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }
            @Override
            public int getCount() {
                return mFragments.size();
            }
        };
        mViewPager.setAdapter(mFragmentPagerAdapter);
        mViewPager.setOffscreenPageLimit(3);
        //mViewPager.setOnPageChangeListener(new PageChangerListener());
        //initData();
        tabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}
