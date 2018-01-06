package com.xtdar.app.view.fragment;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.xtdar.app.R;
import com.xtdar.app.loader.GlideImageLoader;
import com.xtdar.app.presenter.HomeRecommendPresenter;
import com.xtdar.app.view.activity.MineActivity;
import com.xtdar.app.view.activity.SignInActivity;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AMing on 16/6/21.
 * Company RongCloud
 */
public class HomeRecommendFragment extends BaseFragment  {
    public static HomeRecommendFragment instance = null;
    public static List<?> images=new ArrayList<>();
    private RecyclerView recycleView;
    public ScrollView scrollView;

    private HomeRecommendPresenter presenter;
    private Banner banner;
    private RelativeLayout layout_me;
    private View right_icon;

    public static HomeRecommendFragment getInstance() {
        if (instance == null) {
            instance = new HomeRecommendFragment();
        }
        return instance;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_recommend;
    }

    @Override
    protected void initView() {
        layout_me=findView(R.id.layout_me);
        layout_me.setOnClickListener(this);
        right_icon=findView(R.id.right_icon);
        right_icon.setOnClickListener(this);
        presenter = new HomeRecommendPresenter(getContext());
        //简单使用
        banner = findView(R.id.banner);
        banner.setImageLoader(new GlideImageLoader());//设置图片加载器
        //banner.setOnBannerListener(this);
        recycleView= findView(R.id.recyclerView);

        //        TypedArray typedArray = context.obtainStyledAttributes(attrs
//                , cn.hugeterry.coordinatortablayout.R.styleable.CoordinatorTabLayout);
        //mTabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
//        int tabIndicatorColor = typedArray.getColor(cn.hugeterry.coordinatortablayout.R.styleable.CoordinatorTabLayout_tabIndicatorColor, getResources().getColor(cn.hugeterry.coordinatortablayout.R.color.mainColorPinkDark));
//        mTabLayout.setSelectedTabIndicatorColor(tabIndicatorColor);
//
//        int tabTextColor = typedArray.getColor(cn.hugeterry.coordinatortablayout.R.styleable.CoordinatorTabLayout_tabTextColor, getResources().getColor(cn.hugeterry.coordinatortablayout.R.color.mainColorPinkDark));
//        mTabLayout.setTabTextColors(ColorStateList.valueOf(tabTextColor));


        //String[] urls = getResources().getStringArray(R.array.url);
        //String[] tips = getResources().getStringArray(R.array.title);
//        List list = Arrays.asList(urls);
//        images = new ArrayList(list);

        //if(Build.VERSION.SDK_INT>=23)
//        recycleView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                if (gridLayoutManager.findLastCompletelyVisibleItemPosition()==(UserList.getData().size()-1))
//                {}
//            }
//        });
        //recycleView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.HORIZONTAL));
        presenter.init(banner,recycleView);
    }

    @Override
    protected void initData() {
        presenter.loadData();
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
