package com.xtdar.app.presenter;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.clj.fastble.data.ScanResult;
import com.xtdar.app.XtdConst;
import com.xtdar.app.adapter.MyDevicesAdapter;
import com.xtdar.app.common.NToast;
import com.xtdar.app.listener.AlertDialogCallback;
import com.xtdar.app.loader.GlideImageLoader;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.async.OnDataListener;
import com.xtdar.app.server.response.MyDevicesResponse;
import com.xtdar.app.server.response.UnReadMsgResponse;
import com.xtdar.app.server.response.UserInfoResponse;
import com.xtdar.app.service.BluetoothService;
import com.xtdar.app.view.activity.BleActivity;
import com.xtdar.app.view.activity.LoginActivity;
import com.xtdar.app.view.activity.QrCodeActivity;
import com.xtdar.app.view.activity.UnityPlayerActivity;
import com.xtdar.app.view.widget.DragPointView;
import com.xtdar.app.view.widget.LoadDialog;
import com.xtdar.app.view.widget.SelectableRoundedImageView;
import com.xtdar.app.widget.DialogWithYesOrNoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class MinePresenter extends BasePresenter implements OnDataListener {
    private static final int GETINFO = 2;
    private static final int GETMSGCOUNT = 3;
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
                    LoadDialog.show(context);
                    atm.request(GETMSGCOUNT,this);
                }
                NToast.shortToast(context, userInfoResponse.getMsg());
                break;
            case GETMSGCOUNT:
                UnReadMsgResponse unReadMsgResponse = (UnReadMsgResponse) result;
                if (unReadMsgResponse.getCode() == XtdConst.SUCCESS) {
                    UnReadMsgResponse.DataBean entity = unReadMsgResponse.getData();
                    if(entity.getMsg_count()>0){
                    this.unreadNumView.setText(entity.getMsg_count());
                    this.unreadNumView.setVisibility(View.VISIBLE);
                    }
                }
                NToast.shortToast(context, unReadMsgResponse.getMsg());
                break;

        }
    }



}