package com.xtdar.app.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.xtdar.app.XtdConst;
import com.xtdar.app.adapter.ClassListNuAdapter;
import com.xtdar.app.common.NLog;
import com.xtdar.app.common.NToast;
import com.xtdar.app.common.json.JsonMananger;
import com.xtdar.app.listener.GSYVideoPlayerOnScrollListener;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.async.OnDataListener;
import com.xtdar.app.server.response.ClassListResponse;
import com.xtdar.app.server.response.ShowResponse;
import com.xtdar.app.view.activity.DetailActivity;
import com.xtdar.app.view.widget.LoadDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class ShowFirstPresenter extends BasePresenter implements OnDataListener,ClassListNuAdapter.ItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    private static final int GETSHOWFIRSTLIST = 1;
    private final LinearLayoutManager linearLayoutManager;
    private List<ShowResponse.DataBean> entity;
    private RecyclerView videoList;
    private String lastItem ="0";
    private ClassListNuAdapter dataAdapter;
    List<ClassListResponse.DataBean> list = new ArrayList<>();
    private SwipeRefreshLayout swiper;
    private boolean isFirstLoad=true;
    private GSYVideoPlayerOnScrollListener onScrollListener;

    //private ContactsActivity mActivity;
    public ShowFirstPresenter(Context context){
        super(context);
        //mActivity = (ContactsActivity) context;
        linearLayoutManager = new LinearLayoutManager(context);
        dataAdapter = new ClassListNuAdapter(context, list);
    }

    public void init(SwipeRefreshLayout swiper,RecyclerView videoList) {
        this.swiper=swiper;
        this.swiper.setOnRefreshListener(this);
        this.videoList=videoList;
        this.videoList.setAdapter(dataAdapter);
        this.videoList.setLayoutManager(linearLayoutManager);
        this.videoList.addOnScrollListener(getGsyVideoPlayerOnScrollListener());
        this.videoList.setNestedScrollingEnabled(false);

    }



    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        switch (requestCode) {
            case GETSHOWFIRSTLIST:
                return mUserAction.getAnimations("1",lastItem,"4");
        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        LoadDialog.dismiss(context);
        this.swiper.setRefreshing(false);
        switch (requestCode) {
            case GETSHOWFIRSTLIST:
                ClassListResponse response = (ClassListResponse) result;
                if (response.getCode() == XtdConst.SUCCESS) {
                    List<ClassListResponse.DataBean> listTemp = response.getData();
                    NLog.e("ShowFirstCacheString>>>>",listTemp);
                    lastItem=listTemp.get(listTemp.size()-1).getItem_id();

                    if(isFirstLoad) {
                        list=response.getData();
                        isFirstLoad=false;
                        try {
                            String cache=JsonMananger.beanToJson(list);
                            aCache.put("ShowFirstList",cache);
                        } catch (HttpException e) {
                            e.printStackTrace();
                        }
                    }
                    else
                        list.addAll(response.getData());

                    dataAdapter.setOnItemClickListener(this);
                    dataAdapter.setListData(list);
                    dataAdapter.notifyDataSetChanged();

                }

                break;
        }
    }

    @Override
    public void onItemClick(int position, String itemId, String classId) {
        DetailActivity.StartActivity(context,itemId,classId);
    }

    @Override
    public void onRefresh() {
        this.videoList.addOnScrollListener(getGsyVideoPlayerOnScrollListener());
        lastItem ="0";
        list.clear();
        LoadDialog.show(context);
        atm.request(GETSHOWFIRSTLIST,this);
    }

    //加载数据
    public void loadData() {

        if(isFirstLoad) {
            String Cache = aCache.getAsString("ShowFirstList");
            NLog.e("ShowFirstCacheString",Cache);

            if(Cache!=null && !("null").equals(Cache))
                try {
                    List<ClassListResponse.DataBean> listCache = JsonMananger.jsonToList(Cache, ClassListResponse.DataBean.class);
                    NLog.e("ShowFirstCacheString",listCache);

                    if(!TextUtils.isEmpty(listCache.get(0).getResource())) {
                        list.addAll(listCache);
                        dataAdapter.notifyDataSetChanged();
                    }

                } catch (HttpException e) {
                    e.printStackTrace();
                    NToast.longToast(context,"ShowFirstCacheString:"+Cache);
                    NToast.longToast(context,"ShowFirstCacheString:"+Cache);
                    NToast.longToast(context,"ShowFirstCacheString:"+Cache);
                }
        }
        lastItem ="0";
        list.clear();
        LoadDialog.show(context);
        atm.request(GETSHOWFIRSTLIST,this);
    }

    @NonNull
    private GSYVideoPlayerOnScrollListener getGsyVideoPlayerOnScrollListener() {
        return new GSYVideoPlayerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                if(currentPage>1) {
                    LoadDialog.show(context);
                    atm.request(GETSHOWFIRSTLIST,ShowFirstPresenter.this);
                }

            }
        };
    }
}