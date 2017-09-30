package com.xtdar.app.presenter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.widget.Toast;


import com.orhanobut.logger.Logger;
import com.xtdar.app.R;
import com.xtdar.app.XtdConst;
import com.xtdar.app.common.NToast;
import com.xtdar.app.listener.AlertDialogCallback;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.broadcast.BroadcastManager;
import com.xtdar.app.server.response.LoginResponse;
import com.xtdar.app.server.response.VersionResponse;
import com.xtdar.app.view.activity.LoginActivity;
import com.xtdar.app.view.activity.Main2Activity;
import com.xtdar.app.view.widget.LoadDialog;
import com.xtdar.app.widget.DialogWithYesOrNoUtils;
import com.xtdar.app.widget.downloadService.DownloadService;
import com.xtdar.app.widget.permissionLibrary.PermissionsManager;
import com.xtdar.app.widget.permissionLibrary.PermissionsResultAction;

import cn.jpush.android.api.JPushInterface;

import static com.xtdar.app.common.CommonTools.getVersionInfo;

/**
 * Created by hmxbanz on 2017/4/5.
 */
public class Main2Presenter extends BasePresenter {
    public static final int AUTOLOGIN = 1;
    private static final int CHECKVERSION = 2;
    private final BasePresenter basePresenter;
    private Main2Activity activity;

    public Main2Presenter(Context context){
        super(context);
        activity = (Main2Activity) context;
        basePresenter = BasePresenter.getInstance(context);
    }

    public void init() {
        LoadDialog.show(activity);
        atm.request(CHECKVERSION,this);
        String[] Permissions=new String[]{Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        //权限申请
        PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(activity,
                Permissions,
                new PermissionsResultAction() {
                    @Override
                    public void onGranted() {

                    }

                    @Override
                    public void onDenied(String permission) {
                        Toast.makeText(context, "获取权限失败，请点击后允许获取", Toast.LENGTH_SHORT).show();
                    }
                }, true);
    }

    public void onMeClick(final ViewPager viewPager) {
        basePresenter.initData();
        if(!basePresenter.isLogin){
            DialogWithYesOrNoUtils.getInstance().showDialog(context, "请先登录", null,"前住登录", new AlertDialogCallback() {
                @Override
                public void executeEvent() {
                    activity.startActivity(new Intent(activity, LoginActivity.class));
                }

            });
        }
        else {
            viewPager.setCurrentItem(3);
        }

    }
    @Override
    public Object doInBackground(int requestCode, String id) throws HttpException {
        switch (requestCode) {
            case AUTOLOGIN:
                return mUserAction.login( userName, password);
            case CHECKVERSION:
                return mUserAction.checkVersion();
        }
        return null;
    }
    @Override
    public void onSuccess(int requestCode, Object result) {
        LoadDialog.dismiss(context);
        if (result==null)return;
        switch (requestCode) {
            case AUTOLOGIN:
                LoginResponse loginResponse = (LoginResponse) result;
                if (loginResponse.getCode() == XtdConst.SUCCESS) {
                    LoginResponse.ResultEntity entity=loginResponse.getData();
                    loginWork(entity.getAccess_key());
                    NToast.shortToast(activity, "登录成功");
                } else {
                    NToast.shortToast(activity, "登录："+loginResponse.getMsg());
                }
                break;
            case CHECKVERSION:
                VersionResponse versionResponse = (VersionResponse) result;
                if (versionResponse.getState() == XtdConst.SUCCESS) {
                    final VersionResponse.ResultEntity entity=versionResponse.getAndroid();
                    String[] versionInfo = getVersionInfo(activity);
                    int versionCode = Integer.parseInt(versionInfo[0]);
                    if(entity.getVersionCode()>versionCode)
                    {
                        DialogWithYesOrNoUtils dialog=DialogWithYesOrNoUtils.getInstance();
                        dialog.showDialog(activity, "发现新版本:"+entity.getVersionName(), null,"立即更新",new AlertDialogCallback() {
                            @Override
                            public void executeEvent() {
                                goToDownload(entity.getDownloadUrl());
                            }


                        });
                        dialog.setContent(entity.getVersionInfo());
                    }
                    NToast.shortToast(activity, "版本检测成功");

                }else {
                    NToast.shortToast(activity, "版本检测："+versionResponse.getMsg());
                }
                break;
        }

    }

    public void autoLogin()
    {
        if(!TextUtils.isEmpty(userName)){
            LoadDialog.show(activity);
            atm.request(AUTOLOGIN,this);
        }
    }

    public void onDestroy() {
        activity.editor.putBoolean(XtdConst.ISLOGIN, false);//退出改登录标记
        activity.editor.commit();
        basePresenter.initData();
    }

    private void goToDownload(final String apkUrl) {
        //权限申请
        PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(activity,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionsResultAction() {
                    @Override
                    public void onGranted() {
                        Intent intent=new Intent(activity,DownloadService.class);
                        intent.putExtra("url", apkUrl);
                        activity.startService(intent);
                    }

                    @Override
                    public void onDenied(String permission) {
                        Toast.makeText(context, "获取权限失败，请点击后允许获取", Toast.LENGTH_SHORT).show();
                    }
                }, true);

    }

}
