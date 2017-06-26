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
import com.xtdar.app.presenter.HomeFragmentPresenter;
import com.xtdar.app.presenter.HomeGamePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AMing on 16/6/21.
 * Company RongCloud
 */
public class GameFragment extends Fragment implements View.OnClickListener  {
    private final int CURRENTVIEWPAGEINDEX =1;
    private final int MAXCACHEVIEWPAGES =3;
    private ViewPager mViewPager;
    private List<Fragment> mFragments;

    private View view;
    public static GameFragment instance = null;

    private TabLayout mTabLayout;
    private TextView mTextSearch;
    private RelativeLayout layoutHistory,layoutDownload;
    private TabLayout tabLayout;

    private HomeGamePresenter homeGamePresenter;
    private RelativeLayout layout_back;
    private TextView title;

    public static GameFragment getInstance() {
        if (instance == null) {
            instance = new GameFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_game, null);
        initViews();
        initMianViewPager();
//        initData();
        homeGamePresenter = new HomeGamePresenter(getContext());
        homeGamePresenter.init(tabLayout);

        return view;
    }

    private void initViews() {
        layout_back=(RelativeLayout)view.findViewById(R.id.layout_back);
        layout_back.setVisibility(View.INVISIBLE);
        title=(TextView)view.findViewById(R.id.text_title);
        title.setText("益智游戏");
        tabLayout= (TabLayout) view.findViewById(R.id.tabLayout);

        //tabLayout.setTabTextColors(R.color.appTextColor, R.color.red);//设置文本在选中和为选中时候的颜色

        //tabLayout.setupWithViewPager(mViewPager);
    }
    private void initMianViewPager() {
        Fragment mConversationList;
        FragmentPagerAdapter mFragmentPagerAdapter; //将 tab  页面持久在内存中
        mViewPager = (ViewPager) view.findViewById(R.id.home_viewpager);

        mFragments = new ArrayList<>();
        mFragments.add(HomeShotGameFragment.getInstance());
        mFragments.add(HomeCarGameFragment.getInstance());
        mFragments.add(HomeLearnGameFragment.getInstance());
        mFragments.add(HomeAllGameFragment.getInstance());
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

        }
    }
}
