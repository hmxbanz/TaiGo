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

import com.xtdar.app.R;
import com.xtdar.app.presenter.Main2Presenter;
import com.xtdar.app.view.fragment.HomeFragment;
import com.xtdar.app.view.fragment.MineFragment;
import com.xtdar.app.view.fragment.ShopFragment;
import com.xtdar.app.view.fragment.ShowFragment;
import com.xtdar.app.view.widget.DragPointView;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends BaseActivity implements View.OnClickListener{
    private final int CURRENTVIEWPAGEINDEX =0;
    private final int MAXCACHEVIEWPAGES =3;
    private ViewPager mViewPager;
    private List<Fragment> mFragments;
    private ImageView mImageHome,mImageShop, mImageAr,mImageDiscovery, mImageMe, mMineRed;
    private TextView mTextHome,mTextShop, mTextDiscovery,mTextMe;
    private DragPointView mUnreadNumView;

    private View viewMainTop;
    private Main2Presenter main2Presenter;
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
        RelativeLayout homeLayout,shopLayout, arLayout, discoveryLayout,meLayout;
        homeLayout = (RelativeLayout) findViewById(R.id.tab_layout_home);
        shopLayout = (RelativeLayout) findViewById(R.id.tab_layout_shop);
        arLayout = (RelativeLayout) findViewById(R.id.tab_layout_logo);
        discoveryLayout = (RelativeLayout) findViewById(R.id.tab_layout_show);
        meLayout = (RelativeLayout) findViewById(R.id.tab_layout_me);
        mImageHome = (ImageView) findViewById(R.id.tab_img_home);
        mImageShop = (ImageView) findViewById(R.id.tab_img_shop);
        mImageAr = (ImageView) findViewById(R.id.tab_img_logo);
        mImageDiscovery = (ImageView) findViewById(R.id.tab_img_discovery);
        mImageMe = (ImageView) findViewById(R.id.tab_img_me);
        mTextHome = (TextView) findViewById(R.id.tab_text_home);
        mTextShop=(TextView)findViewById(R.id.tab_text_shop);
        mTextDiscovery=(TextView)findViewById(R.id.tab_text_discovery);
        mTextMe = (TextView) findViewById(R.id.tab_text_me);
        homeLayout.setOnClickListener(this);
        shopLayout.setOnClickListener(this);
        arLayout.setOnClickListener(this);
        discoveryLayout.setOnClickListener(this);
        meLayout.setOnClickListener(this);
        viewMainTop = findViewById(R.id.main_top);
    }
    private void initMianViewPager() {
        Fragment mConversationList;
        FragmentPagerAdapter mFragmentPagerAdapter; //将 tab  页面持久在内存中
        mViewPager = (ViewPager) findViewById(R.id.main_viewpager);
        mUnreadNumView = (DragPointView) findViewById(R.id.seal_num);
//        mUnreadNumView.setOnClickListener(this);
//        mUnreadNumView.setDragListencer(new DragListencer());
        //mConversationList = initConversationList();
        //mFragments.add(mConversationList);
        mFragments = new ArrayList<>();
        mFragments.add(HomeFragment.getInstance());
        mFragments.add(ShopFragment.getInstance());
        mFragments.add(ShowFragment.getInstance());
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
        mViewPager.setAdapter(mFragmentPagerAdapter);
        mViewPager.setCurrentItem(CURRENTVIEWPAGEINDEX);
        mViewPager.setOffscreenPageLimit(MAXCACHEVIEWPAGES);
        mViewPager.setOnPageChangeListener(new PageChangerListener());
        //initData();
    }
    private void changeTextViewColor() {
        mImageHome.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_home));
        mImageShop.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_shop));
        mImageAr.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_ar));
        mImageDiscovery.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_discovery));
        mImageMe.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_me));
        mTextHome.setTextColor(Color.parseColor("#abadbb"));
        mTextShop.setTextColor(Color.parseColor("#abadbb"));
        mTextDiscovery.setTextColor(Color.parseColor("#abadbb"));
        mTextMe.setTextColor(Color.parseColor("#abadbb"));
    }
    private void changeSelectedTabState(int position) {
        switch (position) {
            case 0:
                mTextHome.setTextColor(Color.parseColor("#07a5ff"));
                mImageHome.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_home_on));
                break;
            case 1:
                mTextShop.setTextColor(Color.parseColor("#07a5ff"));
                mImageShop.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_shop_on));
                break;
            case 2:
                mTextDiscovery.setTextColor(Color.parseColor("#07a5ff"));
                mImageDiscovery.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_discovery_on));
                break;
            case 3:
                mTextMe.setTextColor(Color.parseColor("#07a5ff"));
                mImageMe.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_me_on));
                break;
        }
    }
    private class PageChangerListener implements ViewPager.OnPageChangeListener{
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }
        @Override
        public void onPageSelected(int position) {
            changeTextViewColor();
            changeSelectedTabState(position);
            int index= mViewPager.getCurrentItem();
            if(index==2){
            }
                HomeFragment homeFragment= HomeFragment.getInstance();
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
                mViewPager.setCurrentItem(0, false);
                break;
            case R.id.tab_layout_shop:
                mViewPager.setCurrentItem(1, false);
                break;
            case R.id.tab_layout_logo:
                startActivity(new Intent(this,UnityPlayerActivity.class));
                break;
            case R.id.tab_layout_show:
                mViewPager.setCurrentItem(2, false);
                //startActivity(new Intent(this,LoginActivity.class));
                //mMineRed.setVisibility(View.GONE);
                break;
            case R.id.tab_layout_me:
                mViewPager.setCurrentItem(3, false);
                startActivity(new Intent(this,LoginActivity.class));
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
        int index = 3;///requestCode >> 16;
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
        if (ShowFragment.getInstance().onBackPressed()) {
            return;
        }
        finish();
    }
}
