package com.xtdar.app.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.constants.AlibcConstants;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.model.TradeResult;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcMyCartsPage;
import com.alibaba.baichuan.android.trade.page.AlibcMyOrdersPage;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.xtdar.app.XtdConst;
import com.xtdar.app.common.NToast;
import com.xtdar.app.loader.GlideImageLoader;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.async.OnDataListener;
import com.xtdar.app.server.broadcast.BroadcastManager;
import com.xtdar.app.server.response.UnReadMsgResponse;
import com.xtdar.app.server.response.UserInfoResponse;
import com.xtdar.app.view.activity.Main2Activity;
import com.xtdar.app.view.widget.DragPointView;
import com.xtdar.app.view.widget.LoadDialog;
import com.xtdar.app.view.widget.SelectableRoundedImageView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class MinePresenter extends BasePresenter implements OnDataListener {
    private static final int GETINFO = 2;
    private static final int GETMSGCOUNT = 3;
    public static final String UPDATEUNREAD = "updateUnread";
    private GlideImageLoader glideImageLoader;
    private final BasePresenter basePresenter;
    private SelectableRoundedImageView avator;
    private TextView nickName;
    private DragPointView unreadNumView;

    public MinePresenter(Context context){
        super(context);
        basePresenter = BasePresenter.getInstance(context);
        glideImageLoader = new GlideImageLoader();
    }

    public void init(SelectableRoundedImageView selectableRoundedImageView, TextView nickName, DragPointView unreadNumView) {
        this.unreadNumView=unreadNumView;
        this.avator = selectableRoundedImageView;
        this.nickName = nickName;

        BroadcastManager.getInstance(context).addAction(MinePresenter.UPDATEUNREAD, new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String command = intent.getAction();
                String s=intent.getStringExtra("String");
                if (!TextUtils.isEmpty(command)) {
                    switch (s){
                        case "updateUnread":
                            reloadMsgCount();
                            break;
                        default:

                    }

                }
            }
        });

    }
    public void initData(){
        LoadDialog.show(context);
        atm.request(GETINFO,this);
    }

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        switch (requestCode) {
            case GETINFO:
                return mUserAction.getInfo();
            case GETMSGCOUNT:
                return mUserAction.getUnReadMsg();
        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        LoadDialog.dismiss(context);
        switch (requestCode) {
            case GETINFO:
                UserInfoResponse userInfoResponse = (UserInfoResponse) result;
                if (userInfoResponse.getCode() == XtdConst.SUCCESS) {
                    UserInfoResponse.ResultEntity entity = userInfoResponse.getData();
                    //glideImageLoader.displayImage(context, XtdConst.IMGURI+entity.getImg_path(), this.avator);
                    Glide.with(context).load(XtdConst.IMGURI+entity.getImg_path()).skipMemoryCache(true).diskCacheStrategy( DiskCacheStrategy.NONE ).into(this.avator);
                    this.nickName.setText(entity.getNick_name());
                    reloadMsgCount();
                }
                NToast.shortToast(context, userInfoResponse.getMsg());
                break;
            case GETMSGCOUNT:
                UnReadMsgResponse unReadMsgResponse = (UnReadMsgResponse) result;
                if (unReadMsgResponse.getCode() == XtdConst.SUCCESS) {
                    UnReadMsgResponse.DataBean entity = unReadMsgResponse.getData();
                    if(entity.getMsg_count()>0){
                    this.unreadNumView.setText(String.valueOf(entity.getMsg_count()));
                    this.unreadNumView.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        this.unreadNumView.setVisibility(View.GONE);
                    }
                }
                NToast.shortToast(context, unReadMsgResponse.getMsg());
                break;

        }
    }

    private void reloadMsgCount() {
        LoadDialog.show(context);
        atm.request(GETMSGCOUNT,this);
    }
    public void openShopCar() {
        LoadDialog.show(context);
        //提供给三方传递配置参数
        Map<String, String> exParams = new HashMap<>();
        exParams.put(AlibcConstants.ISV_CODE, "appisvcode");
        //实例化我的购物车打开page
        AlibcBasePage myCartsPage = new AlibcMyCartsPage();
        //设置页面打开方式
        AlibcShowParams showParams = new AlibcShowParams(OpenType.Native, false);

        //使用百川sdk提供默认的Activity打开detail
        AlibcTrade.show((Main2Activity)context, myCartsPage, showParams, null, null ,
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

    public void openOrder() {
        LoadDialog.show(context);
        //实例化我的订单打开page
        AlibcBasePage ordersPage = new AlibcMyOrdersPage(0, true);

        //提供给三方传递配置参数
        Map<String, String> exParams = new HashMap<>();
        exParams.put(AlibcConstants.ISV_CODE, "appisvcode");
        //实例化我的购物车打开page
        AlibcBasePage myCartsPage = new AlibcMyCartsPage();
        //设置页面打开方式
        AlibcShowParams showParams = new AlibcShowParams(OpenType.Native, false);

        //使用百川sdk提供默认的Activity打开detail
        AlibcTrade.show((Main2Activity)context, ordersPage, showParams, null, null ,
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
}