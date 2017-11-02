package com.xtdar.app.presenter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import com.orhanobut.logger.Logger;
import com.xtdar.app.R;
import com.xtdar.app.XtdConst;
import com.xtdar.app.common.NToast;
import com.xtdar.app.listener.AlertDialogCallback;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.broadcast.BroadcastManager;
import com.xtdar.app.server.response.CommonResponse;
import com.xtdar.app.server.response.LoginResponse;
import com.xtdar.app.server.response.WxLoginResponse;
import com.xtdar.app.service.BluetoothService;
import com.xtdar.app.view.activity.LoginActivity;
import com.xtdar.app.view.activity.Main2Activity;
import com.xtdar.app.view.activity.RegisterActivity;
import com.xtdar.app.view.widget.LoadDialog;
import com.xtdar.app.widget.DialogWithYesOrNoUtils;

import java.util.HashMap;

import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;


/**
 * Created by hmxbanz on 2017/4/5.
 */

public class LoginPresenter extends BasePresenter  {
    private static final String TAG = LoginPresenter.class.getSimpleName();
    private static final int LOGIN = 1;
    private static final int GET_TOKEN = 2;
    public static final int UPLOADWXOPENID = 3;
    private static final int WXLOGIN = 4;
    private static final int WXBIND = 5;
    public static final int UPLOADQQOPENID = 6;
    private static final int QQBIND = 7;
    private static final int UPLOADRID = 8;
    public static final int UPLOADWBOPENID = 9;
    private static final int WBBIND = 10;
    private final BasePresenter basePresenter;
    private LoginActivity mActivity;
    private EditText mUsername;
    private EditText mPassword;
    private String access_key;
    private String rid;
    private String headimgurl;
    private String nickname;

    public LoginPresenter(Context context){
        super(context);
        mActivity = (LoginActivity) context;
        basePresenter = BasePresenter.getInstance(context);
    }

    public void init(EditText username, EditText password) {
        this.mUsername=username;
        this.mPassword=password;
    }

    public void login(String type) {

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

        if(type.equals("bind")) {
            switch (loginType){
                case "wx":
                    atm.request(WXBIND,this);break;
                case "qq":
                    atm.request(QQBIND,this);break;
                case "wb":
                    atm.request(WBBIND,this);
            }
        }
        else
        {
            atm.request(LOGIN,this);
        }

    }

