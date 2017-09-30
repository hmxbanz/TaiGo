package com.xtdar.app.listener;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.GSYVideoPlayer;
import com.xtdar.app.common.NLog;
import com.xtdar.app.video.RecyclerItemNormalHolder;

/**
 * Created by PVer on 2017/6/12.
 */

public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {
    private int previousTotal = 0;
    private boolean loading = false;
    int firstVisibleItem,lastVisibleItem, visibleItemCount, totalItemCount;

    private int currentPage = 1;
    //是否可加载更多
    private boolean canloadMore = true;

    private LinearLayoutManager mLinearLayoutManager;

    public EndlessRecyclerOnScrollListener(
            LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);

        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mLinearLayoutManager.getItemCount();
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
        lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();

//        NLog.d("滑动可见",visibleItemCount);
//        NLog.d("滑动总数",totalItemCount);
//        NLog.d("滑动最后可见位置", lastVisibleItem);
        if (newState == RecyclerView.SCROLL_STATE_IDLE && !loading) {

            if (visibleItemCount > 0  && lastVisibleItem >= totalItemCount - 1 && canloadMore){
                currentPage++;
                onLoadMore(currentPage);
                loading = true;
            }


        }

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

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
//
//
//            if (totalItemCount > previousTotal) {
//                loading = true;
//                previousTotal = totalItemCount;
//            }
//
//        if (!loading   && (totalItemCount - visibleItemCount) <= lastVisibleItem) {
//            currentPage++;
//            onLoadMore(currentPage);
//            loading = true;
//        }
    }

    //设置是否可加载更多
    public void setCanloadMore(boolean flag) {
        loading=false;
        canloadMore = flag;
    }
    //设置是否可加载更多
    public void reset() {
        visibleItemCount =0;
        totalItemCount = 0;
        lastVisibleItem = 0;
        currentPage=1;
    }
    public abstract void onLoadMore(int currentPage);
}
