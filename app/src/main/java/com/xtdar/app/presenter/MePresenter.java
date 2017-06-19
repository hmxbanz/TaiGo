package com.xtdar.app.presenter;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.xtdar.app.XtdConst;
import com.xtdar.app.common.PhotoUtils;
import com.xtdar.app.loader.GlideImageLoader;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.response.UserInfoResponse;
import com.xtdar.app.view.activity.MeActivity;
import com.xtdar.app.view.activity.SettingActivity;
import com.xtdar.app.view.widget.BottomMenuDialog;
import com.xtdar.app.view.widget.LoadDialog;
import com.xtdar.app.view.widget.SelectableRoundedImageView;


/**
 * Created by hmxbanz on 2017/4/5.
 */
public class MePresenter extends BasePresenter {
    private static final int GETINFO = 1;
    MeActivity mActivity;
    private TextView nickName;
    private SelectableRoundedImageView avator;
    private GlideImageLoader glideImageLoader;
    private BottomMenuDialog dialog;
    private PhotoUtils photoUtils;
    private Uri selectUri;
    public static final int REQUEST_CODE_ASK_PERMISSIONS = 101;

    public MePresenter(Context context) {
        super(context);
        this.mActivity =(MeActivity)context;
        glideImageLoader=new GlideImageLoader();
        setPortraitChangeListener();

    }

    public void init(SelectableRoundedImageView selectableRoundedImageView, TextView nickName) {
        this.avator=selectableRoundedImageView;
        this.nickName=nickName;
        //mView.initData();
        atm.request(GETINFO, this);
    }

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        return mUserAction.getInfo();
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        UserInfoResponse userInfoResponse=(UserInfoResponse)result;
        if (userInfoResponse.getCode() == XtdConst.SUCCESS) {
            UserInfoResponse.ResultEntity entity=userInfoResponse.getData();
            glideImageLoader.displayImage(mContext,XtdConst.SERVERURI+entity.getImg_path(),this.avator);
            this.nickName.setText(entity.getNick_name());
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PhotoUtils.INTENT_CROP:
            case PhotoUtils.INTENT_TAKE:
            case PhotoUtils.INTENT_SELECT:
                photoUtils.onActivityResult(mActivity, requestCode, resultCode, data);
                break;
        }
    }

    private void setPortraitChangeListener() {
        photoUtils = new PhotoUtils(new PhotoUtils.OnPhotoResultListener() {
            @Override
            public void onPhotoResult(Uri uri) {
                if (uri != null && !TextUtils.isEmpty(uri.getPath())) {
                    selectUri = uri;
                    LoadDialog.show(mContext);
                    //request(GET_QI_NIU_TOKEN);
                }
            }

            @Override
            public void onPhotoCancel() {

            }
        });
    }

    /**
     * 弹出底部框
     */
    @TargetApi(23)
    public void showPhotoDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }

        dialog = new BottomMenuDialog(mContext);
        dialog.setConfirmListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                if (Build.VERSION.SDK_INT >= 23) {
                    int checkPermission = mContext.checkSelfPermission(Manifest.permission.CAMERA);
                    if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                        if (mActivity.shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                            mActivity.requestPermissions(new String[] {Manifest.permission.CAMERA}, REQUEST_CODE_ASK_PERMISSIONS);
                        } else {
                            new AlertDialog.Builder(mActivity)
                                    .setMessage("您需要在设置里打开相机权限。")
                                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            mActivity.requestPermissions(new String[] {Manifest.permission.CAMERA}, REQUEST_CODE_ASK_PERMISSIONS);
                                        }
                                    })
                                    .setNegativeButton("取消", null)
                                    .create().show();
                        }
                        return;
                    }
                }
                photoUtils.takePicture(mActivity);
            }
        });
        dialog.setMiddleListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                photoUtils.selectPicture(mActivity);
            }
        });
        dialog.show();
    }
}
