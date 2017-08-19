package com.xtdar.app.presenter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.constants.AlibcConstants;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.model.TradeResult;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcDetailPage;
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
import com.xtdar.app.view.activity.Main2Activity;
import com.xtdar.app.view.activity.ShopMoreActivity;
import com.xtdar.app.view.widget.LoadDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
            public void onItemClick(int position, ShopMoreResponse.DataBean listItem) {
                LoadDialog.show(context);
                //商品详情page
                AlibcBasePage detailPage = new AlibcDetailPage(listItem.getTaobao_id());
                //提供给三方传递配置参数
                Map<String, String> exParams = new HashMap<>();
                exParams.put(AlibcConstants.ISV_CODE, "appisvcode");
                //设置页面打开方式
                AlibcShowParams showParams = new AlibcShowParams(OpenType.Native, false);

                //使用百川sdk提供默认的Activity打开detail
                AlibcTrade.show((ShopMoreActivity)context, detailPage, showParams, null, null ,
                        new AlibcTradeCallback() {
                            @Override
                            public void onTradeSuccess(TradeResult tradeResult) {
                                //打开电商组件，用户操作中成功信息回调。tradeResult：成功信息（结果类型：加购，支付；支付结果）
                                LoadDialog.dismiss(context);
                            }

                            @Override
                            public void onFailure(int code, String msg) {
                                //打开电商组件，用户操作中错误信息回调。code：错误码；msg：错误信息
                            }
                        });
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
