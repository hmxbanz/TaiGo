package com.xtdar.app.presenter;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.xtdar.app.Interface.IFavorView;
import com.xtdar.app.adapter.FavorAdapter;
import com.xtdar.app.model.UserList;
import com.xtdar.app.server.async.OnDataListener;
import com.xtdar.app.view.activity.FavorActivity;

import com.xtdar.app.R;
import com.xtdar.app.view.fragment.FavorMusicFragment;
import com.xtdar.app.view.fragment.FavorVideoFragment;
import com.xtdar.app.view.fragment.HistoryMusicFragment;
import com.xtdar.app.view.fragment.HistoryVideoFragment;

import java.util.ArrayList;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class FavorPresenter extends BasePresenter implements OnDataListener {
    Context mContext;
    IFavorView mView;
    FavorActivity mActivity;
    ListView mListView;
    private FavorAdapter mFavorAdapter;
    private ViewPager viewpager;
    private ArrayList<Fragment> mFragments;
    private FragmentPagerAdapter mFragmentPagerAdapter;

    public FavorPresenter(Context context) {
        super(context);
        this.mView=(IFavorView)context;
        mActivity= (FavorActivity) context;

    }

    public void init(){
        mView.initData();
        //LoadDialog.show(context);

        TabLayout tabLayout= (TabLayout) mActivity.findViewById(R.id.tabLayout);
//        tabLayout.addTab(tabLayout.newTab().setText("视频"), true);//添加 Tab,默认选中
//        tabLayout.addTab(tabLayout.newTab().setText("音频"),false);//添加 Tab,默认不选中

        viewpager = (ViewPager) mActivity.findViewById(R.id.history_viewpager);

        mFragments = new ArrayList<>();
        mFragments.add(FavorVideoFragment.getInstance());
        mFragments.add(FavorMusicFragment.getInstance());

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
    };

}
