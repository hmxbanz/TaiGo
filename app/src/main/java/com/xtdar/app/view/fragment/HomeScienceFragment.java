package com.xtdar.app.view.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.xtdar.app.R;
import com.xtdar.app.adapter.RecyclerViewAdapter;
import com.xtdar.app.loader.GlideImageLoader;
import com.xtdar.app.model.UserList;
import com.xtdar.app.view.activity.DetailActivity;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by AMing on 16/6/21.
 * Company RongCloud
 */
public class HomeScienceFragment extends Fragment implements RecyclerViewAdapter.ItemClickListener {
    public static HomeScienceFragment instance = null;
    public static List<?> images=new ArrayList<>();
    private RecyclerView recycleView;
    private RecyclerViewAdapter dataAdapter;
    public ScrollView scrollView;
    private View view;
    private GridLayoutManager gridLayoutManager;

    private TextView mTextSearch;
    private TabLayout mTabLayout;

    public static HomeScienceFragment getInstance() {
        if (instance == null) {
            instance = new HomeScienceFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_recommend, null);
        initViews();
//        initData();
        return view;
    }

    private void initViews() {
//        TypedArray typedArray = context.obtainStyledAttributes(attrs
//                , cn.hugeterry.coordinatortablayout.R.styleable.CoordinatorTabLayout);
        //mTabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
//        int tabIndicatorColor = typedArray.getColor(cn.hugeterry.coordinatortablayout.R.styleable.CoordinatorTabLayout_tabIndicatorColor, getResources().getColor(cn.hugeterry.coordinatortablayout.R.color.mainColorPinkDark));
//        mTabLayout.setSelectedTabIndicatorColor(tabIndicatorColor);
//
//        int tabTextColor = typedArray.getColor(cn.hugeterry.coordinatortablayout.R.styleable.CoordinatorTabLayout_tabTextColor, getResources().getColor(cn.hugeterry.coordinatortablayout.R.color.mainColorPinkDark));
//        mTabLayout.setTabTextColors(ColorStateList.valueOf(tabTextColor));



        String[] urls = getResources().getStringArray(R.array.url);
        //String[] tips = getResources().getStringArray(R.array.title);
        List list = Arrays.asList(urls);
        images = new ArrayList(list);
        Banner banner = (Banner) view.findViewById(R.id.banner);
        //简单使用
        banner.setImages(images);//设置图片集合
        banner.setImageLoader(new GlideImageLoader());//设置图片加载器
        banner.start();

        //banner.setOnBannerListener(this);

        recycleView= (RecyclerView) view.findViewById(R.id.recyclerView);
        gridLayoutManager=new GridLayoutManager(getContext(),2);
        recycleView.setLayoutManager(gridLayoutManager);
        dataAdapter = new RecyclerViewAdapter(UserList.getData(), getContext());
        dataAdapter.setFooterView(LayoutInflater.from(getContext()).inflate(R.layout.recyclerview_footer,null));
        recycleView.setAdapter(dataAdapter);
        recycleView.setNestedScrollingEnabled(false);
        if(Build.VERSION.SDK_INT>=23)
        recycleView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (gridLayoutManager.findLastCompletelyVisibleItemPosition()==(UserList.getData().size()-1))
                {}
            }
        });
        //recycleView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.HORIZONTAL));
        dataAdapter.setOnItemClickListener(this);
        scrollView=(ScrollView) view.findViewById(R.id.scrollview);
        scrollView.smoothScrollTo(0, 0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //BroadcastManager.getInstance(getActivity()).destroy(SealConst.CHANGEINFO);
    }

    @Override
    public void onItemClick(int position, String data) {
        getActivity().startActivity(new Intent(getActivity(), DetailActivity.class));
        Toast.makeText(getContext(),"你点击了位置："+String.valueOf(position)+"-标题："+data,Toast.LENGTH_SHORT).show();
    }


}
