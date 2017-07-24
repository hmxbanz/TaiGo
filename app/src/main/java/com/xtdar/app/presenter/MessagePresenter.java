package com.xtdar.app.presenter;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ListView;

import com.xtdar.app.Interface.IFavorView;
import com.xtdar.app.R;
import com.xtdar.app.adapter.FavorAdapter;
import com.xtdar.app.server.async.OnDataListener;
import com.xtdar.app.view.activity.FavorActivity;
import com.xtdar.app.view.activity.MessageActivity;
import com.xtdar.app.view.fragment.FavorVideoFragment;
import com.xtdar.app.view.fragment.PersonMsgFragment;
import com.xtdar.app.view.fragment.SysMsgFragment;

import java.util.ArrayList;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class MessagePresenter extends BasePresenter implements OnDataListener {
    Context mContext;
    IFavorView mView;
    MessageActivity mActivity;
    ListView mListView;
    private FavorAdapter mFavorAdapter;
    private ViewPager viewpager;
    private ArrayList<Fragment> mFragments;
    private FragmentPagerAdapter mFragmentPagerAdapter;

    public MessagePresenter(Context context) {
        super(context);
        this.mView=(IFavorView)context;
        mActivity= (MessageActivity) context;
    }

    public void init(){
        mView.initData();
        //LoadDialog.show(context);

        TabLayout tabLayout= (TabLayout) mActivity.findViewById(R.id.tabLayout);
//        tabLayout.addTab(tabLayout.newTab().setText("视频"), true);//添加 Tab,默认选中
//        tabLayout.addTab(tabLayout.newTab().setText("音频"),false);//添加 Tab,默认不选中

        viewpager = (ViewPager) mActivity.findViewById(R.id.history_viewpager);

        mFragments = new ArrayList<>();
        mFragments.add(SysMsgFragment.getInstance());
        mFragments.add(PersonMsgFragment.getInstance());

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
        viewpager.setOffscreenPageLimit(1);
        tabLayout.setupWithViewPager(viewpager);
        tabLayout.getTabAt(0).setText("系统消息");
        tabLayout.getTabAt(1).setText("个人消息");
    };

}
