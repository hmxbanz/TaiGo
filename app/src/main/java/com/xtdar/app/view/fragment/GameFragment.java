package com.xtdar.app.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.xtdar.app.R;
import com.xtdar.app.presenter.GamePresenter;
import com.xtdar.app.view.activity.MineActivity;
import com.xtdar.app.view.activity.SignInActivity;

/**
 * Created by AMing on 16/6/21.
 * Company RongCloud
 */
public class GameFragment extends Fragment implements View.OnClickListener  {

    private ViewPager mViewPager;
    private View view;
    public static GameFragment instance = null;
    private TabLayout tabLayout;
    private GamePresenter presenter;

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
//      initData();
        presenter = new GamePresenter(getContext());
        presenter.init(tabLayout,mViewPager);

        return view;
    }

    private void initViews() {
        view.findViewById(R.id.layout_me).setOnClickListener(this);
        view.findViewById(R.id.right_icon).setOnClickListener(this);
        tabLayout= (TabLayout) view.findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) view.findViewById(R.id.home_viewpager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        presenter.onMeClick(v);
    }
}
