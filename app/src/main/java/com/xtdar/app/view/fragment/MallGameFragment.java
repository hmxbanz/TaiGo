package com.xtdar.app.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.xtdar.app.common.NToast;
import com.xtdar.app.presenter.MallGamePresenter;
import com.xtdar.app.view.activity.DetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AMing on 16/6/21.
 * Company RongCloud
 */
public class MallGameFragment extends Fragment {
    public static MallGameFragment instance = null;
    public static List<?> images=new ArrayList<>();
    private RecyclerView recycleView;
    public ScrollView scrollView;
    private View view;

    private MallGamePresenter presenter;
    private SwipeRefreshLayout swiper;

    public static MallGameFragment getInstance() {
        if (instance == null) {
            instance = new MallGameFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_animation, null);
        initViews();
//        initData();
        presenter = new MallGamePresenter(getContext());
        presenter.init(swiper,recycleView);
        return view;
    }

    private void initViews() {
        swiper=(SwipeRefreshLayout)view.findViewById(R.id.swiper);
        recycleView= (RecyclerView) view.findViewById(R.id.recyclerView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //BroadcastManager.getInstance(getActivity()).destroy(SealConst.CHANGEINFO);
    }
    @Override
    public void onPause() {
        super.onPause();
        presenter.unbindService();
    }


}
