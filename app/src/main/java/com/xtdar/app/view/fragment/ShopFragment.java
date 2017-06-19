package com.xtdar.app.view.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.xtdar.app.R;
import com.xtdar.app.adapter.RecyclerViewAdapter;
import com.xtdar.app.adapter.RecyclerViewAdapterForShop;
import com.xtdar.app.model.Category;
import com.xtdar.app.model.CategoryList;
import com.xtdar.app.model.UserList;
import com.xtdar.app.view.activity.CategoryActivity;

/**
 * Created by AMing on 16/6/21.
 * Company RongCloud
 */
public class ShopFragment extends Fragment  {
    private final int CURRENTVIEWPAGEINDEX =1;
    private final int MAXCACHEVIEWPAGES =3;
    private ViewPager mViewPager;
    private List<Fragment> mFragments;

    private View view;
    public static ShopFragment instance = null;

    private TabLayout mTabLayout;
    private TextView mTextSearch;
    private TabLayout tabLayout;

    public static ShopFragment getInstance() {
        if (instance == null) {
            instance = new ShopFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shop, null);
        initViews();
        initMianViewPager();
//        initData();
        return view;
    }

    private void initViews() {
        //头部
        RecyclerView recycleViewTop= (RecyclerView) view.findViewById(R.id.shop_recyclerview_top);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),4);
        recycleViewTop.setLayoutManager(gridLayoutManager);
        RecyclerViewAdapterForShop dataAdapter2 = new RecyclerViewAdapterForShop(CategoryList.getData(), getContext());
        dataAdapter2.setOnItemClickListener(new RecyclerViewAdapterForShop.ItemClickListener() {
            @Override
            public void onItemClick(int position, String data) {
                Toast.makeText(getActivity(), "位置在："+String.valueOf(position), Toast.LENGTH_LONG).show();
            }
        });
        dataAdapter2.setFooterView(LayoutInflater.from(getContext()).inflate(R.layout.recyclerview_shop_category_footer,null));
        recycleViewTop.setAdapter(dataAdapter2);
        dataAdapter2.getFooterView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "底部", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getContext(), CategoryActivity.class));
            }
        });
        recycleViewTop.setNestedScrollingEnabled(false);


        mTextSearch= (TextView) view.findViewById(R.id.txt_search);
        Drawable icon_search=getActivity().getResources().getDrawable(R.drawable.icon_search);
        icon_search.setBounds(0,0,50,50);
        //if(Build.VERSION.SDK_INT>+21)
        //icon_search.setTint(getResources().getColor(R.color.wheat));
        mTextSearch.setCompoundDrawables(icon_search,null,null,null);
//
        tabLayout= (TabLayout) view.findViewById(R.id.tabLayout);
//        tabLayout.addTab(tabLayout.newTab().setText("推荐"), true);//添加 Tab,默认选中

        //tabLayout.setTabTextColors(R.color.appTextColor, R.color.red);//设置文本在选中和为选中时候的颜色

        //tabLayout.setupWithViewPager(mViewPager);
    }
    private void initMianViewPager() {
        Fragment mConversationList;
        FragmentPagerAdapter mFragmentPagerAdapter; //将 tab  页面持久在内存中
        mViewPager = (ViewPager) view.findViewById(R.id.shop_viewpager);

        mFragments = new ArrayList<>();
        mFragments.add(ShopRecommendFragment.getInstance());
        mFragments.add(ShopBoyFragment.getInstance());
        mFragments.add(ShopGirlFragment.getInstance());
        mFragments.add(Shop03Fragment.getInstance());
        mFragments.add(Shop06Fragment.getInstance());

        mFragmentPagerAdapter = new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
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
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(MAXCACHEVIEWPAGES);
        //mViewPager.setOnPageChangeListener(new PageChangerListener());
        //initData();
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(0).setText("推荐");
        tabLayout.getTabAt(1).setText("男孩");
        tabLayout.getTabAt(2).setText("女孩");
        tabLayout.getTabAt(3).setText("0-3岁");
        tabLayout.getTabAt(4).setText("3-6岁");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
