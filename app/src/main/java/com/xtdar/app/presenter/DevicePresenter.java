package com.xtdar.app.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.xtdar.app.R;
import com.xtdar.app.adapter.RecyclerViewAdapterForShop;
import com.xtdar.app.model.CategoryList;
import com.xtdar.app.model.DeviceList;
import com.xtdar.app.view.activity.CategoryActivity;
import com.xtdar.app.view.activity.DeviceActivity;
import com.xtdar.app.view.activity.SettingActivity;


/**
 * Created by hmxbanz on 2017/4/5.
 */

public class DevicePresenter {
    Context mContext;
    DeviceActivity mActivity;

    public DevicePresenter(Context context) {
        this.mActivity =(DeviceActivity)context;
        this.mContext=context;
    }
    public void init(){
        //头部
        RecyclerView recycleViewTop= (RecyclerView) mActivity.findViewById(R.id.shop_recyclerview_top);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(mContext,4);
        recycleViewTop.setLayoutManager(gridLayoutManager);
        RecyclerViewAdapterForShop dataAdapter2 = new RecyclerViewAdapterForShop(DeviceList.getData(), mContext);
        dataAdapter2.setOnItemClickListener(new RecyclerViewAdapterForShop.ItemClickListener() {
            @Override
            public void onItemClick(int position, String data) {
                Toast.makeText(mContext, "位置在："+String.valueOf(position), Toast.LENGTH_LONG).show();
            }
        });
        dataAdapter2.setFooterView(LayoutInflater.from(mContext).inflate(R.layout.recyclerview_device_footer,null));
        recycleViewTop.setAdapter(dataAdapter2);
        dataAdapter2.getFooterView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "点击添加", Toast.LENGTH_LONG).show();
            }
        });
        recycleViewTop.setNestedScrollingEnabled(false);
    //mView.initData();
};


}
