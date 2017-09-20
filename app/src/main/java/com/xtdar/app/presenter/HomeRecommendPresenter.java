package com.xtdar.app.presenter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xtdar.app.XtdConst;
import com.xtdar.app.adapter.HomeRecommendAdapter;
import com.xtdar.app.common.json.JsonMananger;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.async.OnDataListener;
import com.xtdar.app.server.response.AdResponse;
import com.xtdar.app.server.response.TaobaoResponse;
import com.xtdar.app.view.widget.LoadDialog;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class HomeRecommendPresenter extends BasePresenter implements OnDataListener {
    private static final int GETADS = 1;
    private static final int GETRECOMMEND = 2;
    private Banner banner;
    private RecyclerView recycleView;
    private GridLayoutManager gridLayoutManager;
    private HomeRecommendAdapter dataAdapter;
    private List<TaobaoResponse.DataBean.DeviceTypeListBean> list=new ArrayList<>();

    public HomeRecommendPresenter(Context context){
        super(context);
        //mActivity = (ContactsActivity) context;
    }

    public void init(Banner banner, RecyclerView recycleView) {
        this.banner =banner;
        this.recycleView=recycleView;

        gridLayoutManager=new GridLayoutManager(context,1);
        recycleView.setLayoutManager(gridLayoutManager);

        //设置列表
        dataAdapter = new HomeRecommendAdapter(list, context);
        //dataAdapter.setFooterView(LayoutInflater.from(context).inflate(R.layout.recyclerview_footer,null));
        recycleView.setAdapter(dataAdapter);
        recycleView.setNestedScrollingEnabled(false);


        LoadDialog.show(context);
        //atm.request(GETADS,this);

    }

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        switch (requestCode) {
            case GETADS:
                return mUserAction.getAds();
            case GETRECOMMEND:
                return mUserAction.getTaobao();
        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        LoadDialog.dismiss(context);
        if (result==null)return;
        switch (requestCode) {
            case GETADS:
                AdResponse adResponse = (AdResponse) result;
                if (adResponse.getCode() == XtdConst.SUCCESS) {
                }
                break;
            case GETRECOMMEND:
                TaobaoResponse taobaoResponse = (TaobaoResponse) result;
                if (taobaoResponse.getCode() == XtdConst.SUCCESS) {
                    try {
                        String cache=JsonMananger.beanToJson(taobaoResponse.getData());
                        aCache.put("RecommendList",cache);
                    } catch (HttpException e) {
                        e.printStackTrace();
                    }
                    list.clear();
                    list.addAll(taobaoResponse.getData().getDevice_type_list());
                    dataAdapter.notifyDataSetChanged();

                    List<String> images = new ArrayList<>();
                    for (String s : taobaoResponse.getData().getImg_list()) {
                        s=XtdConst.IMGURI+s;
                        images.add(s);
                    }

                    banner.setImages(images);//设置图片集合
                    banner.start();
                }
                break;
        }
    }

    //加载数据
    public void loadData() {
        //取缓存
            String Cache = aCache.getAsString("RecommendList");
            if(Cache!=null && !("null").equals(Cache))
                try {
                    TaobaoResponse.DataBean listCache = JsonMananger.jsonToBean(Cache, TaobaoResponse.DataBean.class);
                    if(listCache.getDevice_type_list()!=null)
                    list.addAll(listCache.getDevice_type_list());
                    dataAdapter.notifyDataSetChanged();

                    List<String> images = new ArrayList<>();
                    if(listCache.getDevice_type_list()!=null)
                    for (String s : listCache.getImg_list()) {
                        s=XtdConst.IMGURI+s;
                        images.add(s);
                    }
                    banner.setImages(images);//设置图片集合
                    banner.start();

                } catch (HttpException e) {
                    e.printStackTrace();
                }

        LoadDialog.show(context);
        atm.request(GETRECOMMEND,this);
    }
}