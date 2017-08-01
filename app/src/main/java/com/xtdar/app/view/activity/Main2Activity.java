package com.xtdar.app.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.xtdar.app.R;
import com.xtdar.app.presenter.Main2Presenter;
import com.xtdar.app.service.BluetoothService;
import com.xtdar.app.view.fragment.DiscoveryFragment;
import com.xtdar.app.view.fragment.GameFragment;
import com.xtdar.app.view.fragment.HomeFragment;
import com.xtdar.app.view.fragment.MineFragment;
import com.xtdar.app.view.widget.DragPointView;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends BaseActivity implements View.OnClickListener{
    private final int CURRENTVIEWPAGEINDEX =0;
    private final int MAXCACHEVIEWPAGES =3;

    public ViewPager getViewPager() {
        return viewPager;
    }

    private ViewPager viewPager;
    private List<Fragment> mFragments;
    private ImageView mImageHome,mImageShop, mImageAr,mImageDiscovery, mImageMe, mMineRed;
    private TextView mTextHome,mTextShop, mTextDiscovery,mTextMe;
    private DragPointView mUnreadNumView;

    private View viewMainTop;
    private Main2Presenter main2Presenter;
    public BluetoothService mBluetoothService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 设置一个exit transition
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().setEnterTransition(new Explode());
            getWindow().setExitTransition(new Explode());
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initViews();
        initMianViewPager();
        changeTextViewColor();
        changeSelectedTabState(0);
        main2Presenter = new Main2Presenter(this);
        main2Presenter.init();
    }
    private void initViews() {
        RelativeLayout homeLayout,shopLayout, discoveryLayout,meLayout;
        homeLayout = (RelativeLayout) findViewById(R.id.tab_layout_home);
        shopLayout = (RelativeLayout) findViewById(R.id.tab_layout_shop);
        discoveryLayout = (RelativeLayout) findViewById(R.id.tab_layout_show);
        meLayout = (RelativeLayout) findViewById(R.id.tab_layout_me);
        mImageHome = (ImageView) findViewById(R.id.tab_img_home);
        mImageShop = (ImageView) findViewById(R.id.tab_img_shop);
        mImageDiscovery = (ImageView) findViewById(R.id.tab_img_discovery);
        mImageMe = (ImageView) findViewById(R.id.tab_img_me);
        mTextHome = (TextView) findViewById(R.id.tab_text_home);
        mTextShop=(TextView)findViewById(R.id.tab_text_shop);
        mTextDiscovery=(TextView)findViewById(R.id.tab_text_discovery);
        mTextMe = (TextView) findViewById(R.id.tab_text_me);
        homeLayout.setOnClickListener(this);
        shopLayout.setOnClickListener(this);
        discoveryLayout.setOnClickListener(this);
        meLayout.setOnClickListener(this);
        viewMainTop = findViewById(R.id.main_top);
    }
    private void initMianViewPager() {
        Fragment mConversationList;
        FragmentPagerAdapter mFragmentPagerAdapter; //将 tab  页面持久在内存中
        viewPager = (ViewPager) findViewById(R.id.main_viewpager);
        mUnreadNumView = (DragPointView) findViewById(R.id.seal_num);
//        mUnreadNumView.setOnClickListener(this);
//        mUnreadNumView.setDragListencer(new DragListencer());
        //mConversationList = initConversationList();
        //mFragments.add(mConversationList);
        mFragments = new ArrayList<>();
        mFragments.add(HomeFragment.getInstance());
        mFragments.add(GameFragment.getInstance());
        mFragments.add(DiscoveryFragment.getInstance());
        mFragments.add(MineFragment.getInstance());

        mFragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
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
        viewPager.setCurrentItem(CURRENTVIEWPAGEINDEX);
        viewPager.setOffscreenPageLimit(MAXCACHEVIEWPAGES);
        viewPager.setOnPageChangeListener(new PageChangerListener());
        //initData();
    }
    private void changeTextViewColor() {
        mImageHome.setImageDrawable(getResources().getDrawable(R.drawable.tab_home));
        mImageShop.setImageDrawable(getResources().getDrawable(R.drawable.tab_shop));
        mImageDiscovery.setImageDrawable(getResources().getDrawable(R.drawable.tab_discovery));
        mImageMe.setImageDrawable(getResources().getDrawable(R.drawable.tab_me));
        mTextHome.setTextColor(Color.parseColor("#abadbb"));
        mTextShop.setTextColor(Color.parseColor("#abadbb"));
        mTextDiscovery.setTextColor(Color.parseColor("#abadbb"));
        mTextMe.setTextColor(Color.parseColor("#abadbb"));
    }
    private void changeSelectedTabState(int position) {
        switch (position) {
            case 0:
                mTextHome.setTextColor(Color.parseColor("#07a5ff"));
                mImageHome.setImageDrawable(getResources().getDrawable(R.drawable.tab_home_on));
                break;
            case 1:
                mTextShop.setTextColor(Color.parseColor("#07a5ff"));
                mImageShop.setImageDrawable(getResources().getDrawable(R.drawable.tab_shop_on));
                break;
            case 2:
                mTextDiscovery.setTextColor(Color.parseColor("#07a5ff"));
                mImageDiscovery.setImageDrawable(getResources().getDrawable(R.drawable.tab_discovery_on));
                break;
            case 3:
                mTextMe.setTextColor(Color.parseColor("#07a5ff"));
                mImageMe.setImageDrawable(getResources().getDrawable(R.drawable.tab_me_on));
                break;
        }
    }
    private class PageChangerListener implements ViewPager.OnPageChangeListener{
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }
        @Override
        public void onPageSelected(int position) {

            int index= viewPager.getCurrentItem();
            if(index==3){
                main2Presenter.onMeClick(viewPager);
            }
            changeTextViewColor();
            changeSelectedTabState(position);
                //HomeFragment homeFragment= HomeFragment.getInstance();
                //homeFragment.scrollView.smoothScrollTo(0, 0);
        }
        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_layout_home:
                viewPager.setCurrentItem(0, false);
                break;
            case R.id.tab_layout_shop:
                viewPager.setCurrentItem(1, false);
                break;
            case R.id.tab_layout_show:
                viewPager.setCurrentItem(2, false);
                //startActivity(new Intent(this,LoginActivity.class));
                //mMineRed.setVisibility(View.GONE);
                break;
            case R.id.tab_layout_me:
                main2Presenter.onMeClick(viewPager);
                //mMineRed.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 重写onactivityresult方法，使二个或多个fragment嵌套使用时能收到onactivityresut回调
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        FragmentManager fm = getSupportFragmentManager();
        int index = 1;///requestCode >> 16;
        switch (requestCode) {
            case 1:
                index=1;
        }
        if (index != 0) {
            index--;
            if (fm.getFragments() == null || index < 0
                    || index >= fm.getFragments().size()) {
                Log.w("TAG", "Activity result fragment index out of range: 0x"
                        + Integer.toHexString(requestCode));
                return;
            }
            Fragment frag = fm.getFragments().get(index);
            if (frag == null) {
                Log.w("TAG", "Activity result no fragment exists for index: 0x"
                        + Integer.toHexString(requestCode));
            } else {
                handleResult(frag, requestCode, resultCode, data);
            }
            return;
        }

    }

    /**
     * 递归调用，对所有子Fragement生效
     *
     * @param frag
     * @param requestCode
     * @param resultCode
     * @param data
     */
    private void handleResult(Fragment frag, int requestCode, int resultCode,
                              Intent data) {
        frag.onActivityResult(requestCode & 0xffff, resultCode, data);
        List<Fragment> frags = frag.getChildFragmentManager().getFragments();
        if (frags != null) {
            for (Fragment f : frags) {
                if (f != null)
                    handleResult(f, requestCode, resultCode, data);
            }
        }
    }

    @Override
    public void onBackPressed() {
//        if (ShowFragment.getInstance().onBackPressed()) {
//            return;
//        }
        finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        main2Presenter.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
    }
}