    @Override
    public Object doInBackground(int requestCode, String id) throws HttpException {
        switch (requestCode) {
            case LOGIN:
                return mUserAction.login(mUsername.getText().toString(), mPassword.getText().toString());
            case UPLOADWXOPENID:
                return mUserAction.wxLogin(openId,headimgurl);
            case WXBIND:
                return mUserAction.wxBind(openId,mUsername.getText().toString(), mPassword.getText().toString());
            case UPLOADQQOPENID:
                return mUserAction.qqLogin(openId,headimgurl);
            case QQBIND:
                return mUserAction.qqBind(openId,mUsername.getText().toString(), mPassword.getText().toString());
            case UPLOADWBOPENID:
                return mUserAction.wbOpenId(openId);
            case WBBIND:
                return mUserAction.wbBind(openId,mUsername.getText().toString(), mPassword.getText().toString());
            case UPLOADRID:
                return mUserAction.upLoadRid(rid);

        }
        return null;
    }
    @Override
    public void onSuccess(final int requestCode, Object result) {
        LoadDialog.dismiss(context);
        if (result==null)return;
            switch (requestCode) {
                case LOGIN:
                    LoginResponse loginResponse = (LoginResponse) result;
                    if (loginResponse.getCode() == XtdConst.SUCCESS) {
                        LoginResponse.ResultEntity entity=loginResponse.getData();
                        loginWork2(entity.getAccess_key());
                        LoadDialog.show(context);
                        atm.request(UPLOADRID,LoginPresenter.this);
                    } else if (loginResponse.getCode() == XtdConst.FAILURE) {

                    }
                    NToast.shortToast(context, loginResponse.getMsg());
                    break;
                case UPLOADWXOPENID:
                case UPLOADQQOPENID:
                case UPLOADWBOPENID:
                    WxLoginResponse response = (WxLoginResponse) result;
                    if (response != null && response.getCode() == XtdConst.SUCCESS) {
                        WxLoginResponse.DataBean entity = response.getData();
                        access_key=entity.getAccess_key();
                        loginWork(entity.getAccess_key());



                        context.startActivity(new Intent(context,Main2Activity.class));
                    } else if (response.getCode() == XtdConst.FAILURE) {
                        DialogWithYesOrNoUtils.getInstance().showDialog(context,"温馨提示","新用户请注册","老用户请绑定",new AlertDialogCallback(){
                            @Override
                            public void executeEvent() {
                                if(requestCode==UPLOADWXOPENID)
                                    LoginActivity.StartActivity(context, openId,"wx");
                                else if(requestCode==UPLOADQQOPENID)
                                    LoginActivity.StartActivity(context, openId,"qq");
                                else if(requestCode==UPLOADWBOPENID)
                                    LoginActivity.StartActivity(context, openId,"wb");
                            }

                            @Override
                            public void onCancle() {
                                Intent toRegisterIntent=new Intent(context,RegisterActivity.class);
                                toRegisterIntent.putExtra("nickname", nickname);
                                toRegisterIntent.putExtra("headimgurl", headimgurl);
                                context.startActivity(toRegisterIntent);

                            }
                        });


                    }
                    NToast.shortToast(context, response.getMsg());
                    break;

                case WXBIND:
                case QQBIND:
                case WBBIND:
                    CommonResponse commonResponse = (CommonResponse) result;
                    if (commonResponse != null && commonResponse.getCode() == XtdConst.SUCCESS) {
                        DialogWithYesOrNoUtils.getInstance().showDialog(context,"绑定成功",null,null,new AlertDialogCallback(){
                            @Override
                            public void executeEvent() {
                                atm.request(LOGIN,LoginPresenter.this);
                            }
                        });
                    }
                    NToast.shortToast(context, commonResponse.getMsg());
                    break;
                case UPLOADRID:
                    CommonResponse commonResponse2 = (CommonResponse) result;
                    if (commonResponse2 != null && commonResponse2.getCode() == XtdConst.SUCCESS) {
                        context.startActivity(new Intent(context,Main2Activity.class));
                    }
                    break;

            }
    }

    public void wxLogin() {
        LoadDialog.show(context);
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
        String avator =  (String) hashMap.get("nickname");
        Logger.d(hashMap.toString());
        nickname = (String) hashMap.get("nickname");
        openId =(String)hashMap.get("openid");
        NToast.shortToast(context,nickname);
        headimgurl = (String) hashMap.get("headimgurl");
        Logger.d("nickname:"+nickname);
        Logger.d("headimgurl:"+headimgurl);
        editor.putString(XtdConst.OPENID, openId);
        editor.putString("loginType", "wx");
        editor.apply();
        atm.request(UPLOADWXOPENID,this);
//        Glide.with(ShareLogin.this)
//                .load(url)
//                .placeholder(R.mipmap.ic_launcher)
//                .error(R.mipmap.ic_launcher)
//                .into(ivPortrait);
    }

