package com.xtdar.app.presenter;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import com.xtdar.app.R;
import com.xtdar.app.XtdConst;
import com.xtdar.app.adapter.HomeRecommendAdapter;
import com.xtdar.app.adapter.RecyclerViewAdapter;
import com.xtdar.app.common.json.JsonMananger;
import com.xtdar.app.model.UserList;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.async.OnDataListener;
import com.xtdar.app.server.response.AdResponse;
import com.xtdar.app.server.response.RecommendResponse;
import com.xtdar.app.server.response.TagResponse;
import com.xtdar.app.view.widget.LoadDialog;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class HomeRecommendPresenter extends BasePresenter implements OnDataListener {
    private static final int GETADS = 1;
    private static final int GETRECOMMEND = 2;
    private Banner Banner;
    private RecyclerView recycleView;
    private GridLayoutManager gridLayoutManager;
    private HomeRecommendAdapter dataAdapter;
    private List<RecommendResponse.DataBean.RecommendListBean> list;

    public HomeRecommendPresenter(Context context){
        super(context);
        //mActivity = (ContactsActivity) context;
    }

    public void init(Banner banner, RecyclerView recycleView) {
        this.Banner=banner;
        this.recycleView=recycleView;

        gridLayoutManager=new GridLayoutManager(mContext,1);
        recycleView.setLayoutManager(gridLayoutManager);
        //取缓存
        String RecommendListCache = aCache.getAsString("RecommendList");
        if(RecommendListCache!=null && !("null").equals(RecommendListCache))
            try {
                list = JsonMananger.jsonToList(RecommendListCache, RecommendResponse.DataBean.RecommendListBean.class);
            } catch (HttpException e) {
                e.printStackTrace();
            }


        //设置列表
        dataAdapter = new HomeRecommendAdapter(list, mContext);
        //dataAdapter.setFooterView(LayoutInflater.from(mContext).inflate(R.layout.recyclerview_footer,null));
        recycleView.setAdapter(dataAdapter);
        recycleView.setNestedScrollingEnabled(false);

        LoadDialog.show(mContext);
        atm.request(GETADS,this);
    }

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        switch (requestCode) {
            case GETADS:
                return mUserAction.getAds();
            case GETRECOMMEND:
                return mUserAction.getRecommends();
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
                    Banner.setImages(images);//设置图片集合
                    Banner.start();
                    atm.request(GETRECOMMEND,this);
                }
                break;
            case GETRECOMMEND:
                RecommendResponse recommendResponse = (RecommendResponse) result;
                if (recommendResponse.getCode() == XtdConst.SUCCESS) {
                    list=recommendResponse.getData().getRecommend_list();
                    try {
                        String cache=JsonMananger.beanToJson(list);
                        aCache.put("RecommendList",cache);
                    } catch (HttpException e) {
                        e.printStackTrace();
                    }
                    dataAdapter.notifyDataSetChanged();
                }
                break;
        }
    }
}