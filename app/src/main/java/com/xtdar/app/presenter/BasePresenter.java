package com.xtdar.app.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.xtdar.app.R;
import com.xtdar.app.XtdConst;
import com.xtdar.app.common.CommonTools;
import com.xtdar.app.common.NToast;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.UserAction;
import com.xtdar.app.server.async.AsyncTaskManager;
import com.xtdar.app.server.async.OnDataListener;
import com.xtdar.app.view.activity.BaseActivity;
import com.xtdar.app.view.widget.LoadDialog;
import com.xtdar.app.widget.ACache;

/**
 * Created by PVer on 2017/5/23.
 */

public class BasePresenter implements OnDataListener {
    protected final SharedPreferences sp;
    protected final SharedPreferences.Editor editor;
    public boolean isLogin;
    protected Context mContext;
    public UserAction mUserAction;
    public AsyncTaskManager atm ;
    protected String mUserInfoId;
    public ACache aCache;
    public BasePresenter(Context context)
    {
        this.mContext=context;
        atm= AsyncTaskManager.getInstance(context);
        mUserAction = UserAction.getInstance(context);
        aCache=ACache.get(context);
        sp = ((BaseActivity)mContext).sp;
        editor=sp.edit();
        mUserInfoId= sp.getString(XtdConst.USERINFOID,"0");
        isLogin = sp.getBoolean(XtdConst.ISLOGIN, false);
        mUserAction.token=GetToken();
    }
    protected String GetToken(){
        return sp.getString(XtdConst.ACCESS_TOKEN,"");
    }

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {

    }

    @Override
    public void onFailure(int requestCode, int state, Object result) {
        LoadDialog.dismiss(mContext);
        if (!CommonTools.isNetworkConnected(mContext)) {
            NToast.shortToast(mContext, mContext.getString(R.string.network_not_available));
            return;
        }

    }
}