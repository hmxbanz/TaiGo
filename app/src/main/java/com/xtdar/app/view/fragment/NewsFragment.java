package com.xtdar.app.view.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xtdar.app.R;
import com.xtdar.app.presenter.NewsPresenter;

import butterknife.BindView;

/**
 * Created by AMing on 16/6/21.
 * Company RongCloud
 */
public class NewsFragment extends BaseFragment {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swiper)
    SwipeRefreshLayout swiper;
    NewsPresenter presenter;
    public static NewsFragment instance = null;

    public static NewsFragment getInstance() {
        if (instance == null) {
            instance = new NewsFragment();
        }
        return instance;
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initView() {
        presenter = new NewsPresenter(getContext());
        presenter.init(swiper, recyclerView);
    }

    @Override
    protected void initData() {
        presenter.loadData();
    }


}

