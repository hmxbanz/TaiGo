package com.xtdar.app.presenter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.xtdar.app.R;
import com.xtdar.app.XtdConst;
import com.xtdar.app.adapter.HomeRecommendItemAdapter;
import com.xtdar.app.adapter.RecyclerViewAdapterForShop;
import com.xtdar.app.adapter.ShopMoreAdapter;
import com.xtdar.app.common.NToast;
import com.xtdar.app.model.DeviceList;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.response.FavorResponse;
import com.xtdar.app.server.response.ShopMoreResponse;
import com.xtdar.app.server.response.TaobaoResponse;
import com.xtdar.app.view.activity.DeviceActivity;
import com.xtdar.app.view.activity.ShopMoreActivity;
import com.xtdar.app.view.widget.LoadDialog;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by hmxbanz on 2017/4/5.
 */

public class ShopMorePresenter extends BasePresenter {
    private static final int GETLIST = 1;
    ShopMoreActivity mActivity;
    private List<ShopMoreResponse.DataBean> list=new ArrayList<>();
    private String deviceTtypeId;
    private ShopMoreAdapter dataAdapter;

    public ShopMorePresenter(Context context) {
        super(context);
        this.mActivity =(ShopMoreActivity)context;
    }
    public void init(String deviceTtypeId, RecyclerView recyclerView){
        this.deviceTtypeId=deviceTtypeId;
        //装入item
        LoadDialog.show(context);
        atm.request(GETLIST,this);

        GridLayoutManager gridLayoutManager=new GridLayoutManager(context,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        dataAdapter = new ShopMoreAdapter(list, context);
        dataAdapter.setOnItemClickListener(new ShopMoreAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position, String itemId,String classId) {
                //DetailActivity.StartActivity(context,itemId,classId);
                NToast.shortToast(context,"暂无库存，敬请关注！");
            }
        });
        recyclerView.setAdapter(dataAdapter);
        //glideImageLoader.displayImage(context,listItem.getAvator(),holder.imageView);
        //holder.imageView.setImageResource(listItem.getImgResource());

};

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        switch (requestCode) {
            case GETLIST:
                return mUserAction.getShopMore(deviceTtypeId,"10","0");
        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        LoadDialog.dismiss(context);
        switch (requestCode) {
            case GETLIST:
                ShopMoreResponse shopMoreResponse = (ShopMoreResponse) result;
                if (shopMoreResponse != null && shopMoreResponse.getCode() == XtdConst.SUCCESS) {
                    list.addAll(shopMoreResponse.getData());
                    dataAdapter.notifyDataSetChanged();
                }
                break;
        }
    }
}
