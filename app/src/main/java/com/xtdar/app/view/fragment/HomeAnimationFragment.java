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
import com.xtdar.app.presenter.HomeAnimationPresenter;
import com.xtdar.app.presenter.HomeRecommendPresenter;
import com.xtdar.app.view.activity.DetailActivity;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by AMing on 16/6/21.
 * Company RongCloud
 */
public class HomeAnimationFragment extends Fragment implements RecyclerViewAdapter.ItemClickListener {
    public static HomeAnimationFragment instance = null;
    public static List<?> images=new ArrayList<>();
    private RecyclerView recycleView;
    private RecyclerViewAdapter dataAdapter;
    public ScrollView scrollView;
    private View view;
    private GridLayoutManager gridLayoutManager;

    private TextView mTextSearch;
    private TabLayout mTabLayout;

    private HomeAnimationPresenter homeAnimationPresenter;

    public static HomeAnimationFragment getInstance() {
        if (instance == null) {
            instance = new HomeAnimationFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_animation, null);
        initViews();
//        initData();
        homeAnimationPresenter = new HomeAnimationPresenter(getContext());
        homeAnimationPresenter.init(recycleView);
        return view;
    }

    private void initViews() {

        recycleView= (RecyclerView) view.findViewById(R.id.recyclerView);


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
