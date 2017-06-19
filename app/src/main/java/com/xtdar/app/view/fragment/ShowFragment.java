package com.xtdar.app.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.GSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.xtdar.app.R;
import com.xtdar.app.presenter.HomeShowPresenter;
import com.xtdar.app.server.response.ShowResponse;
import com.xtdar.app.video.RecyclerBaseAdapter;
import com.xtdar.app.video.RecyclerItemNormalHolder;
import com.xtdar.app.video.RecyclerNormalAdapter;
import com.xtdar.app.video.VideoModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


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
    HomeShowPresenter homeShowPresenter;
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
        layoutBack= (RelativeLayout) view.findViewById(R.id.layout_back);
        layoutBack.setVisibility(View.INVISIBLE);
        TextView txtTitle= (TextView) view.findViewById(R.id.text_title);
        txtTitle.setText("秀场");
        TextView txtRight= (TextView) view.findViewById(R.id.text_right);
        txtRight.setVisibility(View.VISIBLE);
        txtRight.setTextColor(getResources().getColor(R.color.titleBlue));
        txtRight.setText("玩一把");

        resolveData();

        homeShowPresenter = new HomeShowPresenter(getContext());
        homeShowPresenter.init(videoList);
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
