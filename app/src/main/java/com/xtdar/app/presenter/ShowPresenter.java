package com.xtdar.app.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.xtdar.app.common.NLog;
import com.xtdar.app.common.json.JsonMananger;
import com.xtdar.app.listener.GSYVideoPlayerOnScrollListener;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.async.OnDataListener;
import com.xtdar.app.server.response.ShowResponse;
import com.xtdar.app.video.RecyclerNormalAdapter;
import com.xtdar.app.view.activity.ShowDetailActivity;
import com.xtdar.app.view.widget.LoadDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class ShowPresenter extends BasePresenter implements OnDataListener,RecyclerNormalAdapter.ItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    private static final int GETSHOWLIST = 1;
    private final LinearLayoutManager linearLayoutManager;
    private final RecyclerNormalAdapter recyclerNormalAdapter;
    private List<ShowResponse.DataBean> list =new ArrayList<>();
    private RecyclerView videoList;
    private String lastItem ="0";
    private SwipeRefreshLayout swiper;
    private boolean isFirstLoad=true;

    //private ContactsActivity mActivity;
    public ShowPresenter(Context context){
        super(context);
        //mActivity = (ContactsActivity) context;
        linearLayoutManager = new LinearLayoutManager(context);
        recyclerNormalAdapter = new RecyclerNormalAdapter(context, list);
        recyclerNormalAdapter.setOnItemClickListener(this);
    }

    public void init(SwipeRefreshLayout swiper, RecyclerView videoList) {
        this.swiper=swiper;
        this.swiper.setOnRefreshListener(this);
        this.videoList=videoList;
        this.videoList.setAdapter(recyclerNormalAdapter);
        this.videoList.setNestedScrollingEnabled(false);

        videoList.setLayoutManager(linearLayoutManager);
        videoList.addOnScrollListener(getGsyVideoPlayerOnScrollListener());

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
                    List<ShowResponse.DataBean> listTemp = showResponse.getData();
                    lastItem=listTemp.get(listTemp.size()-1).getShow_id();

                    if(isFirstLoad) {
                        list=showResponse.getData();
                        isFirstLoad=false;
                        try {
                            String cache=JsonMananger.beanToJson(list);
                            aCache.put("ShowList",cache);
                        } catch (HttpException e) {
                            e.printStackTrace();
                        }
                    }
                    else
                        list.addAll(showResponse.getData());

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
        this.videoList.addOnScrollListener(getGsyVideoPlayerOnScrollListener());
        lastItem ="0";
        list.clear();
        LoadDialog.show(context);
        atm.request(GETSHOWLIST,this);
    }

    //加载数据
    public void loadData() {
        if(isFirstLoad) {
            String Cache = aCache.getAsString("ShowList");
            NLog.e("CacheString",Cache);
            if(Cache!=null && !("null").equals(Cache))
                try {
                    List<ShowResponse.DataBean> listCache = JsonMananger.jsonToList(Cache, ShowResponse.DataBean.class);
                    if(!TextUtils.isEmpty(listCache.get(0).getImg_path())) {
                        list.addAll(listCache);
                        recyclerNormalAdapter.notifyDataSetChanged();
                    }
                } catch (HttpException e) {
                    e.printStackTrace();
                }
        }
        lastItem ="0";
        isFirstLoad=true;
        LoadDialog.show(context);
        atm.request(GETSHOWLIST,this);
    }


    @NonNull
    private GSYVideoPlayerOnScrollListener getGsyVideoPlayerOnScrollListener() {
        return new GSYVideoPlayerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                if(currentPage>1) {
                    LoadDialog.show(context);
                    atm.request(GETSHOWLIST,ShowPresenter.this);
                }

            }
        };
    }
}