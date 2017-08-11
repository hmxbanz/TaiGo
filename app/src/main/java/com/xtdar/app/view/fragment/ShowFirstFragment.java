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
import com.xtdar.app.presenter.HomeNuPresenter;


/**
 * Created by AMing on 16/6/21.
 * Company RongCloud
 */
public class ShowFirstFragment extends Fragment  {
    RelativeLayout layoutBack,layoutRight;
    //@BindView(R.id.list_item_recycler)
    RecyclerView videoList;
    private View view;
    HomeNuPresenter presenter;
    public static ShowFirstFragment instance = null;
    private SwipeRefreshLayout swiper;


    public static ShowFirstFragment getInstance() {
        if (instance == null) {
            instance = new ShowFirstFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_show, null);
        initViews();

        presenter = new HomeNuPresenter(getContext());
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        presenter.init(swiper,videoList);
        presenter.loadData();
    }
    private void initViews() {
        swiper=(SwipeRefreshLayout)view.findViewById(R.id.swiper);
        videoList= (RecyclerView) view.findViewById(R.id.list_item_recycler);
    }

    public boolean onBackPressed() {
        if (StandardGSYVideoPlayer.backFromWindowFull(getActivity())) {
            return true;
        }
        return false;
    }
}
