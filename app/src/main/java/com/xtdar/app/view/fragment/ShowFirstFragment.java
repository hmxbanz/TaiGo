package com.xtdar.app.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.xtdar.app.R;
import com.xtdar.app.presenter.HomeNuPresenter;
import com.xtdar.app.presenter.HomeShowPresenter;
import com.xtdar.app.server.response.ShowResponse;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by AMing on 16/6/21.
 * Company RongCloud
 */
public class ShowFirstFragment extends Fragment  {
    RelativeLayout layoutBack,layoutRight;
    //@BindView(R.id.list_item_recycler)
    RecyclerView videoList;

    List<ShowResponse.DataBean> dataList = new ArrayList<>();

    private View view;
    HomeNuPresenter presenter;
    public static ShowFirstFragment instance = null;


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
        videoList= (RecyclerView) view.findViewById(R.id.list_item_recycler);

        resolveData();

        presenter = new HomeNuPresenter(getContext());
        presenter.init(videoList);
        return view;


    }

    private void resolveData() {
        for (int i = 0; i < 19; i++) {
            ShowResponse.DataBean videoModel = new ShowResponse.DataBean();
            dataList.add(videoModel);
        }
    }
    public boolean onBackPressed() {
        if (StandardGSYVideoPlayer.backFromWindowFull(getActivity())) {
            return true;
        }
        return false;
    }
}
