package com.xtdar.app.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;

import com.xtdar.app.XtdConst;
import com.xtdar.app.common.NToast;
import com.xtdar.app.listener.AlertDialogCallback;
import com.xtdar.app.view.activity.LoginActivity;
import com.xtdar.app.view.activity.SettingActivity;
import com.xtdar.app.widget.DialogWithYesOrNoUtils;

import java.io.File;


/**
 * Created by hmxbanz on 2017/4/5.
 */

public class SettingPresenter extends BasePresenter {
    Context mContext;
    SettingActivity mActivity;

    public SettingPresenter(Context context) {
        //this.mActivity =(SettingActivity)context;
        super(context);
        this.mContext=context;
    }
    public void init(){
    //mView.initData();
    };
public void logOff()
{
    DialogWithYesOrNoUtils.getInstance().showDialog(context, "确定要退出TaiGo吗?",null, new AlertDialogCallback() {
        @Override
        public void executeEvent() {
            //File file = new File(Environment.getExternalStorageDirectory().getPath() + getPackageName());
            //deleteFile(file);
            editor.putBoolean(XtdConst.ISLOGIN, false);//退出改登录标记
            editor.commit();
            NToast.shortToast(context, "退出成功");
            context.startActivity(new Intent(context, LoginActivity.class));
        }

    });

}

}