    public void qqLogin() {

        LoadDialog.show(context);
        //获取QQ平台手动授权
        final Platform qq = ShareSDK.getPlatform(QQ.NAME);
        //设置回调监听
        qq.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int action, final HashMap<String, Object> hashMap) {
                Log.d(TAG, " _QQ: -->> onComplete: Platform:" + platform.toString());
                Log.d(TAG, " _QQ: -->> onComplete: hashMap:" + hashMap);
                if (action == Platform.ACTION_USER_INFOR) {
                    final PlatformDb platDB = platform.getDb();//获取数平台数据DB
                    //通过DB获取各种数据
                    platDB.getToken();
                    platDB.getUserGender();
                    platDB.getUserIcon();
                    platDB.getUserId();
                    platDB.getUserName();

                    //当前线程不能执行UI操作，需要放到主线程中去
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showUser_QQ(platDB,hashMap);
                        }
                    });

                }

            }

            @Override
            public void onError(Platform platform, int action, Throwable throwable) {
                Log.d(TAG, " _QQ: -->> onError:  " + throwable.toString());
                throwable.printStackTrace();
            }

            @Override
            public void onCancel(Platform platform, int i) {
                qq.removeAccount(true);
            }
        });
        //单独授权,进入输入用户名和密码界面
        /*qq.authorize();*/
        //授权并获得用户信息
        qq.showUser(null);


    }
    private void showUser_QQ(PlatformDb platDB, HashMap<String, Object> hashMap) {
        nickname = (String) hashMap.get("nickname");
        openId =platDB.getUserId();
        headimgurl=platDB.getUserIcon();
        headimgurl=headimgurl.substring(0,headimgurl.lastIndexOf('/'))+"/100";

        NToast.shortToast(context,nickname);

        String url = (String) hashMap.get("figureurl_qq_1");

        Logger.d("nickname:"+nickname);
        Logger.d("headimgurl:"+headimgurl);

        editor.putString("openId", openId);
        editor.putString("loginType", "qq");
        editor.apply();
        atm.request(UPLOADQQOPENID,this);

    }

    /**
     * 新浪授权
     */
    public void weiboLogin() {
        LoadDialog.show(context);
        //获取具体的平台手动授权
        final Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
        //设置回调监听
        weibo.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, final HashMap<String, Object> hashMap) {
                Log.d(TAG, " _XinLang: -->> onComplete: Platform:" + platform.toString());
                Log.d(TAG, " _XinLang: -->> onComplete: hashMap:" + hashMap);
//获取微博平台手动授权
                final Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);

                //当前线程不能执行UI操作，需要放到主线程中去
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showUser_XinLang(hashMap,weibo);
                    }
                });
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Log.d(TAG, " _XinLang: -->> onError:  " + throwable.toString());
                throwable.printStackTrace();
            }

            @Override
            public void onCancel(Platform platform, int i) {
                weibo.removeAccount(true);
            }
        });
        //authorize与showUser单独调用一个即可，
        //单独授权,进入输入用户名和密码界面
        /*weibo.authorize();*/
        //授权并获取用户信息
        weibo.showUser(null);
        //移除授权
        /*weibo.removeAccount(true);*/
    }
    public void showUser_XinLang(HashMap<String, Object> params, Platform weibo) {
        openId =weibo.getDb().getToken();
        LoadDialog.show(context);
        atm.request(UPLOADWBOPENID,this);
        nickname=weibo.getDb().getUserName();
        headimgurl=weibo.getDb().getUserIcon();
        Logger.d("nickname:"+nickname);
        Logger.d("headimgurl:"+headimgurl);
        editor.putString("openId", openId);
        editor.putString("loginType", "wb");
        editor.apply();
    }

    private void loginWork2(String access_key)
    {
        editor.putString(XtdConst.ACCESS_TOKEN, access_key);
        editor.putString(XtdConst.LOGIN_USERNAME, mUsername.getText().toString());
        editor.putString(XtdConst.LOGING_PASSWORD, mPassword.getText().toString());
        editor.putBoolean(XtdConst.ISLOGIN, true);
        editor.apply();
        basePresenter.initData();
        rid = JPushInterface.getRegistrationID(context.getApplicationContext());
        BroadcastManager.getInstance(context).sendBroadcast(MinePresenter.UPDATEUNREAD, "loadAvator");
        BroadcastManager.getInstance(context).sendBroadcast(HomeFragmentPresenter.LOADDEVICE, "loadDevice");

    }


}
