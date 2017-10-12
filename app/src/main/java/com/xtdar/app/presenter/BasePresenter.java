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
import com.xtdar.app.server.broadcast.BroadcastManager;
import com.xtdar.app.view.activity.BaseActivity;
import com.xtdar.app.view.widget.LoadDialog;
import com.xtdar.app.widget.ACache;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by PVer on 2017/5/23.
 */

public class BasePresenter implements OnDataListener {
    private static BasePresenter instance;
    protected SharedPreferences sp;
    protected SharedPreferences.Editor editor;
    public boolean isLogin;
    protected Context context;
    public UserAction mUserAction;
    public AsyncTaskManager atm ;
    protected String mUserInfoId;
    public ACache aCache;
    protected String userName;
    protected String password;

    public String openId,loginType;

    public BasePresenter(Context context)
    {
        this.context =context;
        atm= AsyncTaskManager.getInstance(context);
        mUserAction = UserAction.getInstance(context);
        if(context != null){
            aCache = ACache.get(context);
            sp = this.context.getSharedPreferences("UserConfig", MODE_PRIVATE);
            editor = sp.edit();
            initData();
        }
    }
    public static BasePresenter getInstance(Context context) {
        if (instance == null) {
            synchronized (BasePresenter.class) {
                if (instance == null) {
                    instance = new BasePresenter(context);
                }
            }
        }
        return instance;
    }
    protected void initData()
    {
        mUserAction.token = sp.getString(XtdConst.ACCESS_TOKEN,"");
        isLogin = sp.getBoolean(XtdConst.ISLOGIN, false);
        userName=sp.getString(XtdConst.LOGIN_USERNAME,"");
        password=sp.getString(XtdConst.LOGING_PASSWORD,"");
        openId=sp.getString("openId","");
        loginType=sp.getString("loginType","");

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
        LoadDialog.dismiss(context);
        if (!CommonTools.isNetworkConnected(context)) {
            NToast.shortToast(context, context.getString(R.string.network_not_available));
            return;
        }

    }

    public void loginWork(String access_key)
    {
        editor.putString(XtdConst.ACCESS_TOKEN, access_key);
        editor.putBoolean(XtdConst.ISLOGIN, true);
        editor.apply();
        initData();
        BroadcastManager.getInstance(context).sendBroadcast(MinePresenter.UPDATEUNREAD, "loadAvator");
        BroadcastManager.getInstance(context).sendBroadcast(HomeFragmentPresenter.LOADDEVICE, "loadDevice");
    }

}