package com.xtdar.app.view.fragment;

import android.content.Intent;
import android.graphics.Color;
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

import com.jaeger.library.StatusBarUtil;
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
private static final int Blue=0x0007a5ff;
    private View view;
    public static HomeFragment instance = null;


    private HomeFragmentPresenter homeFragmentPresenter;
    private TextView title;
    private RelativeLayout layout_back;

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

//        initData();
        homeFragmentPresenter = new HomeFragmentPresenter(getContext());
        //homeFragmentPresenter.init(tabLayout);
        //StatusBarUtil.setTranslucent(getActivity(), StatusBarUtil.);
        StatusBarUtil.setColor(getActivity(), Blue,0);
        return view;
    }

    private void initViews() {
        layout_back=(RelativeLayout)view.findViewById(R.id.layout_back);
        layout_back.setVisibility(View.INVISIBLE);
        title=(TextView)view.findViewById(R.id.text_title);
        title.setText("设备");


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
