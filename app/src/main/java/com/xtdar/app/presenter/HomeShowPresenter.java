package com.xtdar.app.presenter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.GSYVideoPlayer;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.async.OnDataListener;
import com.xtdar.app.server.response.ShowResponse;
import com.xtdar.app.video.RecyclerBaseAdapter;
import com.xtdar.app.video.RecyclerItemNormalHolder;
import com.xtdar.app.video.RecyclerNormalAdapter;
import com.xtdar.app.view.widget.LoadDialog;

import java.util.List;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class HomeShowPresenter extends BasePresenter implements OnDataListener {
    private static final int GETSHOWLIST = 1;
    private final LinearLayoutManager linearLayoutManager;
    private List<ShowResponse.DataBean> entity;
    private RecyclerView videoList;

    //private ContactsActivity mActivity;
    public HomeShowPresenter(Context context){
        super(context);
        //mActivity = (ContactsActivity) context;
        linearLayoutManager = new LinearLayoutManager(context);
    }

    public void init(RecyclerView videoList) {
        this.videoList=videoList;
        LoadDialog.show(mContext);

    atm.request(GETSHOWLIST,this);

        videoList.setLayoutManager(linearLayoutManager);
        videoList.addOnScrollListener(new RecyclerView.OnScrollListener() {

            int firstVisibleItem, lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                firstVisibleItem   = linearLayoutManager.findFirstVisibleItemPosition();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                //大于0说明有播放
                if (GSYVideoManager.instance().getPlayPosition() >= 0) {
                    //当前播放的位置
                    int position = GSYVideoManager.instance().getPlayPosition();
                    //对应的播放列表TAG
                    if (GSYVideoManager.instance().getPlayTag().equals(RecyclerItemNormalHolder.TAG)
                            && (position < firstVisibleItem || position > lastVisibleItem)) {

                        //如果滑出去了上面和下面就是否，和今日头条一样
                        GSYVideoPlayer.releaseAllVideos();
                        //recyclerNormalAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        switch (requestCode) {
            case GETSHOWLIST:
                return mUserAction.getShowList();
        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        switch (requestCode) {
            case GETSHOWLIST:
                ShowResponse showResponse=(ShowResponse)result;
                if (showResponse != null && showResponse.getData() != null) {
                    entity=showResponse.getData();
                    final RecyclerNormalAdapter recyclerNormalAdapter = new RecyclerNormalAdapter(mContext, entity);
                    this.videoList.setAdapter(recyclerNormalAdapter);
                }
//
                break;
        }
    }
}