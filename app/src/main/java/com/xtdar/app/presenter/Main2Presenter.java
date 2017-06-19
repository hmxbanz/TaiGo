package com.xtdar.app.presenter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.widget.Toast;


import com.xtdar.app.R;
import com.xtdar.app.XtdConst;
import com.xtdar.app.common.NToast;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.response.LoginResponse;
import com.xtdar.app.server.response.VersionResponse;
import com.xtdar.app.view.activity.LoginActivity;
import com.xtdar.app.view.activity.Main2Activity;
import com.xtdar.app.view.widget.LoadDialog;
import com.xtdar.app.widget.DialogWithYesOrNoUtils;
import com.xtdar.app.widget.downloadService.DownloadService;
import com.xtdar.app.widget.permissionLibrary.PermissionsManager;
import com.xtdar.app.widget.permissionLibrary.PermissionsResultAction;

import static com.xtdar.app.common.CommonTools.getVersionInfo;

/**
 * Created by hmxbanz on 2017/4/5.
 */
public class Main2Presenter extends BasePresenter {
    private static final int AUTOLOGIN = 1;
    private static final int CHECKVERSION = 2;
    private Main2Activity mActivity;
    private String userName;
    private String password;

    public Main2Presenter(Context context){
        super(context);
        mActivity = (Main2Activity) context;
    }

    public void init() {
        userName = mActivity.sp.getString(XtdConst.LOGIN_USERNAME, "");
        password = mActivity.sp.getString(XtdConst.LOGING_PASSWORD, "");
        if(!TextUtils.isEmpty(userName) && !isLogin){
            LoadDialog.show(mActivity);
            atm.request(AUTOLOGIN,this);
        }
        atm.request(CHECKVERSION,this);
    }

    public void onMeClick(final ViewPager viewPager) {
        isLogin = sp.getBoolean(XtdConst.ISLOGIN, false);
        if(!isLogin){
            DialogWithYesOrNoUtils.getInstance().showDialog(mActivity, "请先登录", "前住登录", new DialogWithYesOrNoUtils.DialogCallBack() {
                @Override
                public void executeEvent() {
                    viewPager.setCurrentItem(1, false);
                    mActivity.startActivity(new Intent(mActivity, LoginActivity.class));
                }

                @Override
                public void onCancle() {
                    viewPager.setCurrentItem(1, false);
                }
            });
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
        if (result != null) {
            switch (requestCode) {
                case AUTOLOGIN:
                    LoginResponse loginResponse = (LoginResponse) result;
                    if (loginResponse.getCode() == XtdConst.SUCCESS) {
                        LoginResponse.ResultEntity entity=loginResponse.getData();
                        mActivity.editor.putBoolean(XtdConst.ISLOGIN, true);
                        mActivity.editor.apply();
                        LoadDialog.dismiss(mActivity);
                        NToast.shortToast(mActivity, "登录成功");
                    } else if (loginResponse.getCode() == XtdConst.FAILURE) {

                    }
                    break;
                case CHECKVERSION:
                    VersionResponse versionResponse = (VersionResponse) result;
                    if (versionResponse.getState() == XtdConst.SUCCESS) {
                        final VersionResponse.ResultEntity entity=versionResponse.getAndroid();
                        String[] versionInfo = getVersionInfo(mActivity);
                        int versionCode = Integer.parseInt(versionInfo[0]);
                        if(entity.getVersionCode()>versionCode)
                        {
                            DialogWithYesOrNoUtils.getInstance().showDialog(mActivity, "发现新版本:"+versionInfo[1], "立即更新",new DialogWithYesOrNoUtils.DialogCallBack() {
                                @Override
                                public void executeEvent() {
                                    goToDownload(entity.getDownloadUrl());
                                }

                                @Override
                                public void onCancle() {

                                }

                            });
                        }
                        LoadDialog.dismiss(mActivity);
                        NToast.shortToast(mActivity, "版本检测成功");
                    } else if (versionResponse.getState() == XtdConst.FAILURE) {

                    }
                    break;
            }
        }
    }
    @Override
    public void onFailure(int requestCode, int state, Object result) {
        super.onFailure(requestCode, state, result);
        switch (requestCode) {
            case AUTOLOGIN:
                LoadDialog.dismiss(mActivity);
                NToast.shortToast(mActivity, mActivity.getString(R.string.login_api_fail));
                break;
        }
    }

    public void onDestroy() {
        mActivity.editor.putBoolean(XtdConst.ISLOGIN, false);//退出改登录标记
        mActivity.editor.commit();
    }

    private void goToDownload(final String apkUrl) {
        //权限申请
        PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(mActivity,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionsResultAction() {
                    @Override
                    public void onGranted() {
                        Intent intent=new Intent(mActivity,DownloadService.class);
                        intent.putExtra("url", apkUrl);
                        mActivity.startService(intent);

                    }

                    @Override
                    public void onDenied(String permission) {
                        Toast.makeText(mContext, "获取权限失败，请点击后允许获取", Toast.LENGTH_SHORT).show();
                    }
                }, true);



    }
}
