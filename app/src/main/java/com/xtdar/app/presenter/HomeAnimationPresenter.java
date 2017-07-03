package com.xtdar.app.presenter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xtdar.app.XtdConst;
import com.xtdar.app.adapter.ClassListAnimationAdapter;
import com.xtdar.app.listener.EndlessRecyclerOnScrollListener;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.async.OnDataListener;
import com.xtdar.app.server.response.AdResponse;
import com.xtdar.app.server.response.ClassListResponse;
import com.xtdar.app.server.response.GameListResponse;
import com.xtdar.app.view.activity.DetailActivity;
import com.xtdar.app.view.widget.LoadDialog;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class HomeAnimationPresenter extends BasePresenter implements OnDataListener,ClassListAnimationAdapter.ItemClickListener {
    private static final int GETADS = 1;
    private static final int GETANIMATION = 2;
    private Banner Banner;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private ClassListAnimationAdapter dataAdapter;
    private List<GameListResponse.DataBean> list=new ArrayList<>();
    private String lastItem ="0";
    private int lastOffset;
    private int lastPosition;
    private boolean isAdapterSetted=false;

    public HomeAnimationPresenter(Context context){
        super(context);
        //mActivity = (ContactsActivity) context;
        dataAdapter = new ClassListAnimationAdapter(this.context);
    }

    public void init(RecyclerView recycleView) {
        this.recyclerView =recycleView;
        gridLayoutManager=new GridLayoutManager(context,1);
        recycleView.setLayoutManager(gridLayoutManager);
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                LoadDialog.show(context);
                //atm.request(GETANIMATION,HomeAnimationPresenter.this);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(recyclerView.getLayoutManager() != null) {
                    getPositionAndOffset();
                }
            }
        });
        recyclerView.setNestedScrollingEnabled(false);
        LoadDialog.show(context);
        //atm.request(GETADS,this);
        atm.request(GETANIMATION,this);

    }

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        switch (requestCode) {
            case GETADS:
                return mUserAction.getAds();
            case GETANIMATION:
                return mUserAction.getShot("0",lastItem,"2");
        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        LoadDialog.dismiss(context);
        switch (requestCode) {
            case GETADS:
                AdResponse adResponse = (AdResponse) result;
                if (adResponse.getCode() == XtdConst.SUCCESS) {
                    List<String> images = new ArrayList<>();
                    for (String s : adResponse.getData()) {
                        s=XtdConst.IMGURI+s;
                        images.add(s);
                    }
                    dataAdapter.setAdImages(images);
                    atm.request(GETANIMATION,this);
                }
                break;
            case GETANIMATION:
                GameListResponse response = (GameListResponse) result;
                if (response.getCode() == XtdConst.SUCCESS) {
                    final List<GameListResponse.DataBean> datas = response.getData();
                    lastItem=((GameListResponse.DataBean) datas.get(datas.size()-1)).getGame_id();
                    list.addAll(response.getData());
                    //设置列表
                    //dataAdapter.setHeaderView(LayoutInflater.from(context).inflate(R.layout.recyclerview_header,null));
                    dataAdapter.setListItems(list);
                    dataAdapter.setOnItemClickListener(this);
                    //dataAdapter.setFooterView(LayoutInflater.from(context).inflate(R.layout.recyclerview_footer,null));

                    if(!isAdapterSetted)
                        recyclerView.setAdapter(dataAdapter);
                    isAdapterSetted=true;
                    dataAdapter.notifyDataSetChanged();


                }
                break;
        }
    }


    @Override
    public void onItemClick(int position, String item_id,String class_id) {
        DetailActivity.StartActivity(context,item_id,class_id);
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
}