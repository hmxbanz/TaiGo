package com.xtdar.app.presenter;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.GSYVideoPlayer;
import com.xtdar.app.XtdConst;
import com.xtdar.app.adapter.ClassListAnimationAdapter;
import com.xtdar.app.adapter.ClassListNuAdapter;
import com.xtdar.app.listener.GSYVideoPlayerOnScrollListener;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.async.OnDataListener;
import com.xtdar.app.server.response.ClassListResponse;
import com.xtdar.app.server.response.GameListResponse;
import com.xtdar.app.server.response.ShowResponse;
import com.xtdar.app.video.RecyclerItemNormalHolder;
import com.xtdar.app.view.activity.DetailActivity;
import com.xtdar.app.view.widget.LoadDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class HomeNuPresenter extends BasePresenter implements OnDataListener,ClassListNuAdapter.ItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    private static final int GETSHOWLIST = 1;
    private final LinearLayoutManager linearLayoutManager;
    private List<ShowResponse.DataBean> entity;
    private RecyclerView videoList;
    private String lastItem ="0";
    private ClassListNuAdapter dataAdapter;
    List<ClassListResponse.DataBean> datas = new ArrayList<>();
    private SwipeRefreshLayout swiper;

    //private ContactsActivity mActivity;
    public HomeNuPresenter(Context context){
        super(context);
        //mActivity = (ContactsActivity) context;
        linearLayoutManager = new LinearLayoutManager(context);
        dataAdapter = new ClassListNuAdapter(context,datas);
    }

    public void init(SwipeRefreshLayout swiper,RecyclerView videoList) {
        this.swiper=swiper;
        this.swiper.setOnRefreshListener(this);
        this.videoList=videoList;
        this.videoList.setAdapter(dataAdapter);
        videoList.setLayoutManager(linearLayoutManager);
        videoList.addOnScrollListener(new GSYVideoPlayerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                LoadDialog.show(context);
                atm.request(GETSHOWLIST,HomeNuPresenter.this);
            }
        });


        LoadDialog.show(context);
        atm.request(GETSHOWLIST,this);
    }

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        switch (requestCode) {
            case GETSHOWLIST:
                return mUserAction.getAnimations("1",lastItem,"4");
        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        LoadDialog.dismiss(context);
        this.swiper.setRefreshing(false);
        switch (requestCode) {
            case GETSHOWLIST:
                ClassListResponse response = (ClassListResponse) result;
                if (response.getCode() == XtdConst.SUCCESS) {
                    datas.addAll(response.getData());
                    lastItem=datas.get(0).getItem_id();
                    dataAdapter.setOnItemClickListener(this);
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
        lastItem ="0";
        datas.clear();
        videoList.addOnScrollListener(new GSYVideoPlayerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                LoadDialog.show(context);
                atm.request(GETSHOWLIST,HomeNuPresenter.this);
            }
        });
        atm.request(GETSHOWLIST,this);
    }
}