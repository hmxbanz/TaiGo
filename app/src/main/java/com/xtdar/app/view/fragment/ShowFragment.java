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
import com.xtdar.app.presenter.HomeShowPresenter;
import com.xtdar.app.server.response.ShowResponse;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by AMing on 16/6/21.
 * Company RongCloud
 */
public class ShowFragment extends Fragment  {
    RelativeLayout layoutBack,layoutRight;
    //@BindView(R.id.list_item_recycler)
    RecyclerView videoList;

    List<ShowResponse.DataBean> dataList = new ArrayList<>();

    private View view;
    HomeShowPresenter presenter;
    public static ShowFragment instance = null;


    public static ShowFragment getInstance() {
        if (instance == null) {
            instance = new ShowFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_show, null);
        videoList= (RecyclerView) view.findViewById(R.id.list_item_recycler);


        resolveData();

        presenter = new HomeShowPresenter(getContext());
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
