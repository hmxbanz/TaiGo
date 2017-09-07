package com.xtdar.app.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xtdar.app.XtdConst;
import com.xtdar.app.adapter.ClassListAnimationAdapter;
import com.xtdar.app.common.json.JsonMananger;
import com.xtdar.app.listener.EndlessRecyclerOnScrollListener;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.async.OnDataListener;
import com.xtdar.app.server.response.GameListResponse;
import com.xtdar.app.view.widget.LoadDialog;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class SpecialGamePresenter extends BasePresenter implements SwipeRefreshLayout.OnRefreshListener,OnDataListener,ClassListAnimationAdapter.ItemClickListener {
    private static final int GETSPECIALlIST = 2;
    private Banner Banner;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private ClassListAnimationAdapter dataAdapter;
    private List<GameListResponse.DataBean> list=new ArrayList<>();
    private String lastItem ="0";
    private int lastOffset;
    private int lastPosition;
    private SwipeRefreshLayout swiper;

    private boolean isFirstLoad=true;
    private EndlessRecyclerOnScrollListener onScrollListener;

    public SpecialGamePresenter(Context context){
        super(context);
        dataAdapter = new ClassListAnimationAdapter(this.context);
        dataAdapter.setOnItemClickListener(this);

        dataAdapter.setListItems(list);

    }

    public void init(SwipeRefreshLayout swiper, RecyclerView recyclerView) {
        this.swiper=swiper;
        this.swiper.setOnRefreshListener(this);
        this.recyclerView =recyclerView;
        gridLayoutManager=new GridLayoutManager(context,1);
        onScrollListener= getOnScrollListener();
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(dataAdapter);
        recyclerView.addOnScrollListener(onScrollListener);
        recyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        switch (requestCode) {
            case GETSPECIALlIST:
                return mUserAction.getShot("-1",lastItem,"10");
        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        LoadDialog.dismiss(context);
        this.swiper.setRefreshing(false);
        switch (requestCode) {
            case GETSPECIALlIST:
                GameListResponse response = (GameListResponse) result;
                if (response.getCode() == XtdConst.SUCCESS) {
                    final List<GameListResponse.DataBean> datas = response.getData();
                    if(isFirstLoad) {
                        list = response.getData();
                        isFirstLoad=false;
                        try {
                            String cache=JsonMananger.beanToJson(datas);
                            aCache.put("SpecialGameList",cache);
                        } catch (HttpException e) {
                            e.printStackTrace();
                        }
                    }
                    else
                        list.addAll(response.getData());

                    dataAdapter.setListItems(list);
                    dataAdapter.notifyDataSetChanged();


                }
                break;
        }
    }



    /**
     * 记录RecyclerView当前位置
     */
    private void getPositionAndOffset() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        //获取可视的第一个view
        View topView = layoutManager.getChildAt(0);
        if(topView != null) {
            //获取与该view的顶部的偏移量
            lastOffset = topView.getTop();
            //得到该View的数组位置
            lastPosition = layoutManager.getPosition(topView);
        }
    }

    /**
     * 让RecyclerView滚动到指定位置
     */
    private void scrollToPosition() {
        if(recyclerView.getLayoutManager() != null && lastPosition >= 0) {
            ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPositionWithOffset(lastPosition, lastOffset);
        }
    }

    @Override
    public void onRefresh() {
        recyclerView.addOnScrollListener(getOnScrollListener());
        lastItem ="0";
        list.clear();
        LoadDialog.show(context);
        atm.request(GETSPECIALlIST,this);
    }

    @Override
    public void onItemClick(int position, GameListResponse.DataBean bean) {

    }

    //加载数据
    public void loadData() {

        if(isFirstLoad) {
            String Cache = aCache.getAsString("SpecialGameList");
            if(Cache!=null && !("null").equals(Cache))
                try {
                    List<GameListResponse.DataBean> gameListCache = JsonMananger.jsonToList(Cache, GameListResponse.DataBean.class);
                    list.addAll(gameListCache);
                    dataAdapter.notifyDataSetChanged();

                } catch (HttpException e) {
                    e.printStackTrace();
                }
        }
        lastItem ="0";
        list.clear();
        LoadDialog.show(context);
        atm.request(GETSPECIALlIST,this);
    }


    @NonNull
    private EndlessRecyclerOnScrollListener getOnScrollListener() {
        return new EndlessRecyclerOnScrollListener(SpecialGamePresenter.this.gridLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                lastItem = String.valueOf(currentPage);
                if(currentPage>1) {
                    LoadDialog.show(context);
                    //atm.request(GETSPECIALlIST, SpecialGamePresenter.this);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(recyclerView.getLayoutManager() != null) {
                    getPositionAndOffset();
                }
            }
        };
    }


}