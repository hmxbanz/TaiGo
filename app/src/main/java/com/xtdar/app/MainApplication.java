package com.xtdar.app;

import android.app.Application;

import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.xtdar.app.common.CommonTools;

import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.ShareSDK;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import com.unity3d.player.UnityPlayer;
import com.xtdar.app.common.NLog;

/**
 * Created by hmx on 2016/4/19.
 */

public class MainApplication extends Application {
    public static String MacAddr;
    @Override
    public void onCreate() {
        super.onCreate();
        if(BuildConfig.DEBUG){
            Logger.init("hmx").setLogLevel(LogLevel.FULL);
        }
        else{ Logger.init("hmx").setLogLevel(LogLevel.NONE);}

        //阿里百川商城
        AlibcTradeSDK.asyncInit(this, new AlibcTradeInitCallback() {
            @Override
            public void onSuccess() {
                //初始化成功，设置相关的全局配置参数
                // 是否使用支付宝
                AlibcTradeSDK.setShouldUseAlipay(true);

                // 设置是否使用同步淘客打点
//                AlibcTradeSDK.setSyncForTaoke(true);

//                // 是否走强制H5的逻辑，为true时全部页面均为H5打开
//                AlibcTradeSDK.setForceH5(true);
//
//                // 设置全局淘客参数，方便开发者用同一个淘客参数，不需要在show接口重复传入
//                AlibcTradeSDK.setTaokeParams(taokeParams)
//
//                // 设置渠道信息(如果有渠道专享价，需要设置)
//                AlibcTradeSDK.setChannel(typeName, channelName)

                // ...

                /**
                 * 百川电商SDK初始化【异步】
                 *
                 * @param context 建议设置Application（必填）
                 * @param initResultCallback  初始化状态信息回调（可以为null）
                 */
                AlibcTradeSDK.asyncInit(MainApplication.this, new AlibcTradeInitCallback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onFailure(int i, String s) {

                    }
                });
            }

            @Override
            public void onFailure(int code, String msg) {
                //初始化失败，可以根据code和msg判断失败原因，详情参见错误说明
            }
        });
        //极光推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        ShareSDK.initSDK(this);
        //xutils3
        //x.Ext.init(this);
        //x.Ext.setDebug(BuildConfig.DEBUG);

//        RongIM.init(this);//融云
          NLog.setDebug(true);//开log日志
//        String token="InrWpf704p8lSDS501hoQqUqI4Ur9skzl/M2DxXHGV5UMrXAr4mmC/3UPlxI7qzKnpJ0AvGEB3LFDHqaV6ZdGh3JXdyMvD0s";
//        connect(token);

        // init
//        ShareConfig config = ShareConfig.instance()
//                .qqId(QQ_ID)
//                .wxId(WX_ID)
//                .weiboId(WEIBO_ID)
//                // 下面两个，如果不需要登录功能，可不填写
//                .weiboRedirectUrl(REDIRECT_URL)
//                .wxSecret(WX_ID);
//        ShareManager.init(config);
        ZXingLibrary.initDisplayOpinion(this);
    }

    /**
     * <p>连接服务器，在整个应用程序全局，只需要调用一次，需在 {@link #init(Context)} 之后调用。</p>
     * <p>如果调用此接口遇到连接失败，SDK 会自动启动重连机制进行最多10次重连，分别是1, 2, 4, 8, 16, 32, 64, 128, 256, 512秒后。
     * 在这之后如果仍没有连接成功，还会在当检测到设备网络状态变化时再次进行重连。</p>
     *
     * @param token    从服务端获取的用户身份令牌（Token）。
     * @param callback 连接回调。
     * @return RongIM  客户端核心类的实例。
     */
    private void connect(String token) {

        if (getApplicationInfo().packageName.equals(CommonTools.getCurProcessName(getApplicationContext()))) {

            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
                 *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
                 */
                @Override
                public void onTokenIncorrect() {

                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token 对应的用户 id
                 */
                @Override
                public void onSuccess(String userid) {
//                    Log.d("LoginActivity", "--onSuccess" + userid);
//                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                    finish();
                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {

                }
            });
        }
    }
}

