package com.xtdar.app.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.xtdar.app.R;
import com.xtdar.app.presenter.ShowFirstPresenter;


/**
 * Created by AMing on 16/6/21.
 * Company RongCloud
 */
public class ShowFirstFragment extends BaseFragment  {
    RelativeLayout layoutBack,layoutRight;
    //@BindView(R.id.list_item_recycler)
    RecyclerView videoList;
    private View view;
    ShowFirstPresenter presenter;
    public static ShowFirstFragment instance = null;
    private SwipeRefreshLayout swiper;

    public static ShowFirstFragment getInstance() {
        if (instance == null) {
            instance = new ShowFirstFragment();
        }
        return instance;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_show;
    }

    @Override
    protected void initView() {
        swiper=findView(R.id.swiper);
        videoList= findView(R.id.list_item_recycler);
        presenter = new ShowFirstPresenter(getContext());
        presenter.init(swiper,videoList);
    }

    @Override
    protected void initData() {
        presenter.loadData();
    }


}
