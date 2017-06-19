package com.xtdar.app.presenter;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.xtdar.app.model.UserList;
import com.xtdar.app.Interface.IAlbumView;
import com.xtdar.app.R;
import com.xtdar.app.adapter.AlbumAdapter;

import com.xtdar.app.view.activity.AlbumActivity;


/**
 * Created by hmxbanz on 2017/4/5.
 */

public class AlbumPresenter {
    Context mContext;
    AlbumActivity mActivity;
    IAlbumView mView;
    AlbumAdapter albumAdapter;
    private RecyclerView recycleView;

    private GridLayoutManager gridLayoutManager;
    public AlbumPresenter(Context context) {
        this.mContext=context;
        this.mActivity =(AlbumActivity)context;
        this.mView=(IAlbumView)context;
        this.albumAdapter = new AlbumAdapter(UserList.getData(),mContext);
    }
    public void init(){
        recycleView= (RecyclerView) mActivity.findViewById(R.id.recyclerView);
        gridLayoutManager=new GridLayoutManager(mContext,3);
        recycleView.setLayoutManager(gridLayoutManager);
        albumAdapter = new AlbumAdapter(UserList.getData(), mContext);
        albumAdapter.setFooterView(LayoutInflater.from(mContext).inflate(R.layout.recyclerview_footer,null));
        recycleView.setAdapter(albumAdapter);
        recycleView.setNestedScrollingEnabled(false);
        if(Build.VERSION.SDK_INT>=23)
            recycleView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (gridLayoutManager.findLastCompletelyVisibleItemPosition()==(UserList.getData().size()-1))
                    {}
                }
            });
        //recycleView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));
        albumAdapter.setOnItemClickListener(mActivity);
    }

}
