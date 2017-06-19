package com.xtdar.app.presenter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import com.xtdar.app.R;
import com.xtdar.app.XtdConst;
import com.xtdar.app.adapter.ClassListAnimationAdapter;
import com.xtdar.app.listener.EndlessRecyclerOnScrollListener;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.async.OnDataListener;
import com.xtdar.app.server.response.AdResponse;
import com.xtdar.app.server.response.ClassListResponse;
import com.xtdar.app.view.activity.DetailActivity;
import com.xtdar.app.view.activity.SongAlbumActivity;
import com.xtdar.app.view.widget.LoadDialog;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class HomeSongPresenter extends BasePresenter implements OnDataListener,ClassListAnimationAdapter.ItemClickListener {
    private static final int GETADS = 1;
    private static final int GETANIMATION = 2;
    private Banner Banner;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private ClassListAnimationAdapter dataAdapter;
    private List<ClassListResponse.DataBean> list=new ArrayList<>();
    private String lastItem ="0";

    public HomeSongPresenter(Context context){
        super(context);
        //mActivity = (ContactsActivity) context;
        dataAdapter = new ClassListAnimationAdapter(mContext);
    }

    public void init(RecyclerView recycleView) {
        this.recyclerView =recycleView;
        gridLayoutManager=new GridLayoutManager(mContext,2);
        recycleView.setLayoutManager(gridLayoutManager);
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                LoadDialog.show(mContext);
                atm.request(GETANIMATION,HomeSongPresenter.this);
            }
        });
        recyclerView.setNestedScrollingEnabled(false);
        LoadDialog.show(mContext);
        atm.request(GETADS,this);

    }

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        switch (requestCode) {
            case GETADS:
                return mUserAction.getAds();
            case GETANIMATION:
                return mUserAction.getAnimations("5",lastItem,"4");
        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        LoadDialog.dismiss(mContext);
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
                ClassListResponse classListResponse = (ClassListResponse) result;
                if (classListResponse.getCode() == XtdConst.SUCCESS) {
                    final List<ClassListResponse.DataBean> datas = classListResponse.getData();
                    lastItem=((ClassListResponse.DataBean) datas.get(datas.size()-1)).getItem_id();
                    list.addAll(classListResponse.getData());
                    //设置列表
                    dataAdapter.setHeaderView(LayoutInflater.from(mContext).inflate(R.layout.recyclerview_header,null));
                    dataAdapter.setListItems(list);
                    dataAdapter.setOnItemClickListener(this);
                    //dataAdapter.setFooterView(LayoutInflater.from(mContext).inflate(R.layout.recyclerview_footer,null));
                    recyclerView.setAdapter(dataAdapter);

                }
                break;
        }
    }


    @Override
    public void onItemClick(int position, String item_id,String class_id) {
        SongAlbumActivity.StartActivity(mContext,item_id);
    }
}