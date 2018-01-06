package com.xtdar.app.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.xtdar.app.R;
import com.xtdar.app.listener.AlertDialogCallback;
import com.xtdar.app.server.response.TagResponse;
import com.xtdar.app.view.activity.LoginActivity;
import com.xtdar.app.view.activity.Main2Activity;
import com.xtdar.app.view.activity.MineActivity;
import com.xtdar.app.view.activity.SignInActivity;
import com.xtdar.app.view.fragment.MallGameFragment;
import com.xtdar.app.view.fragment.SpecialGameFragment;
import com.xtdar.app.widget.DialogWithYesOrNoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class GamePresenter extends BasePresenter {
    private static final int GETTAGS = 1;
    private final int CURRENTVIEWPAGEINDEX = 1;
    private final int MAXCACHEVIEWPAGES = 2;
    private TabLayout tabLayout;
    private List<TagResponse.DataBean> tagList;
    private List<Fragment> mFragments;
    private ViewPager viewPager;

    private Main2Activity activity;
    private final BasePresenter basePresenter;

    public GamePresenter(Context context) {
        super(context);
        basePresenter = BasePresenter.getInstance(context);
        activity = (Main2Activity) context;
    }

    public void init(TabLayout tabLayout, ViewPager viewPager) {
        this.tabLayout = tabLayout;
        this.viewPager = viewPager;
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
        viewPager.setOffscreenPageLimit(MAXCACHEVIEWPAGES);
        //mViewPager.setOnPageChangeListener(new PageChangerListener());
        //initData();
        tabLayout.setupWithViewPager(viewPager);

    }

    public void onMeClick(View v) {
        basePresenter.initData();
        if (!basePresenter.isLogin) {
            DialogWithYesOrNoUtils.getInstance().showDialog(context, "请先登录", null, "前住登录", new AlertDialogCallback() {
                @Override
                public void executeEvent() {
                    context.startActivity(new Intent(context, LoginActivity.class));
                }

            });
        } else {
            switch (v.getId()) {
                case R.id.layout_me:
                    context.startActivity(new Intent(context, MineActivity.class));
                    break;
                case R.id.right_icon:
                    context.startActivity(new Intent(context, SignInActivity.class));
                    break;
            }

        }

    }
}