package com.xtdar.app.presenter;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xtdar.app.XtdConst;
import com.xtdar.app.common.NLog;
import com.xtdar.app.common.NToast;
import com.xtdar.app.common.json.JsonMananger;
import com.xtdar.app.listener.EndlessRecyclerOnScrollListener;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.async.OnDataListener;
import com.xtdar.app.server.response.NewsResponse;
import com.xtdar.app.video.NewsAdapter;
import com.xtdar.app.view.activity.Main2Activity;
import com.xtdar.app.view.activity.NewsDetailActivity;
import com.xtdar.app.view.widget.LoadDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class NewsPresenter extends BasePresenter implements OnDataListener,NewsAdapter.ItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    private static final int GETNEWSLIST = 1;
    private final LinearLayoutManager linearLayoutManager;
    private final NewsAdapter recyclerAdapter;
    private List<NewsResponse.DataBean> list =new ArrayList<>();
    private RecyclerView recyclerView;
    private String pageIndex ="0";
    private SwipeRefreshLayout swiper;
    private boolean isFirstLoad=true;
    private EndlessRecyclerOnScrollListener onScrollListener;
    private Main2Activity activity;

    public NewsPresenter(Context context){
        super(context);
        activity = (Main2Activity) context;
        linearLayoutManager = new LinearLayoutManager(context);
        recyclerAdapter = new NewsAdapter(context, list);
        recyclerAdapter.setOnItemClickListener(this);
    }

    public void init(SwipeRefreshLayout swiper, RecyclerView recyclerView) {
        onScrollListener=new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                pageIndex = String.valueOf(currentPage - 1);
                LoadDialog.show(context);
                atm.request(GETNEWSLIST,NewsPresenter.this);
            }
        };
        this.swiper=swiper;
        this.swiper.setOnRefreshListener(this);
        this.recyclerView =recyclerView;
        this.recyclerView.setAdapter(recyclerAdapter);
        this.recyclerView.setLayoutManager(linearLayoutManager);
        this.recyclerView.addOnScrollListener(onScrollListener);
        this.recyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        switch (requestCode) {
            case GETNEWSLIST:
                return mUserAction.getNewsList("12",pageIndex);
        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        LoadDialog.dismiss(context);
        if (result==null)return;
        this.swiper.setRefreshing(false);
        switch (requestCode) {
            case GETNEWSLIST:
                NewsResponse newsResponse=(NewsResponse)result;
                if (newsResponse.getCode() == XtdConst.SUCCESS && newsResponse.getData() != null && newsResponse.getData().size()>0) {
                    List<NewsResponse.DataBean> listTemp = newsResponse.getData();

                    if(isFirstLoad) {
                        list=newsResponse.getData();
                        isFirstLoad=false;
                        try {
                            String cache=JsonMananger.beanToJson(list);
                            aCache.put("NewsList",cache);
                        } catch (HttpException e) {
                            e.printStackTrace();
                        }
                    }
                    else
                        list.addAll(newsResponse.getData());

                    recyclerAdapter.setListData(list);
                }
                else {
                        NToast.shortToast(context, "资讯列表："+newsResponse.getMsg());
                }
                break;
        }
    }

    @Override
    public void onItemClick(int position, String itemId) {
        NewsDetailActivity.StartActivity(context,itemId);
    }

    @Override
    public void onRefresh() {
        pageIndex ="0";
        int length=list.size();
        list.clear();
        recyclerAdapter.notifyItemRangeRemoved(0,length);
        loadData();
    }

    //加载数据
    public void loadData() {
        onScrollListener.reset();
        onScrollListener.setCanloadMore(true);
        if(isFirstLoad) {
            String Cache = aCache.getAsString("NewsList");
            NLog.d("NewsCacheString",Cache);

//            if(Cache!=null && !("null").equals(Cache)){
//                try {
//                    List<NewsResponse.DataBean> listCache = JsonMananger.jsonToList(Cache, NewsResponse.DataBean.class);
//                    if(!TextUtils.isEmpty(listCache.get(0).getTitle())) {
//                        list.addAll(listCache);
//                        recyclerAdapter.notifyDataSetChanged();
//                    }
//                } catch (HttpException e) {
//                    e.printStackTrace();
//                    NToast.longToast(context,"NewsCacheString:"+Cache);
//                }
//            }

        }
        pageIndex ="0";
        LoadDialog.show(context);
        atm.request(GETNEWSLIST,this);
    }


}