package com.xtdar.app.view.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
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

import java.util.ArrayList;
import java.util.List;

import com.xtdar.app.R;
import com.xtdar.app.presenter.HomeFragmentPresenter;
import com.xtdar.app.view.activity.DownloadActivity;
import com.xtdar.app.view.activity.HistoryActivity;
import com.xtdar.app.view.activity.SearchActivity;

/**
 * Created by AMing on 16/6/21.
 * Company RongCloud
 */
public class HomeFragment extends Fragment implements View.OnClickListener  {
    private final int CURRENTVIEWPAGEINDEX =1;
    private final int MAXCACHEVIEWPAGES =3;
    private ViewPager mViewPager;
    private List<Fragment> mFragments;

    private View view;
    public static HomeFragment instance = null;

    private TabLayout mTabLayout;
    private TextView mTextSearch;
    private RelativeLayout layoutHistory,layoutDownload;
    private TabLayout tabLayout;

    private HomeFragmentPresenter homeFragmentPresenter;

    public static HomeFragment getInstance() {
        if (instance == null) {
            instance = new HomeFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, null);
        initViews();
        initMianViewPager();
//        initData();
        homeFragmentPresenter = new HomeFragmentPresenter(getContext());
        homeFragmentPresenter.init(tabLayout);

        return view;
    }

    private void initViews() {
        layoutHistory=(RelativeLayout)view.findViewById(R.id.tag_layout_history);
        layoutHistory.setOnClickListener(this);
        layoutDownload=(RelativeLayout)view.findViewById(R.id.tag_layout_download);
        layoutDownload.setOnClickListener(this);
        mTextSearch= (TextView) view.findViewById(R.id.txt_search);
        mTextSearch.setOnClickListener(this);
        Drawable icon_search=getActivity().getResources().getDrawable(R.drawable.icon_search);
        icon_search.setBounds(0,0,50,50);
        //if(Build.VERSION.SDK_INT>+21)
        //icon_search.setTint(getResources().getColor(R.color.wheat));
        mTextSearch.setCompoundDrawables(icon_search,null,null,null);
//
         tabLayout= (TabLayout) view.findViewById(R.id.tabLayout);

        //tabLayout.setTabTextColors(R.color.appTextColor, R.color.red);//设置文本在选中和为选中时候的颜色

        //tabLayout.setupWithViewPager(mViewPager);
    }
    private void initMianViewPager() {
        Fragment mConversationList;
        FragmentPagerAdapter mFragmentPagerAdapter; //将 tab  页面持久在内存中
        mViewPager = (ViewPager) view.findViewById(R.id.home_viewpager);

        mFragments = new ArrayList<>();
        mFragments.add(HomeRecommendFragment.getInstance());
        mFragments.add(HomeAnimationFragment.getInstance());
        mFragments.add(HomeSongFragment.getInstance());
        mFragments.add(HomeStoryFragment.getInstance());
        //mFragments.add(HomeCountryFragment.getInstance());
        //mFragments.add(HomeScienceFragment.getInstance());

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
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(MAXCACHEVIEWPAGES);
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
            case R.id.txt_search:
                getActivity().startActivity(new Intent(getContext(), SearchActivity.class));
                break;
            case R.id.tag_layout_history:
                getActivity().startActivity(new Intent(getContext(), HistoryActivity.class));
                break;
            case R.id.tag_layout_download:
                getActivity().startActivity(new Intent(getContext(), DownloadActivity.class));
                break;
        }
    }
}
