package com.xtdar.app.presenter;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ListView;

import com.xtdar.app.Interface.IFavorView;
import com.xtdar.app.R;
import com.xtdar.app.adapter.DownloadAdapter;
import com.xtdar.app.adapter.FavorAdapter;
import com.xtdar.app.server.async.OnDataListener;
import com.xtdar.app.view.activity.DownloadActivity;
import com.xtdar.app.view.activity.FavorActivity;
import com.xtdar.app.view.fragment.DownloadMusicFragment;
import com.xtdar.app.view.fragment.DownloadVideoFragment;
import com.xtdar.app.view.fragment.FavorMusicFragment;
import com.xtdar.app.view.fragment.FavorVideoFragment;

import java.util.ArrayList;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class DownloadPresenter extends BasePresenter implements OnDataListener {
    Context mContext;
    DownloadActivity mActivity;
    ListView mListView;
    private DownloadAdapter downloadAdapter;
    private ViewPager viewpager;
    private ArrayList<Fragment> mFragments;
    private FragmentPagerAdapter mFragmentPagerAdapter;

    public DownloadPresenter(Context context) {
        super(context);
        //this.mView=(IFavorView)context;
        mActivity= (DownloadActivity) context;

    }

    public void init(){
        //mView.initData();
        //LoadDialog.show(mContext);

        TabLayout tabLayout= (TabLayout) mActivity.findViewById(R.id.tabLayout);
//        tabLayout.addTab(tabLayout.newTab().setText("视频"), true);//添加 Tab,默认选中
//        tabLayout.addTab(tabLayout.newTab().setText("音频"),false);//添加 Tab,默认不选中

        viewpager = (ViewPager) mActivity.findViewById(R.id.history_viewpager);

        mFragments = new ArrayList<>();
        mFragments.add(DownloadVideoFragment.getInstance());
        mFragments.add(DownloadMusicFragment.getInstance());

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
