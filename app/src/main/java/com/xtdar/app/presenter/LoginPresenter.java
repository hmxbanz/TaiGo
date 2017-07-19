package com.xtdar.app.presenter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mob.tools.utils.UIHandler;
import com.xtdar.app.R;
import com.xtdar.app.XtdConst;
import com.xtdar.app.common.NToast;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.response.LoginResponse;
import com.xtdar.app.view.activity.LoginActivity;
import com.xtdar.app.view.activity.Main2Activity;
import com.xtdar.app.view.widget.LoadDialog;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;


/**
 * Created by hmxbanz on 2017/4/5.
 */

public class LoginPresenter extends BasePresenter  {
    private static final int LOGIN = 1;
    private static final int GET_TOKEN = 2;
    private static final String TAG = "WWWWWWWW";
    private final BasePresenter basePresenter;
    private LoginActivity mActivity;
    private EditText mUsername;
    private EditText mPassword;
    public LoginPresenter(Context context){
        super(context);
        mActivity = (LoginActivity) context;
        basePresenter = BasePresenter.getInstance(context);
    }

    public void init(EditText username, EditText password) {
        this.mUsername=username;
        this.mPassword=password;
    }
    public void login() {
        if(TextUtils.isEmpty(mUsername.getText().toString()))
        {
            NToast.shortToast(context, R.string.phone_number_be_null);
            return;
        }
        if (TextUtils.isEmpty(mPassword.getText().toString())) {
            NToast.shortToast(context, R.string.password_be_null);
            return;
        }
        if (mPassword.getText().toString().contains(" ")) {
            NToast.shortToast(context, R.string.password_cannot_contain_spaces);
            return;
        }
        LoadDialog.show(context);
        atm.request(LOGIN,this);
    }

    @Override
    public Object doInBackground(int requestCode, String id) throws HttpException {
        switch (requestCode) {
            case LOGIN:
                return mUserAction.login(mUsername.getText().toString(), mPassword.getText().toString());
            case GET_TOKEN:
                //return action.getToken();

        }
        return null;
    }
    @Override
    public void onSuccess(int requestCode, Object result) {
        LoadDialog.dismiss(context);
        if (result != null) {
            switch (requestCode) {
                case LOGIN:
                    LoginResponse loginResponse = (LoginResponse) result;
                    if (loginResponse.getCode() == XtdConst.SUCCESS) {
                        LoginResponse.ResultEntity entity=loginResponse.getData();
                        editor.putString(XtdConst.ACCESS_TOKEN, entity.getAccess_key());
                        editor.putString(XtdConst.LOGIN_USERNAME, mUsername.getText().toString());
                        editor.putString(XtdConst.LOGING_PASSWORD, mPassword.getText().toString());

                        editor.putBoolean(XtdConst.ISLOGIN, true);
                        editor.apply();
                        basePresenter.initData();
                        context.startActivity(new Intent(context,Main2Activity.class));
                    } else if (loginResponse.getCode() == XtdConst.FAILURE) {

                    }
                    NToast.shortToast(context, loginResponse.getMsg());
                    break;

            }
        }
    }

    public void wxLogin() {
        final Platform weixin = ShareSDK.getPlatform(Wechat.NAME);
        String name = weixin.getName();

        //设置监听回调
        weixin.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, final HashMap<String, Object> hashMap) {
                Log.d(TAG, " _Weixin: -->> onComplete: Platform:" + platform.toString());
                Log.d(TAG, " _Weixin: -->> onComplete: hashMap:" + hashMap);

                //当前线程不能执行UI操作，需要放到主线程中去
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showUser_WeiXin(hashMap);
                    }
                });
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Log.d(TAG, " _Weixin: -->> onError:  " + throwable.toString());
                throwable.printStackTrace();
                weixin.removeAccount(true);
            }

            @Override
            public void onCancel(Platform platform, int i) {
                NToast.shortToast(context,"取消了");
            }
        });

        //授权并获取用户信息
        weixin.showUser(null);

//        if(!wechat.isAuthValid()){
//            NToast.shortToast(context,"aaaaaaaaaaaaa");
//        } else {
//            NToast.shortToast(context,"bbbbbbbbbbbbb");
//        }



    }

    private void showUser_WeiXin(HashMap<String, Object> hashMap) {
        String name = (String) hashMap.get("nickname");
        NToast.shortToast(context,name);

        String url = (String) hashMap.get("headimgurl");
//        Glide.with(ShareLogin.this)
//                .load(url)
//                .placeholder(R.mipmap.ic_launcher)
//                .error(R.mipmap.ic_launcher)
//                .into(ivPortrait);
    }




}
