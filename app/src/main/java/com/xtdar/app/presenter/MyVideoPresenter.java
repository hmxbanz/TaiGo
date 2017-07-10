package com.xtdar.app.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.GSYVideoPlayer;
import com.xtdar.app.Interface.IFavorView;
import com.xtdar.app.R;
import com.xtdar.app.adapter.FavorAdapter;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.async.OnDataListener;
import com.xtdar.app.server.response.ShowResponse;
import com.xtdar.app.video.RecyclerItemNormalHolder;
import com.xtdar.app.video.RecyclerNormalAdapter;
import com.xtdar.app.view.activity.FavorActivity;
import com.xtdar.app.view.activity.MyVideoActivity;
import com.xtdar.app.view.activity.ShowDetailActivity;
import com.xtdar.app.view.fragment.FavorMusicFragment;
import com.xtdar.app.view.fragment.FavorVideoFragment;
import com.xtdar.app.view.widget.LoadDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class MyVideoPresenter extends BasePresenter implements OnDataListener,RecyclerNormalAdapter.ItemClickListener {
    Context mContext;
    MyVideoActivity mActivity;
    private static final int GETSHOWLIST = 1;
    private final LinearLayoutManager linearLayoutManager;
    private List<ShowResponse.DataBean> entity;
    private RecyclerView videoList;

    public MyVideoPresenter(Context context) {
        super(context);
        mActivity= (MyVideoActivity) context;
        linearLayoutManager = new LinearLayoutManager(context);
    }

    public void init(RecyclerView videoList) {
        this.videoList=videoList;
        LoadDialog.show(context);

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
                return mUserAction.getMyVideo();
        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        LoadDialog.dismiss(context);
        switch (requestCode) {
            case GETSHOWLIST:
                ShowResponse showResponse=(ShowResponse)result;
                if (showResponse != null && showResponse.getData() != null) {
                    entity=showResponse.getData();
                    final RecyclerNormalAdapter recyclerNormalAdapter = new RecyclerNormalAdapter(context, entity);
                    recyclerNormalAdapter.setOnItemClickListener(this);
                    this.videoList.setAdapter(recyclerNormalAdapter);
                }
//
                break;
        }
    }

    @Override
    public void onItemClick(int position, String itemId, String classId) {
        Intent intent = new Intent(context, ShowDetailActivity.class);
        intent.putExtra("show_id", itemId);
        context.startActivity(intent);

    }
}
