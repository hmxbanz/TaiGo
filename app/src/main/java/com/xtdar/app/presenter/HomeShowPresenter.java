package com.xtdar.app.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.GSYVideoPlayer;
import com.xtdar.app.listener.EndlessRecyclerOnScrollListener;
import com.xtdar.app.listener.GSYVideoPlayerOnScrollListener;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.async.OnDataListener;
import com.xtdar.app.server.response.ShowDetailResponse;
import com.xtdar.app.server.response.ShowResponse;
import com.xtdar.app.video.RecyclerItemNormalHolder;
import com.xtdar.app.video.RecyclerNormalAdapter;
import com.xtdar.app.view.activity.ShowDetailActivity;
import com.xtdar.app.view.widget.LoadDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class HomeShowPresenter extends BasePresenter implements OnDataListener,RecyclerNormalAdapter.ItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    private static final int GETSHOWLIST = 1;
    private final LinearLayoutManager linearLayoutManager;
    private final RecyclerNormalAdapter recyclerNormalAdapter;
    private List<ShowResponse.DataBean> datas=new ArrayList<>();
    private RecyclerView videoList;
    private String lastItem ="0";
    private SwipeRefreshLayout swiper;

    //private ContactsActivity mActivity;
    public HomeShowPresenter(Context context){
        super(context);
        //mActivity = (ContactsActivity) context;
        linearLayoutManager = new LinearLayoutManager(context);
        recyclerNormalAdapter = new RecyclerNormalAdapter(context, datas);
        recyclerNormalAdapter.setOnItemClickListener(this);
    }

    public void init(SwipeRefreshLayout swiper, RecyclerView videoList) {
        this.swiper=swiper;
        this.swiper.setOnRefreshListener(this);
        this.videoList=videoList;
        this.videoList.setAdapter(recyclerNormalAdapter);
        this.videoList.setNestedScrollingEnabled(false);

        videoList.setLayoutManager(linearLayoutManager);
        videoList.addOnScrollListener(new GSYVideoPlayerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                LoadDialog.show(context);
                atm.request(GETSHOWLIST,HomeShowPresenter.this);
            }
        });

        LoadDialog.show(context);
        atm.request(GETSHOWLIST,this);
    }

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        switch (requestCode) {
            case GETSHOWLIST:
                return mUserAction.getShowList(lastItem,"4");
        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        LoadDialog.dismiss(context);
        this.swiper.setRefreshing(false);
        switch (requestCode) {
            case GETSHOWLIST:
                ShowResponse showResponse=(ShowResponse)result;
                if (showResponse != null && showResponse.getData() != null) {
                    datas.addAll(showResponse.getData());
                    lastItem=datas.get(0).getShow_id();
                   recyclerNormalAdapter.notifyDataSetChanged();

                }
                break;
        }
    }

    @Override
    public void onItemClick(int position, String itemId, String classId) {
        ShowDetailActivity.StartActivity(context,itemId,classId);
    }

    @Override
    public void onRefresh() {
        lastItem ="0";
        datas.clear();
        videoList.addOnScrollListener(new GSYVideoPlayerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                LoadDialog.show(context);
                atm.request(GETSHOWLIST,HomeShowPresenter.this);
            }
        });
        atm.request(GETSHOWLIST,this);
    }
}